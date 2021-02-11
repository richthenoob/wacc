package ic.doc.semantics.Types;

import java.util.Objects;

public class BoolType implements Type {

  public final static String CLASS_NAME = "BOOL";

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
