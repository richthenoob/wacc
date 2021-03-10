package ic.doc.frontend.identifiers;

import ic.doc.frontend.types.ClassType;
import ic.doc.frontend.types.Type;
import java.util.Map;

public class ClassIdentifier extends Identifier {

  private final String className;
  private final int sizeOfClass;
  private final Map<String, Type> fieldTypes;

  public ClassIdentifier(String className, int sizeOfClass,
      Map<String, Type> fieldTypes) {
    super(new ClassType(className));
    this.className = className;
    this.sizeOfClass = sizeOfClass;
    this.fieldTypes = fieldTypes;
  }

  public int getSizeOfClass() {
    return sizeOfClass;
  }

  public Type getTypeOfField(String fieldName) {
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
