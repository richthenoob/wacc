package ic.doc.semantics.Types;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CharType implements Type {

  public final static String CLASS_NAME = "CHAR";

  private static final List<Character> validEscapedChars
      = Arrays.asList('\0', '\b', '\t', '\n', '\f', '\r', '\"', '\'', '\\');

  public static boolean isValidChar(char c) {
    return validEscapedChars.contains(c) || Character.isAlphabetic(c);
  }

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
