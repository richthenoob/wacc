package ic.doc.frontend.types;

public class ErrorType extends Type {

  public static final String CLASS_NAME = "ERROR";

  public int getVarSize() {
    throw new IllegalStateException("ErrorType should not have a var size!");
  }

  @Override
  public String toString() {
    return CLASS_NAME;
  }
}
