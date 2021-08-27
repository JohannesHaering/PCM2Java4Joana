package edu.kit.kastel.scbs.pcm2java4joana.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;

import edu.kit.kastel.scbs.pcm2java4joana.joana.Annotation;
import edu.kit.kastel.scbs.pcm2java4joana.joana.EntryPoint;
import edu.kit.kastel.scbs.pcm2java4joana.joana.FlowRelation;
import edu.kit.kastel.scbs.pcm2java4joana.joana.FlowSpecification;
import edu.kit.kastel.scbs.pcm2java4joana.joana.FlowSpecificationElement;
import edu.kit.kastel.scbs.pcm2java4joana.joana.JOANARoot;
import edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaFactory;
import edu.kit.kastel.scbs.pcm2java4joana.joana.Lattice;
import edu.kit.kastel.scbs.pcm2java4joana.joana.SecurityLevel;
import edu.kit.kastel.scbs.pcm2java4joana.joana.Sink;
import edu.kit.kastel.scbs.pcm2java4joana.joana.Source;
import edu.kit.kastel.scbs.pcm2java4joana.joana.impl.SinkImpl;

public final class JoanaModelUtils {
	private JoanaModelUtils() {

	}

	public static List<FlowSpecificationElement> getJoanaFlowSpecificationElementsFor(JOANARoot joanaModel,
			String className, String methodName) {
		return JoanaModelUtils.getJoanaFlowSpecificationElementsFor(joanaModel, className, methodName, "");
	}

	public static List<FlowSpecificationElement> getJoanaFlowSpecificationElementsFor(JOANARoot joanaModel,
			String className, String methodName, String parameterName) {
		List<FlowSpecificationElement> elements = new ArrayList<FlowSpecificationElement>();

		if (joanaModel == null) {
			return elements;
		}

		for (FlowSpecification flow : joanaModel.getFlowspecification()) {
			for (Annotation specificationElement : flow.getAnnotation()) {
				if (specificationElement.getAnnotatedClass().getName().equals(className)
						&& specificationElement.getAnnotatedMethod().getName().equals(methodName)
						&& ((specificationElement.getAnnotatedParameter() == null && parameterName.equals(""))
								|| (specificationElement.getAnnotatedParameter() != null && specificationElement
										.getAnnotatedParameter().getName().equals(parameterName)))) {
					elements.add(specificationElement);
				}
			}
		}

		return elements;
	}

	public static List<EntryPoint> getEntryPoints(JOANARoot root, String className, String methodName) {
		List<EntryPoint> entries = new ArrayList<EntryPoint>();

		for (FlowSpecification element : root.getFlowspecification()) {
			if (element.getEntrypoint().getAnnotatedClass().getName().equals(className)
					&& element.getEntrypoint().getAnnotatedMethod().getName().equals(methodName)) {
				entries.add(element.getEntrypoint());
			}
		}

		return entries;
	}

	public static List<Source> getSourcesFor(JOANARoot root, String className, String methodName) {
		return JoanaModelUtils.getSourcesFor(root, className, methodName, "");
	}

	public static List<Source> getSourcesFor(JOANARoot root, String className, String methodName,
			String parameterName) {
		List<Source> sources = new ArrayList<Source>();

		for (FlowSpecificationElement element : JoanaModelUtils.getJoanaFlowSpecificationElementsFor(root, className,
				methodName, parameterName)) {
			if (element instanceof Source) {
				sources.add((Source) element);
			}
		}

		return sources;
	}

	public static List<Sink> getSinksFor(JOANARoot root, String className, String methodName) {
		return JoanaModelUtils.getSinksFor(root, className, methodName, "");
	}

	public static List<Sink> getSinksFor(JOANARoot root, String className, String methodName, String parameterName) {
		List<Sink> sinks = new ArrayList<Sink>();

		for (FlowSpecificationElement element : JoanaModelUtils.getJoanaFlowSpecificationElementsFor(root, className,
				methodName, parameterName)) {
			if (element.getClass() == SinkImpl.class) {
				sinks.add((Sink) element);
			}
		}

		return sinks;
	}

	public static List<SecurityLevel> sortSecurityLevels(List<SecurityLevel> unsorted) {
		return unsorted.stream().sorted((object1, object2) -> object1.getName().compareTo(object2.getName()))
				.collect(Collectors.toList());
	}

	public static String combineIntoOneSecurityLevel(List<SecurityLevel> levels) {
		List<SecurityLevel> sorted = JoanaModelUtils.sortSecurityLevels(JoanaModelUtils.copySecurityLevels(levels));
		String combined = "";
		for (SecurityLevel level : sorted) {
			combined += level.getName();
		}

		return combined;
	}

	public static Lattice copyLattice(Lattice lattice) {
		JoanaFactory factory = JoanaFactory.eINSTANCE;
		Lattice copiedLattice = factory.createLattice();
		for (FlowRelation relation : lattice.getFlowrelation()) {
			copiedLattice.getFlowrelation().add(JoanaModelUtils.copyFlowRelation(relation));
		}
		return copiedLattice;
	}

	public static FlowRelation copyFlowRelation(FlowRelation relation) {
		JoanaFactory factory = JoanaFactory.eINSTANCE;
		FlowRelation flowRelation = factory.createFlowRelation();
		flowRelation.getTo().addAll(JoanaModelUtils.copySecurityLevels(relation.getTo()));
		flowRelation.getFrom().addAll(JoanaModelUtils.copySecurityLevels(relation.getFrom()));

		return flowRelation;
	}

	public static List<SecurityLevel> copySecurityLevels(List<SecurityLevel> levels) {
		List<SecurityLevel> copiedlLevels = new ArrayList<SecurityLevel>();
		for (SecurityLevel level : levels) {
			copiedlLevels.add(JoanaModelUtils.copySecurityLevel(level));
		}
		return copiedlLevels;
	}

	public static SecurityLevel copySecurityLevel(SecurityLevel level) {
		JoanaFactory factory = JoanaFactory.eINSTANCE;
		SecurityLevel copiedLevel = factory.createSecurityLevel();
		copiedLevel.setName(level.getName());
		return copiedLevel;
	}

	public static Annotation copyAnnotation(Annotation annotation) {
		JoanaFactory factory = JoanaFactory.eINSTANCE;
		Annotation copiedAnnotation = factory.createAnnotation();
		copiedAnnotation.setAnnotatedClass(annotation.getAnnotatedClass());
		copiedAnnotation.setAnnotatedMethod(annotation.getAnnotatedMethod());
		copiedAnnotation.setAnnotatedParameter(annotation.getAnnotatedParameter());
		copiedAnnotation.getSecuritylevel().addAll(copySecurityLevels(annotation.getSecuritylevel()));
		return copiedAnnotation;
	}

	public static Collection<EObject> flattenJoanaModel() {
		Collection<EObject> objects = new ArrayList<EObject>();

		return objects;
	}
}
