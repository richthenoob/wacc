package ic.doc.semantics.Types;

import java.util.Objects;

public class AnyType implements Type {

  @Override
  public java.lang.String toString() {
    return "T";
  }

  @Override
  public boolean equals(Object obj) {
    return this.getClass().getGenericSuperclass()
        .equals(obj.getClass().getGenericSuperclass());
  }

  @Override
  public int hashCode() {
    System.err
        .println("WARNING: Attempting to use hashCode to compare AnyType. "
            + "This is unsafe when comparing with other types as the hashCode "
            + "will NOT be the same, even though equals is the same.");
    return Objects.hash(this.getClass().getGenericSuperclass());
  }
}
