package edu.kit.kastel.scbs.pcm2java4joana.utils;

import java.util.ArrayList;
import java.util.List;

import edu.kit.kastel.scbs.pcm2java4joana.joanasimplifiedresult.ResultMethod;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.BuiltInType;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Class;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.CollectionType;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Field;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Interface;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Method;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Parameter;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.ReferenceType;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.SourceCodeRoot;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.TopLevelType;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Type;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Variable;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.impl.ClassImpl;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.impl.CollectionTypeImpl;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.impl.InterfaceImpl;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.impl.MethodImpl;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.impl.ReferenceTypeImpl;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.impl.VariableImpl;

public final class SourceCodeModelUtils {
	private SourceCodeModelUtils() {

	}

	public static Interface getInterface(SourceCodeRoot sourceCodeModel, String entityName) {
		for (TopLevelType topLevelType : sourceCodeModel.getTopleveltype()) {
			if (topLevelType.getClass() == InterfaceImpl.class) {
				if (topLevelType.getName().equals(entityName)) {
					return (Interface) topLevelType;
				}
			}
		}
		return null;
	}

	public static List<Class> getComponents(SourceCodeRoot sourceCodeModel, Interface implemented) {
		List<Class> components = new ArrayList<Class>();
		for (TopLevelType topLevelType : sourceCodeModel.getTopleveltype()) {
			if (topLevelType.getClass() == ClassImpl.class) {
				Class component = (Class) topLevelType;
				if (component.getImplements().contains(implemented)) {
					components.add(component);
				}
			}
		}

		return components;
	}

	public static List<Method> getMethod(Interface inter, String methodName) {
		List<Method> methods = new ArrayList<Method>();
		for (Method method : inter.getMethods()) {
			if (method.getName().equals(methodName)) {
				methods.add(method);
			}
		}
		return methods;
	}

	public static Parameter getParameter(Method method, String parameterName) {
		for (Parameter parameter : method.getParameter()) {
			if (parameter.getName().equals(parameterName)) {
				return parameter;
			}
		}
		return null;
	}

	public static List<Variable> getVariables(Class scClass) {
		List<Variable> variables = new ArrayList<Variable>();
		for (Field field : scClass.getFields()) {
			if (field.getClass() == VariableImpl.class) {
				variables.add((Variable) field);
			}
		}
		return variables;
	}

	public static boolean hasCollectionType(List<Field> fields) {
		boolean returnValue = false;
		for (Field field : fields) {
			if (field.getClass() == Variable.class) {
				returnValue = returnValue || SourceCodeModelUtils.hasVariableCollectionType((Variable) field);
			}
			if (field.getClass() == Method.class) {
				returnValue = returnValue || SourceCodeModelUtils.hasMethodCollectionType((Method) field);
			}
		}
		return returnValue;
	}

	public static boolean hasMethodCollectionType(Method method) {
		if (method.getType() != null && method.getType().getClass() == CollectionTypeImpl.class) {
			return true;
		}

		for (Parameter parameter : method.getParameter()) {
			if (parameter.getType() != null && parameter.getType().getClass() == CollectionTypeImpl.class) {
				return true;
			}
		}

		return false;
	}

	public static boolean hasVariableCollectionType(Variable variable) {
		if (variable.getType().getClass() == CollectionTypeImpl.class) {
			return true;
		}
		return false;
	}

	public static List<String> getReferenceTypes(List<Field> fields) {
		List<String> referenceTypes = new ArrayList<String>();
		for (Field field : fields) {
			List<String> addTypes = SourceCodeModelUtils.getReferenceTypes(field);
			referenceTypes.addAll(addTypes);
		}
		return SetOperations.removeDuplicates(referenceTypes);
	}

	public static List<String> getReferenceTypes(Field field) {
		List<String> returnValue = new ArrayList<String>();
		if (field.getClass() == VariableImpl.class) {
			returnValue.addAll(SourceCodeModelUtils.getReferenceTypeForVariable((Variable) field));
		}
		if (field.getClass() == MethodImpl.class) {
			returnValue.addAll(SourceCodeModelUtils.getReferenceTypeForMethod((Method) field));
		}
		return returnValue;
	}

	public static List<String> getReferenceTypeForMethod(Method method) {
		String methodType = "";
		var referenceTypes = new ArrayList<String>();

		if (method.getType() != null) {
			if (method.getType().getClass() == ReferenceTypeImpl.class) {
				methodType = SourceCodeModelUtils.getReferenceTypeName((ReferenceType) method.getType());
			}
			if (method.getType().getClass() == CollectionType.class) {
				methodType = SourceCodeModelUtils.getReferenceTypeForCollectionType((CollectionType) method.getType());
			}
			if (!methodType.equals("")) {
				referenceTypes.add(methodType);
			}
		}

		for (Parameter parameter : method.getParameter()) {
			var paramterType = SourceCodeModelUtils.getReferenceTypeForParameter(parameter);
			if (!paramterType.equals("")) {
				referenceTypes.add(paramterType);
			}
		}

		return referenceTypes;
	}

	public static String getReferenceTypeForParameter(Parameter parameter) {
		String returnValue = "";
		if (parameter.getType() != null && parameter.getType().getClass() == ReferenceTypeImpl.class) {
			returnValue = SourceCodeModelUtils.getReferenceTypeName((ReferenceType) parameter.getType());
		}
		if (parameter.getType() != null && parameter.getType().getClass() == CollectionTypeImpl.class) {
			returnValue = SourceCodeModelUtils.getReferenceTypeForCollectionType((CollectionType) parameter.getType());
		}
		return returnValue;
	}

	public static List<String> getReferenceTypeForVariable(Variable variable) {
		List<String> referenceTypes = new ArrayList<String>();
		String referenceType = "";
		if (variable.getType() != null) {
			if (variable.getType().getClass() == ReferenceTypeImpl.class) {
				referenceType = SourceCodeModelUtils.getReferenceTypeName((ReferenceType) variable.getType());
			}
			if (variable.getType().getClass() == CollectionTypeImpl.class) {
				referenceType = SourceCodeModelUtils
						.getReferenceTypeForCollectionType((CollectionType) variable.getType());
			}
			if (!referenceType.equals("")) {
				referenceTypes.add(referenceType);
			}
		}
		return referenceTypes;
	}

	public static String getReferenceTypeName(ReferenceType type) {
		return type.getTopleveltype().getName();
	}

	public static String getReferenceTypeForCollectionType(CollectionType type) {
		Type innerType = type.getType();
		String returnValue = "";
		if (innerType != null) {
			if (innerType.getClass() == ReferenceTypeImpl.class) {
				returnValue = SourceCodeModelUtils.getReferenceTypeName((ReferenceType) innerType);
			}
			if (innerType.getClass() == CollectionTypeImpl.class) {
				returnValue = SourceCodeModelUtils.getReferenceTypeForCollectionType((CollectionType) innerType);
			}
		}
		return returnValue;
	}

	public static Parameter getParamter(Method method, String parameterName) {
		for (Parameter parameter : method.getParameter()) {
			if (parameter.getName().equals(parameterName)) {
				return parameter;
			}
		}
		return null;
	}

	public static boolean hasMethodSignature(Class javaClass, Method method) {
		for (Field field : javaClass.getFields()) {
			if (field instanceof Method) {
				if (SourceCodeModelUtils.haveSameSignature(method, (Method) field)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean hasMethodSignature(Class javaClass, ResultMethod method) {
		for (Field field : javaClass.getFields()) {
			if (field instanceof Method) {
				if (SourceCodeModelUtils.haveSameSignature((Method) field, method)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean hasMethodSignature(Interface javaInterface, Method method) {
		for (Method interfaceMethod : javaInterface.getMethods()) {
			if (method instanceof Method) {
				if (SourceCodeModelUtils.haveSameSignature(method, interfaceMethod)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean hasMethodSignature(Interface javaInterface, ResultMethod method) {
		for (Method interfaceMethod : javaInterface.getMethods()) {
			if (SourceCodeModelUtils.haveSameSignature(interfaceMethod, method)) {
				return true;
			}
		}

		return false;
	}

	public static boolean haveSameSignature(Method method1, Method method2) {
		if (!method1.getName().equals(method2.getName())
				|| method1.getParameter().size() != method2.getParameter().size()
				|| method1.getType().equals(method2.getType())) {
			return false;
		}

		for (int i = 0; i < method1.getParameter().size(); i++) {
			if (!method1.getParameter().get(i).equals(method2.getParameter().get(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean haveSameSignature(Method method1, ResultMethod method2) {
		if (!method1.getName().equals(method2.getName())
				|| method1.getParameter().size() != method2.getParamterTypes().size()) {
			return false;
		}

		if (!sameType(method1.getType(), method2.getReturnType().getTypeString())) {
			return false;
		}

		for (int i = 0; i < method1.getParameter().size(); i++) {
			if (!sameType(method1.getParameter().get(i).getType(), method2.getParamterTypes().get(i).getTypeString())) {
				return false;
			}
		}
		return true;
	}

	public static boolean haveSameSignature(ResultMethod method1, ResultMethod method2) {
		if (!method1.getName().equals(method2.getName())
				|| method1.getParamterTypes().size() != method2.getParamterTypes().size()) {
			return false;
		}

		if (!method1.getReturnType().getTypeString().equals(method2.getReturnType().getTypeString())) {
			return false;
		}

		for (int i = 0; i < method1.getParamterTypes().size(); i++) {
			if (!method1.getParamterTypes().get(i).getTypeString()
					.equals(method2.getParamterTypes().get(i).getTypeString())) {
				return false;
			}
		}
		return true;
	}

	private static boolean sameType(Type type, String typeName) {
		if (type instanceof BuiltInType) {
			return sameBuiltInType((BuiltInType) type, typeName);
		}
		if (type instanceof CollectionType) {
			return isCollectionType(typeName);
		}
		if (type instanceof ReferenceType) {
			return sameReferenceType((ReferenceType) type, typeName);
		}

		return false;
	}

	private static boolean sameBuiltInType(BuiltInType type, String typeName) {
		if (typeName.contains("String")) {
			typeName = typeName.split(".")[2];
		}
		return type.getBuiltInType().getName().toLowerCase().equals(typeName.toLowerCase());
	}

	private static boolean sameReferenceType(ReferenceType type, String typeName) {
		return typeName.contains(type.getTopleveltype().getName());
	}

	private static boolean isCollectionType(String typeName) {
		return typeName.contains("Collection") || typeName.contains("List") || typeName.contains("ArrayList");
	}
}
