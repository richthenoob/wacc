package ic.doc.frontend.types;

import java.util.Arrays;
import java.util.List;

public class CharType extends Type {

  public static final String CLASS_NAME = "CHAR";
  private static final int VAR_SIZE = 1;

  public int getVarSize() {
    return VAR_SIZE;
  }

  private static final List<Character> validEscapedChars =
      Arrays.asList('\0', '\b', '\t', '\n', '\f', '\r', '\"', '\'', '\\');

  public static boolean isValidChar(char c) {
    return validEscapedChars.contains(c) || Character.isAlphabetic(c);
  }

  @Override
  public String toString() {
    return CLASS_NAME;
  }
}
