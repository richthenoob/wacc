package ic.doc.frontend.types;

public class ArrayType extends Type {

  public static final String CLASS_NAME = "ARRAY";
  private final Type internalType;

  public ArrayType(Type type) {
    this.internalType = type;
  }

  public Type getInternalType() {
    return internalType;
  }

  @Override
  public String toString() {
    return getInternalType() + "[]";
  }
}
