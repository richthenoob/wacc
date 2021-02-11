package ic.doc.semantics.Types;

import java.util.Objects;

public class IntType implements Type {

    public static final int INTMAX = 2147483647;
    public static final int INTMIN = -2147483648;

    @Override
    public java.lang.String toString() {
        return "INT";
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
