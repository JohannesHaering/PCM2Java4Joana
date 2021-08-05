/**
 */
package edu.kit.kastel.scbs.pcm2java4joana.joana;

import sourcecode.Method;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Flow Specification Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.kastel.scbs.pcm2java4joana.joana.FlowSpecificationElement#getTag <em>Tag</em>}</li>
 *   <li>{@link edu.kit.kastel.scbs.pcm2java4joana.joana.FlowSpecificationElement#getAnnotatedClass <em>Annotated Class</em>}</li>
 *   <li>{@link edu.kit.kastel.scbs.pcm2java4joana.joana.FlowSpecificationElement#getAnnotatedMethod <em>Annotated Method</em>}</li>
 *   <li>{@link edu.kit.kastel.scbs.pcm2java4joana.joana.FlowSpecificationElement#getAnnotatedClassName <em>Annotated Class Name</em>}</li>
 *   <li>{@link edu.kit.kastel.scbs.pcm2java4joana.joana.FlowSpecificationElement#getAnnotatedMethodName <em>Annotated Method Name</em>}</li>
 * </ul>
 *
 * @see edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaPackage#getFlowSpecificationElement()
 * @model
 * @generated
 */
public interface FlowSpecificationElement extends JoanaElement {
	/**
	 * Returns the value of the '<em><b>Tag</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tag</em>' attribute.
	 * @see #setTag(String)
	 * @see edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaPackage#getFlowSpecificationElement_Tag()
	 * @model default="" required="true"
	 * @generated
	 */
	String getTag();

	/**
	 * Sets the value of the '{@link edu.kit.kastel.scbs.pcm2java4joana.joana.FlowSpecificationElement#getTag <em>Tag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tag</em>' attribute.
	 * @see #getTag()
	 * @generated
	 */
	void setTag(String value);

	/**
	 * Returns the value of the '<em><b>Annotated Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotated Class</em>' reference.
	 * @see #setAnnotatedClass(sourcecode.Class)
	 * @see edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaPackage#getFlowSpecificationElement_AnnotatedClass()
	 * @model required="true"
	 * @generated
	 */
	sourcecode.Class getAnnotatedClass();

	/**
	 * Sets the value of the '{@link edu.kit.kastel.scbs.pcm2java4joana.joana.FlowSpecificationElement#getAnnotatedClass <em>Annotated Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Annotated Class</em>' reference.
	 * @see #getAnnotatedClass()
	 * @generated
	 */
	void setAnnotatedClass(sourcecode.Class value);

	/**
	 * Returns the value of the '<em><b>Annotated Method</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotated Method</em>' reference.
	 * @see #setAnnotatedMethod(Method)
	 * @see edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaPackage#getFlowSpecificationElement_AnnotatedMethod()
	 * @model required="true"
	 * @generated
	 */
	Method getAnnotatedMethod();

	/**
	 * Sets the value of the '{@link edu.kit.kastel.scbs.pcm2java4joana.joana.FlowSpecificationElement#getAnnotatedMethod <em>Annotated Method</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Annotated Method</em>' reference.
	 * @see #getAnnotatedMethod()
	 * @generated
	 */
	void setAnnotatedMethod(Method value);

	/**
	 * Returns the value of the '<em><b>Annotated Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotated Class Name</em>' attribute.
	 * @see #setAnnotatedClassName(String)
	 * @see edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaPackage#getFlowSpecificationElement_AnnotatedClassName()
	 * @model required="true"
	 * @generated
	 */
	String getAnnotatedClassName();

	/**
	 * Sets the value of the '{@link edu.kit.kastel.scbs.pcm2java4joana.joana.FlowSpecificationElement#getAnnotatedClassName <em>Annotated Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Annotated Class Name</em>' attribute.
	 * @see #getAnnotatedClassName()
	 * @generated
	 */
	void setAnnotatedClassName(String value);

	/**
	 * Returns the value of the '<em><b>Annotated Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotated Method Name</em>' attribute.
	 * @see #setAnnotatedMethodName(String)
	 * @see edu.kit.kastel.scbs.pcm2java4joana.joana.JoanaPackage#getFlowSpecificationElement_AnnotatedMethodName()
	 * @model required="true"
	 * @generated
	 */
	String getAnnotatedMethodName();

	/**
	 * Sets the value of the '{@link edu.kit.kastel.scbs.pcm2java4joana.joana.FlowSpecificationElement#getAnnotatedMethodName <em>Annotated Method Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Annotated Method Name</em>' attribute.
	 * @see #getAnnotatedMethodName()
	 * @generated
	 */
	void setAnnotatedMethodName(String value);

} // FlowSpecificationElement