package ic.doc.frontend.identifiers;

import ic.doc.frontend.nodes.ClassNode;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.types.ClassType;
import ic.doc.frontend.types.Type;

public class ClassIdentifier extends Identifier {

  private final String className;
  private ClassNode classNode;
  private final SymbolTable classSymbolTable;
  private final String immediateSuperClass;

  public ClassIdentifier(String className, SymbolTable classSymbolTable, String immediateSuperClass) {
    super(new ClassType(className));
    this.className = className;
    this.classSymbolTable = classSymbolTable;
    this.immediateSuperClass = immediateSuperClass;
  }

  public String getClassName() {
    return className;
  }

  public String getImmediateSuperClass() {
    return immediateSuperClass;
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

  @Override
  public String toString() {
    /* CLASS: className */
    return "CLASS: " + className;
  }
}
