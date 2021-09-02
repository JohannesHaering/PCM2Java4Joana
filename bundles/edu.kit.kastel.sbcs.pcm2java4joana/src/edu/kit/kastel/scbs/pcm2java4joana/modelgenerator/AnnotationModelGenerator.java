package edu.kit.kastel.scbs.pcm2java4joana.modelgenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.modelversioning.emfprofileapplication.ProfileApplication;
import org.modelversioning.emfprofileapplication.StereotypeApplication;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;

import edu.kit.ipd.sdq.commons.util.org.palladiosimulator.mdsdprofiles.api.StereotypeAPIUtil;
import edu.kit.kastel.scbs.confidentiality.ConfidentialitySpecification;
import edu.kit.kastel.scbs.confidentiality.data.DataIdentifying;
import edu.kit.kastel.scbs.confidentiality.data.DataSet;
import edu.kit.kastel.scbs.confidentiality.repository.ParametersAndDataPair;
import edu.kit.kastel.scbs.pcm2java4joana.joana.Annotation;
import edu.kit.kastel.scbs.pcm2java4joana.joana.EntryPoint;
import edu.kit.kastel.scbs.pcm2java4joana.joana.FlowRelation;
import edu.kit.kastel.scbs.pcm2java4joana.joana.FlowSpecification;
import edu.kit.kastel.scbs.pcm2java4joana.joana.JOANARoot;
import edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaFactory;
import edu.kit.kastel.scbs.pcm2java4joana.joana.Lattice;
import edu.kit.kastel.scbs.pcm2java4joana.joana.SecurityLevel;
import edu.kit.kastel.scbs.pcm2java4joana.joana.Sink;
import edu.kit.kastel.scbs.pcm2java4joana.joana.Source;
import edu.kit.kastel.scbs.pcm2java4joana.models.ClientAnalysisModel;
import edu.kit.kastel.scbs.pcm2java4joana.models.SupplierAnalysisModel;
import edu.kit.kastel.scbs.pcm2java4joana.securitycorrespondencemodel.SecurityCorrespondenceModel;
import edu.kit.kastel.scbs.pcm2java4joana.securitycorrespondencemodel.SecuritycorrespondencemodelFactory;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Class;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Interface;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Method;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Parameter;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.SourceCodeRoot;
import edu.kit.kastel.scbs.pcm2java4joana.utils.JoanaModelUtils;
import edu.kit.kastel.scbs.pcm2java4joana.utils.SetOperations;
import edu.kit.kastel.scbs.pcm2java4joana.utils.SourceCodeModelUtils;

public class AnnotationModelGenerator {
	private SupplierAnalysisModel supplierAnalysisModel;
	private ClientAnalysisModel clientAnalysisModel;
	private SecurityCorrespondenceModel securityCorrespondenceModel;

	public AnnotationModelGenerator(ClientAnalysisModel clientAnalysisModel,
			SupplierAnalysisModel supplierAnalysisModel) {
		this.supplierAnalysisModel = supplierAnalysisModel;
		this.clientAnalysisModel = clientAnalysisModel;
		this.securityCorrespondenceModel = SecuritycorrespondencemodelFactory.eINSTANCE
				.createSecurityCorrespondenceModel();
	}

	public SupplierAnalysisModel generateJoanaModel() {
		ConfidentialitySpecification confidentiality = this.clientAnalysisModel.getConfidentiality();

		List<SecurityLevel> levels = this.generateLevels(confidentiality);
		Map<SecurityLevel, DataIdentifying> levelToDatasetsMapping = this
				.generateSecurityLevelToDatasetMapping(confidentiality);
		Lattice lattice = this.generateLattice(levels);
		List<Annotation> annotations = this.generateAnnotations(levelToDatasetsMapping,
				confidentiality.getParametersAndDataPairs(),
				(SourceCodeRoot) this.supplierAnalysisModel.getSourceCodeModel(), this.clientAnalysisModel);
		List<FlowSpecification> flowSpecifications = this.generateFlowSpecifications(annotations, lattice, levels);

		JoanaFactory factory = JoanaFactory.eINSTANCE;
		JOANARoot root = factory.createJOANARoot();
		root.getFlowspecification().addAll(flowSpecifications);
		this.supplierAnalysisModel.setJoanaModel(root);
		this.supplierAnalysisModel.setSecurityCorrespondendenceModel(this.securityCorrespondenceModel);

		return this.supplierAnalysisModel;
	}

	private List<FlowSpecification> generateFlowSpecifications(List<Annotation> annotations, Lattice lattice,
			List<SecurityLevel> levels) {
		List<FlowSpecification> flowSpefications = new ArrayList<FlowSpecification>();

		int startIndex = 0;
		for (int i = 0; i < annotations.size(); i++) {
			flowSpefications.addAll(this.generateFlowSpecification(i, startIndex, annotations, lattice, levels));
			startIndex = flowSpefications.size();
		}

		return flowSpefications;
	}

	private List<FlowSpecification> generateFlowSpecification(int entryPointIndex, int startTag,
			List<Annotation> annotations, Lattice lattice, List<SecurityLevel> levels) {
		JoanaFactory factory = JoanaFactory.eINSTANCE;
		List<FlowSpecification> flows = new ArrayList<FlowSpecification>();

		List<Integer> possibleTags = new ArrayList<Integer>();
		for (int i = 0; i < annotations.size(); i++) {
			possibleTags.add(i);
		}
		List<List<Integer>> sourceDistributions = SetOperations.generatePowerSet(possibleTags);

		for (int i = 0; i < sourceDistributions.size(); i++) {
			List<Integer> sourceDistribution = sourceDistributions.get(i);

			FlowSpecification flow = factory.createFlowSpecification();
			EntryPoint entryPoint = this.generateEntryPoint(Integer.toString(startTag + i),
					annotations.get(entryPointIndex), lattice, levels);
			List<Annotation> flowAnnotations = new ArrayList<Annotation>();
			for (int j = 0; j < possibleTags.size(); j++) {
				int possibleTag = possibleTags.get(j);
				if (sourceDistribution.contains(possibleTag)) {
					flowAnnotations
							.add(this.generateSource(Integer.toString(startTag + i), annotations.get(possibleTag)));
				} else {
					flowAnnotations
							.add(this.generateSink(Integer.toString(startTag + i), annotations.get(possibleTag)));
				}
			}

			flow.setEntrypoint(entryPoint);
			flow.getAnnotation().addAll(flowAnnotations);
			flows.add(flow);
		}

		return flows;
	}

	private Source generateSource(String tag, Annotation annotation) {
		JoanaFactory factory = JoanaFactory.eINSTANCE;
		Source source = factory.createSource();
		source.setTag(tag);
		source.setAnnotatedClass(annotation.getAnnotatedClass());
		source.setAnnotatedMethod(annotation.getAnnotatedMethod());
		source.setAnnotatedParameter(annotation.getAnnotatedParameter());
		source.getSecuritylevel().addAll(JoanaModelUtils.copySecurityLevels(annotation.getSecuritylevel()));

		return source;
	}

	private Sink generateSink(String tag, Annotation annotation) {
		JoanaFactory factory = JoanaFactory.eINSTANCE;
		Sink sink = factory.createSink();
		sink.setTag(tag);
		sink.setAnnotatedClass(annotation.getAnnotatedClass());
		sink.setAnnotatedMethod(annotation.getAnnotatedMethod());
		sink.setAnnotatedParameter(annotation.getAnnotatedParameter());
		sink.getSecuritylevel().addAll(JoanaModelUtils.copySecurityLevels(annotation.getSecuritylevel()));

		return sink;
	}

	private EntryPoint generateEntryPoint(String tag, Annotation annotation, Lattice lattice,
			List<SecurityLevel> levels) {
		JoanaFactory factory = JoanaFactory.eINSTANCE;
		EntryPoint entryPoint = factory.createEntryPoint();
		entryPoint.setTag(tag);
		entryPoint.setAnnotatedClass(annotation.getAnnotatedClass());
		entryPoint.setAnnotatedMethod(annotation.getAnnotatedMethod());
		entryPoint.setLattice(JoanaModelUtils.copyLattice(lattice));
		entryPoint.getSecuritylevels().addAll(JoanaModelUtils.copySecurityLevels(levels));

		return entryPoint;
	}

	private List<Annotation> generateAnnotations(Map<SecurityLevel, DataIdentifying> levelToDatasetsMapping,
			List<ParametersAndDataPair> parametersAndDataPair, SourceCodeRoot sourceCode,
			ClientAnalysisModel clientAnalysisModel) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		ProfileApplication profile = clientAnalysisModel.getProfile();

		for (StereotypeApplication application : profile.getStereotypeApplications()) {
			var stereotype = application.getStereotype();
			if (stereotype.getName().equals("InformationFlow")) {
				List<Annotation> annotation = this.generateAnnotation(levelToDatasetsMapping, application, sourceCode);
				if (annotation != null) {
					annotations.addAll(annotation);
				}
			}
		}

		return annotations;
	}

	private List<Annotation> generateAnnotation(Map<SecurityLevel, DataIdentifying> levelToDatasetsMapping,
			StereotypeApplication stereotypeApplication, SourceCodeRoot sourceCode) {
		JoanaFactory factory = JoanaFactory.eINSTANCE;

		List<StereotypeApplication> iter = new ArrayList<StereotypeApplication>();
		iter.add(stereotypeApplication);
		ParametersAndDataPair appliedPair = StereotypeAPIUtil
				.getTaggedValues(iter, "parametersAndDataPairs", ParametersAndDataPair.class).get(0);
		List<SecurityLevel> securityLevels = this.getSecurityLevel(appliedPair, levelToDatasetsMapping);

		OperationSignature pcmSignature = (OperationSignature) stereotypeApplication.getAppliedTo();
		OperationInterface pcmInter = pcmSignature.getInterface__OperationSignature();

		Interface inter = SourceCodeModelUtils.getInterface(sourceCode, pcmInter.getEntityName());
		if (inter == null) {
			return null;
		}
		List<Method> methods = SourceCodeModelUtils.getMethod(inter, pcmSignature.getEntityName());
		List<Method> toRemove = new ArrayList<Method>();
		if (!appliedPair.getParameterSources().get(0).equals("\\return")) {
			for (Method method : methods) {
				List<String> parameterNames = new ArrayList<String>();
				for (Parameter parameter : method.getParameter()) {
					parameterNames.add(parameter.getName());
				}
				if (!SetOperations.isIn(appliedPair.getParameterSources(), parameterNames)) {
					toRemove.add(method);
				}
			}
			methods.removeAll(toRemove);
		}
		List<Class> components = SourceCodeModelUtils.getComponents(sourceCode, inter);

		List<Annotation> annotations = new ArrayList<Annotation>();
		for (Class component : components) {
			for (Method method : methods) {
				Annotation annotation = factory.createAnnotation();
				annotation.getSecuritylevel().addAll(JoanaModelUtils.copySecurityLevels(securityLevels));
				annotation.setAnnotatedClass(component);
				annotation.setAnnotatedMethod(method);
				if (!appliedPair.getParameterSources().get(0).equals("\\return")) {
					annotation.setAnnotatedParameter(
							SourceCodeModelUtils.getParameter(method, appliedPair.getParameterSources().get(0)));
				}
				annotations.add(annotation);
				this.securityCorrespondenceModel.getParametersanddatapair2annotation()
						.add(CorrespondenceModelElementsGenerator.generateParametersAndDataPair2Annotation(appliedPair,
								annotation));
			}
		}

		return annotations;
	}

	private List<SecurityLevel> getSecurityLevel(ParametersAndDataPair pair,
			Map<SecurityLevel, DataIdentifying> levelToDatasetsMapping) {
		List<SecurityLevel> levels = new ArrayList<SecurityLevel>();
		List<DataIdentifying> targets = pair.getDataTargets();

		var it = levelToDatasetsMapping.entrySet().iterator();
		while (it.hasNext()) {
			var entry = (Entry<SecurityLevel, DataIdentifying>) it.next();
			if (targets.contains(entry.getValue())) {
				levels.add(entry.getKey());
			}
		}

		return levels;
	}

	private Map<SecurityLevel, DataIdentifying> generateSecurityLevelToDatasetMapping(
			ConfidentialitySpecification specification) {
		JoanaFactory factory = JoanaFactory.eINSTANCE;
		Map<SecurityLevel, DataIdentifying> levelToDataset = new HashMap<SecurityLevel, DataIdentifying>();

		for (DataIdentifying dataIdentifying : specification.getDataIdentifier()) {
			if (dataIdentifying instanceof DataSet) {
				DataSet dataset = (DataSet) dataIdentifying;
				SecurityLevel level = factory.createSecurityLevel();
				level.setName(dataset.getName());
				levelToDataset.put(level, dataset);
			}
		}

		return levelToDataset;
	}

	private List<SecurityLevel> generateLevels(ConfidentialitySpecification specification) {
		JoanaFactory factory = JoanaFactory.eINSTANCE;
		List<SecurityLevel> levels = new ArrayList<SecurityLevel>();

		for (DataIdentifying dataIdentifying : specification.getDataIdentifier()) {
			if (dataIdentifying instanceof DataSet) {
				DataSet dataset = (DataSet) dataIdentifying;
				SecurityLevel level = factory.createSecurityLevel();
				level.setName(dataset.getName());
				levels.add(level);
				this.securityCorrespondenceModel.getDataset2securitylevel()
						.add(CorrespondenceModelElementsGenerator.generateDatasetCorrespondence(dataset, level));
			}
		}

		return levels;
	}

	private Lattice generateLattice(List<SecurityLevel> levels) {
		JoanaFactory factory = JoanaFactory.eINSTANCE;
		Lattice lattice = factory.createLattice();

		List<List<SecurityLevel>> powerSetLevels = SetOperations.generatePowerSet(levels);

		for (List<SecurityLevel> from : powerSetLevels) {
			if (from.size() > 1) {

			}
			for (List<SecurityLevel> to : powerSetLevels) {
				if (SetOperations.isIn(to, from) && !SetOperations.sameElements(from, to) && from.size() > 0
						&& to.size() > 0) {
					FlowRelation relation = factory.createFlowRelation();
					relation.getFrom().addAll(JoanaModelUtils.copySecurityLevels(from));
					relation.getTo().addAll(JoanaModelUtils.copySecurityLevels(to));
					lattice.getFlowrelation().add(relation);
				}
			}
		}

		return lattice;
	}
}
