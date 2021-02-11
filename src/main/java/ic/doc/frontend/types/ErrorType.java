package ic.doc.frontend.types;

public class ErrorType implements Type {

  public final static String CLASS_NAME = "ERROR";

  @Override
  public String toString() {
    return CLASS_NAME;
  }

  @Override
  public int hashCode() {
    /* Two error types are only equal to each other if they are the same object,
     * which SHOULD NOT happen. */
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    /* Error types should never be equal any other types,
     * even if the other type is an error type as well.
     * WARNING: Note that this breaks reflexivity in java's equality, since
     *          two instances should always be equal to each other.  */
    return false;
  }
}
