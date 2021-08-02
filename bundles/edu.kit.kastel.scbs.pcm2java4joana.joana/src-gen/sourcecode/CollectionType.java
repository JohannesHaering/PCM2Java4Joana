/**
 */
package sourcecode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Collection Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link sourcecode.CollectionType#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see sourcecode.SourcecodePackage#getCollectionType()
 * @model
 * @generated
 */
public interface CollectionType extends Type {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(Type)
	 * @see sourcecode.SourcecodePackage#getCollectionType_Type()
	 * @model containment="true"
	 * @generated
	 */
	Type getType();

	/**
	 * Sets the value of the '{@link sourcecode.CollectionType#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(Type value);

} // CollectionType
