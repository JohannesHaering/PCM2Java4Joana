package edu.kit.kastel.scbs.pcm2java4joana.sourcecodegenerator;

import edu.kit.kastel.scbs.pcm2java4joana.joana.JOANARoot;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Field;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Interface;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Method;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Parameter;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.SourceCodeRoot;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.TopLevelType;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Type;
import edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Variable;
import edu.kit.kastel.scbs.pcm2java4joana.utils.JoanaModelUtils;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.internal.xtend.util.Triplet;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;

@SuppressWarnings("all")
public class SupplierAnalysisModel2AnnotatedCodeGenerator {
  public List<Triplet<String, String, String>> generateAnnotatedCode(final SourceCodeRoot sourceCodeModel, final JOANARoot joanaModel) {
    final ArrayList<Triplet<String, String, String>> contentsForFiles = new ArrayList<Triplet<String, String, String>>();
    EList<TopLevelType> _topleveltype = sourceCodeModel.getTopleveltype();
    for (final TopLevelType topLevelType : _topleveltype) {
      {
        final String content = this.generateTopLevelType(topLevelType, joanaModel);
        String _name = topLevelType.getName();
        String _plus = (_name + ".java");
        final Triplet<String, String, String> newTuple = new Triplet<String, String, String>(content, _plus, "");
        contentsForFiles.add(newTuple);
      }
    }
    return contentsForFiles;
  }
  
  public String generateTopLevelType(final TopLevelType topLevelType, final JOANARoot joanaModel) {
    String _switchResult = null;
    boolean _matched = false;
    if (topLevelType instanceof edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Class) {
      _matched=true;
      _switchResult = this.generateClass(((edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Class)topLevelType), joanaModel);
    }
    if (!_matched) {
      if (topLevelType instanceof Interface) {
        _matched=true;
        _switchResult = this.generateInterface(((Interface)topLevelType));
      }
    }
    return _switchResult;
  }
  
  public String generateInterface(final Interface inter) {
    StringConcatenation _builder = new StringConcatenation();
    String _generatePackage = SourceCodeElementGenerator.generatePackage();
    _builder.append(_generatePackage);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    String _generateImportsInterface = SourceCodeElementGenerator.generateImportsInterface(inter.getMethods());
    _builder.append(_generateImportsInterface);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public interface ");
    String _name = inter.getName();
    _builder.append(_name);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    {
      EList<Method> _methods = inter.getMethods();
      for(final Method method : _methods) {
        _builder.append("\t");
        String _generateInterfaceMethod = this.generateInterfaceMethod(method);
        _builder.append(_generateInterfaceMethod, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String generateInterfaceMethod(final Method method) {
    StringConcatenation _builder = new StringConcatenation();
    String _generateDataType = SourceCodeElementGenerator.generateDataType(method.getType());
    _builder.append(_generateDataType);
    _builder.append(" ");
    String _name = method.getName();
    _builder.append(_name);
    _builder.append("(");
    {
      EList<Parameter> _parameter = method.getParameter();
      for(final Parameter parameter : _parameter) {
        String _generateParameter = this.generateParameter(parameter, null);
        _builder.append(_generateParameter);
        {
          int _indexOf = method.getParameter().indexOf(parameter);
          int _length = ((Object[])Conversions.unwrapArray(method.getParameter(), Object.class)).length;
          int _minus = (_length - 1);
          boolean _notEquals = (_indexOf != _minus);
          if (_notEquals) {
            _builder.append(", ");
          }
        }
      }
    }
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  public String generateClass(final edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Class sourceCodeClass, final JOANARoot joanaModel) {
    StringConcatenation _builder = new StringConcatenation();
    String _generatePackage = SourceCodeElementGenerator.generatePackage();
    _builder.append(_generatePackage);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import edu.kit.joana.ui.annotations.*;");
    _builder.newLine();
    {
      EList<Interface> _implements = sourceCodeClass.getImplements();
      for(final Interface toImport : _implements) {
        String _generateImport = SourceCodeElementGenerator.generateImport(toImport);
        _builder.append(_generateImport);
        _builder.newLineIfNotEmpty();
      }
    }
    String _generateImports = SourceCodeElementGenerator.generateImports(sourceCodeClass.getFields());
    _builder.append(_generateImports);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public class ");
    String _name = sourceCodeClass.getName();
    _builder.append(_name);
    _builder.append(" ");
    {
      int _size = sourceCodeClass.getImplements().size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        _builder.append("implements ");
        String _generateImplements = SourceCodeElementGenerator.generateImplements(sourceCodeClass.getImplements());
        _builder.append(_generateImplements);
      }
    }
    _builder.append("{");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _generateFields = this.generateFields(sourceCodeClass.getFields(), sourceCodeClass, joanaModel);
    _builder.append(_generateFields, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    String _generateConstructor = SourceCodeElementGenerator.generateConstructor(sourceCodeClass);
    _builder.append(_generateConstructor, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String generateFields(final List<Field> fields, final edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Class sourceCodeClass, final JOANARoot joanaModel) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Field field : fields) {
        String _generateField = this.generateField(field, sourceCodeClass, joanaModel);
        _builder.append(_generateField);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder.toString();
  }
  
  public String generateField(final Field field, final edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Class sourceCodeClass, final JOANARoot joanaModel) {
    String _switchResult = null;
    boolean _matched = false;
    if (field instanceof Variable) {
      _matched=true;
      _switchResult = SourceCodeElementGenerator.generateVariable(((Variable)field));
    }
    if (!_matched) {
      if (field instanceof Method) {
        _matched=true;
        _switchResult = this.generateMethod(sourceCodeClass, ((Method)field), joanaModel);
      }
    }
    return _switchResult;
  }
  
  public String generateMethod(final edu.kit.kastel.scbs.pcm2java4joana.sourcecode.Class scClass, final Method method, final JOANARoot joanaModel) {
    StringConcatenation _builder = new StringConcatenation();
    String _generateJoanaAnnotation = JoanaAnnotationsGenerator.generateJoanaAnnotation(scClass, method, joanaModel);
    _builder.append(_generateJoanaAnnotation);
    _builder.newLineIfNotEmpty();
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("public ");
    String _generateDataType = SourceCodeElementGenerator.generateDataType(method.getType());
    _builder.append(_generateDataType);
    _builder.append(" ");
    String _name = method.getName();
    _builder.append(_name);
    _builder.append("(");
    {
      EList<Parameter> _parameter = method.getParameter();
      for(final Parameter parameter : _parameter) {
        {
          int _size = JoanaModelUtils.getJoanaFlowSpecificationElementsFor(joanaModel, scClass.getName(), method.getName(), parameter.getName()).size();
          boolean _notEquals = (_size != 0);
          if (_notEquals) {
            String _generateJoanaAnnotation_1 = JoanaAnnotationsGenerator.generateJoanaAnnotation(scClass, method, parameter, joanaModel);
            _builder.append(_generateJoanaAnnotation_1);
            _builder.append(" ");
            String _generateParameter = this.generateParameter(parameter, null);
            _builder.append(_generateParameter);
            {
              int _indexOf = method.getParameter().indexOf(parameter);
              int _length = ((Object[])Conversions.unwrapArray(method.getParameter(), Object.class)).length;
              int _minus = (_length - 1);
              boolean _notEquals_1 = (_indexOf != _minus);
              if (_notEquals_1) {
                _builder.append(", ");
              }
            }
          }
        }
      }
    }
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("// TODO: Implement me!");
    _builder.newLine();
    _builder.append("\t");
    {
      Type _type = method.getType();
      boolean _tripleNotEquals = (_type != null);
      if (_tripleNotEquals) {
        _builder.append("return null;");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String generateParameter(final Parameter parameter, final JOANARoot joanaModel) {
    StringConcatenation _builder = new StringConcatenation();
    String _generateDataType = SourceCodeElementGenerator.generateDataType(parameter.getType());
    _builder.append(_generateDataType);
    _builder.append(" ");
    String _name = parameter.getName();
    _builder.append(_name);
    return _builder.toString();
  }
}
