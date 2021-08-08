package edu.kit.kastel.scbs.pcm2java4joana.modelgenerator;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.kastel.scbs.pcm2java4joana.models.ClientAnalysisModel;
import edu.kit.kastel.scbs.pcm2java4joana.models.SupplierAnalysisModel;
import edu.kit.kastel.scbs.pcm2java4joana.utils.Tuple;

public class SupplierAnalysisModelGenerator {
	private ClientAnalysisModel models;
	private IPath destinationFolder;

	public SupplierAnalysisModelGenerator(ClientAnalysisModel models, IPath destinationFolder) {
		this.models = models;
		this.destinationFolder = destinationFolder;
	}

	public SupplierAnalysisModel generate() {
		SourceCodeModelWithCorrespondenceModelGenerator sourceCodeGenerator = new SourceCodeModelWithCorrespondenceModelGenerator(
				models.getRepository(), this.destinationFolder);

		Tuple<Resource, Resource> sourceCodeModel = sourceCodeGenerator
				.generateSourceCodeModelWithCorrespondenceModel();

		SupplierAnalysisModel supplierAnalysisModel = new SupplierAnalysisModel(sourceCodeModel.getFirst(),
				sourceCodeModel.getSecond(), this.destinationFolder);

		AnnotationModelGenerator annotationsModelGenerator = new AnnotationModelGenerator(this.models,
				supplierAnalysisModel);
		supplierAnalysisModel = annotationsModelGenerator.generateJoanaModel();

		return supplierAnalysisModel;
	}
}
