package ic.doc.frontend.identifiers;

import ic.doc.frontend.nodes.ClassNode;
import ic.doc.frontend.types.ClassType;
import ic.doc.frontend.types.Type;
import java.util.Map;

public class ClassIdentifier extends Identifier {

  private final String className;
  private final ClassNode classNode;

  public ClassIdentifier(String className, ClassNode classNode) {
    super(new ClassType(className));
    this.className = className;
    this.classNode = classNode;
  }

  public int getSizeOfClass() {
    return classNode.getClassSymbolTable().getTableSize();
  }

  public Type getTypeOfField(String fieldName) {
    Map<String, Type> fieldTypes = classNode.getClassFields();
    if (!fieldTypes.containsKey(fieldName)) {
      throw new IllegalArgumentException(
          "CLASS " + className + "does not contain field \"" + fieldName
              + "\"!");
    }
    return fieldTypes.get(fieldName);
  }

  @Override
  public String toString() {
    /* CLASS: className */
    return "CLASS: " + className;
  }
}
