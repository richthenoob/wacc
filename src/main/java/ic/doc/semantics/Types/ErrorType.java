package ic.doc.semantics.Types;

public class ErrorType implements Type {

  @Override
  public java.lang.String toString() {
    return "ERROR";
  }

  @Override
  public int hashCode() {
    return 42;
  }

  @Override
  public boolean equals(Object obj) {
    /* Error types should never be equal any other types,
     * even if the other type is an error type as well. */
    return false;
  }
}
