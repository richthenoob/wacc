package ic.doc.semantics.Types;

import java.util.Objects;

public class StringType implements Type {

  @Override
  public java.lang.String toString() {
    return "STRING";
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
