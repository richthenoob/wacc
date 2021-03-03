package ic.doc.frontend.types;

public class ArrayType extends Type {

  public static final String CLASS_NAME = "ARRAY";
  private final Type internalType;
  private static final int VAR_SIZE = 4;

  public ArrayType(Type type) {
    this.internalType = type;
  }

  public int getVarSize() {
    return VAR_SIZE;
  }

  public Type getInternalType() {
    return internalType;
  }

  @Override
  public String toString() {
    return getInternalType() + "[]";
  }
}
