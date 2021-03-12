package ic.doc.frontend.identifiers;

import ic.doc.frontend.nodes.ClassNode;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.types.ClassType;
import ic.doc.frontend.types.Type;

public class ClassIdentifier extends Identifier {

  private final String className;
  private ClassNode classNode;
  private final SymbolTable classSymbolTable;

  public ClassIdentifier(String className, SymbolTable classSymbolTable) {
    super(new ClassType(className));
    this.className = className;
    this.classSymbolTable = classSymbolTable;
  }

  public ClassNode getClassNode() {
    if (classNode == null) {
      throw new IllegalStateException(
          "Class " + className + " declared but not yet visited!");
    }
    return classNode;
  }

  public SymbolTable getClassSymbolTable() {
    return classSymbolTable;
  }

  public void setClassNode(ClassNode classNode) {
    this.classNode = classNode;
  }

  public int getSizeOfClass() {
    return classNode.getClassSymbolTable().getTableSize();
  }

  // Disabled for now, since ClassNode only holds a list of
  // ParamNodes, not a Map<String, Type>
//  public Type getTypeOfField(String fieldName) {
//    Map<String, Type> fieldTypes = classNode.getClassFields();
//    if (!fieldTypes.containsKey(fieldName)) {
//      throw new IllegalArgumentException(
//          "CLASS " + className + "does not contain field \"" + fieldName
//              + "\"!");
//    }
//    return fieldTypes.get(fieldName);
//  }

  @Override
  public String toString() {
    /* CLASS: className */
    return "CLASS: " + className;
  }
}
