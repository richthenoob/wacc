package ic.doc.frontend.types;

public class StringType extends Type {

  public static final String CLASS_NAME = "STRING";
  private static final int VAR_SIZE = 4;

  public int getVarSize() {
    return VAR_SIZE;
  }


  @Override
  public String toString() {
    return CLASS_NAME;
  }
}
