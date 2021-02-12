package ic.doc.frontend.types;

import java.util.Objects;

public class IntType extends Type {

  public static final long INT_MAX = (long) (Math.pow(2, 31) - 1);
  public static final long INT_MIN = (long) -Math.pow(2, 31);

  public final static String CLASS_NAME = "INT";

  @Override
  public String toString() {
    return CLASS_NAME;
  }


}
