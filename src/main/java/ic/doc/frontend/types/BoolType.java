package ic.doc.frontend.types;

public class BoolType extends Type {

  public static final String CLASS_NAME = "BOOL";
  private static final int VAR_SIZE = 1;

  public int getVarSize() {
    return VAR_SIZE;
  }

  @Override
  public String toString() {
    return CLASS_NAME;
  }
}
