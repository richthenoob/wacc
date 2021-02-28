package ic.doc.backend;

public class ErrorMessage {
  public static final String OVERFLOW = "OverflowError: the result is too small/large to store in a " +
          "4-byte signed-integer.\\n";

  public static final String DIVIDE_BY_ZERO = "DivideByZeroError: divide or modulo by zero\\n\\0";

  public static final String NULL_REFERENCE = "NullReferenceError: dereference a null reference\\n\\0";

  public static final String ARRAY_IDX_OUT_OF_BOUNDS_NEGATIVE = "ArrayIndexOutOfBoundsError: negative index\\n\\0";

  public static final String ARRAY_IDX_OUT_OF_BOUNDS_LARGE = "ArrayIndexOutOfBoundsError: index too large\\n\\0";

}
