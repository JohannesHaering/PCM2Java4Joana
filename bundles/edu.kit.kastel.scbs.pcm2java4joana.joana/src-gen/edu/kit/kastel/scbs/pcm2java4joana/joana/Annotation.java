/**
 */
package edu.kit.kastel.scbs.pcm2java4joana.joana;

import org.eclipse.emf.common.util.EList;
import sourcecode.Parameter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.kastel.scbs.pcm2java4joana.joana.Annotation#getSecuritylevel <em>Securitylevel</em>}</li>
 *   <li>{@link edu.kit.kastel.scbs.pcm2java4joana.joana.Annotation#getAnnotatedParameter <em>Annotated Parameter</em>}</li>
 *   <li>{@link edu.kit.kastel.scbs.pcm2java4joana.joana.Annotation#getAnnotatedParameterName <em>Annotated Parameter Name</em>}</li>
 * </ul>
 *
 * @see edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaPackage#getAnnotation()
 * @model
 * @generated
 */
public interface Annotation extends FlowSpecificationElement {

	/**
	 * Returns the value of the '<em><b>Securitylevel</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.kastel.scbs.pcm2java4joana.joana.SecurityLevel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Securitylevel</em>' containment reference list.
	 * @see edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaPackage#getAnnotation_Securitylevel()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<SecurityLevel> getSecuritylevel();

	/**
	 * Returns the value of the '<em><b>Annotated Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotated Parameter</em>' reference.
	 * @see #setAnnotatedParameter(Parameter)
	 * @see edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaPackage#getAnnotation_AnnotatedParameter()
	 * @model required="true"
	 * @generated
	 */
	Parameter getAnnotatedParameter();

	/**
	 * Sets the value of the '{@link edu.kit.kastel.scbs.pcm2java4joana.joana.Annotation#getAnnotatedParameter <em>Annotated Parameter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Annotated Parameter</em>' reference.
	 * @see #getAnnotatedParameter()
	 * @generated
	 */
	void setAnnotatedParameter(Parameter value);

	/**
	 * Returns the value of the '<em><b>Annotated Parameter Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotated Parameter Name</em>' attribute.
	 * @see #setAnnotatedParameterName(String)
	 * @see edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaPackage#getAnnotation_AnnotatedParameterName()
	 * @model required="true"
	 * @generated
	 */
	String getAnnotatedParameterName();

	/**
	 * Sets the value of the '{@link edu.kit.kastel.scbs.pcm2java4joana.joana.Annotation#getAnnotatedParameterName <em>Annotated Parameter Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Annotated Parameter Name</em>' attribute.
	 * @see #getAnnotatedParameterName()
	 * @generated
	 */
	void setAnnotatedParameterName(String value);
} // Annotation
