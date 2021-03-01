package ic.doc.frontend.types;

public class IntType extends Type {

  public static final long INT_MAX = (long) (Math.pow(2, 31) - 1);
  public static final long INT_MIN = (long) -Math.pow(2, 31);

  public static final String CLASS_NAME = "INT";
  private static final int VAR_SIZE = 4;

  public int getVarSize() {
    return VAR_SIZE;
  }

  @Override
  public String toString() {
    return CLASS_NAME;
  }


}
