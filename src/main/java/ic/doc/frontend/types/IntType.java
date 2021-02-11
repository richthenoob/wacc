package ic.doc.frontend.types;

import java.util.Objects;

public class IntType implements Type {

  public static final long INT_MAX = (long) (Math.pow(2, 31) - 1);
  public static final long INT_MIN = (long) -Math.pow(2, 31);

  public final static String CLASS_NAME = "INT";

  @Override
  public String toString() {
    return CLASS_NAME;
  }

  @Override
  public boolean equals(Object obj) {
    /* AnyType is considered the same type as any other type classes. */
    if (obj.getClass().equals(AnyType.class)) {
      return true;
    }
    return this.getClass().equals(obj.getClass());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getClass());
  }
}
