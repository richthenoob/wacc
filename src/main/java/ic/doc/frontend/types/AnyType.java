package ic.doc.frontend.types;

public class AnyType extends Type {

  public static final String CLASS_NAME = "ANY";

  public int getVarSize() {
    throw new IllegalStateException("AnyType should not have a var size!");
  }

  @Override
  public String toString() {
    return "T";
  }
}
