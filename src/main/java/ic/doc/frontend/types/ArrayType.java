package ic.doc.frontend.types;

import java.util.Objects;

public class ArrayType extends Type {

  public final static String CLASS_NAME = "ARRAY";
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
