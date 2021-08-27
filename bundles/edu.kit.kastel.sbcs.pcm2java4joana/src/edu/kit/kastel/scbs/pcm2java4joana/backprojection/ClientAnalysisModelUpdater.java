package edu.kit.kastel.scbs.pcm2java4joana.backprojection;

import java.util.Collection;

import edu.kit.kastel.scbs.confidentiality.adversary.Adversary;
import edu.kit.kastel.scbs.confidentiality.data.DataIdentifying;
import edu.kit.kastel.scbs.pcm2java4joana.correspondencemodel.StructuralCorrespondenceModel;
import edu.kit.kastel.scbs.pcm2java4joana.models.ClientAnalysisModel;
import edu.kit.kastel.scbs.pcm2java4joana.models.EquationSystem;
import edu.kit.kastel.scbs.pcm2java4joana.models.SecurityLevelEquation;
import edu.kit.kastel.scbs.pcm2java4joana.securitycorrespondencemodel.ParametersAndDataPair2Annotation;
import edu.kit.kastel.scbs.pcm2java4joana.securitycorrespondencemodel.SecurityCorrespondenceModel;
import edu.kit.kastel.scbs.pcm2java4joana.utils.CorrespondenceModelUtils;

public class ClientAnalysisModelUpdater {
	public void adaptClientAnalysisModel(ClientAnalysisModel clientAnalysisModel,
			StructuralCorrespondenceModel structuralCorrespondenceModel,
			SecurityCorrespondenceModel securityCorrespondenceModel, EquationSystem solvedEquationSystem) {

		for (SecurityLevelEquation equation : solvedEquationSystem.getEquations()) {
			ParametersAndDataPair2Annotation pair2Annotation = CorrespondenceModelUtils
					.getParamtersAndDataPairAnnotationCorrespondence(securityCorrespondenceModel,
							equation.getOwner().getBaseState());
			Collection<Adversary> adversaries = CorrespondenceModelUtils
					.reolveSecurityLevelToAdversary(securityCorrespondenceModel, equation.getSecurityLevel());

			for (Adversary adversary : adversaries) {
				for (DataIdentifying dataSet : adversary.getMayKnowData()) {
					// TODO: Rework with new correspondence model
//					if (!pair2Annotation.getParametersAndDataPair().getDataTargets().contains(dataSet)) {
//						pair2Annotation.getParametersAndDataPair().getDataTargets().add(dataSet);
//					}
				}
			}
		}
	}
}
