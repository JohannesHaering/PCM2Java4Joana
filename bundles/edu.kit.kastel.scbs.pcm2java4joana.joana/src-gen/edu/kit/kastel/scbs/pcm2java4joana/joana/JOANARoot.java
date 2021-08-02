/**
 */
package edu.kit.kastel.scbs.pcm2java4joana.joana;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JOANA Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.kastel.scbs.pcm2java4joana.joana.JOANARoot#getFlowspecification <em>Flowspecification</em>}</li>
 * </ul>
 *
 * @see edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaPackage#getJOANARoot()
 * @model
 * @generated
 */
public interface JOANARoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Flowspecification</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.kastel.scbs.pcm2java4joana.joana.FlowSpecification}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flowspecification</em>' containment reference list.
	 * @see edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaPackage#getJOANARoot_Flowspecification()
	 * @model containment="true"
	 * @generated
	 */
	EList<FlowSpecification> getFlowspecification();

} // JOANARoot
