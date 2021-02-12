package ic.doc.frontend.errors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.antlr.v4.runtime.ParserRuleContext;

public class SemanticErrorList {

  private List<String> semanticErrors;
  private final String[] programLines;

  public SemanticErrorList(String[] programLines) {
    semanticErrors = new ArrayList<>();
    this.programLines = programLines;
  }

  public List<String> getSemanticErrors() {
    return semanticErrors;
  }

  /* Adapted from The Definitive Antlr4 Reference,
   * Section 9.2 Altering and Redirecting ANTLR Error Messages.
   *
   * Note that we have to do some special regex escaping of characters here.
   * Consider the error line: int i = i || i + i
   *
   * There are 2 problems:
   * 1) We want to find the RHS of the '||' operator, 'i + i' but if we just pass
   * the raw input into the pattern, it will treat '+' as a special character.
   * So we have to wrap 'i + i' (assigned to variable input below) in
   * Pattern.quote(input) to escape all special characters in the input.
   *
   * 2) We want to find the LHS of the '||' operator, 'i'. Since there are so
   * many 'i's in the error line, we have to get the start and end charPosition
   * of the context and only search using regular expressions there.
   * contextStart and contextEnd helps us get the substring of the correct
   * part of the line we want, (i || i + i). */
  private String getUnderlineError(ParserRuleContext ctx, String input) {

    StringBuilder stringBuilder = new StringBuilder("\n");
    int contextStart = ctx.getStart().getCharPositionInLine();
    int contextEnd = ctx.getStop().getCharPositionInLine();

    /* Look for string of interest in provided line. */
    int line = ctx.getStart().getLine() - 1;
    String errorLine = programLines[line];
    String substringLine = errorLine.substring(contextStart, contextEnd + 1);
    Pattern pattern = Pattern.compile(".*(" + Pattern.quote(input) + ").*");
    Matcher matcher = pattern.matcher(substringLine);

    if (!matcher.find()) {
      return "";
    }

    /* Print line where error was detected. */
    stringBuilder.append(errorLine);
    stringBuilder.append("\n");

    /* Print line containing carets. */
    int start = matcher.start(1) + contextStart;
    int stop = matcher.end(1) - 1 + contextStart;
    for (int i = 0; i < start; i++) {
      stringBuilder.append(" ");
    }
    if (start >= 0 && stop >= 0) {
      for (int i = start; i <= stop; i++) {
        stringBuilder.append("^");
      }
    }

    stringBuilder.append("\n");
    return stringBuilder.toString();
  }

  /* Adds a custom error message together with its line and character position */
  public void addException(ParserRuleContext ctx, String errorMessage) {
    semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
        + ":" + ctx.getStart().getCharPositionInLine() + " - " + errorMessage);
  }

  /* Adds an error message for mismatched types
   * together with its line and character position.
   * Appends careted code and suggestion (if not empty) */
  public void addTypeException(ParserRuleContext ctx, String input,
      String expectedType, String actualType, String suggestion) {

    String underlineError = getUnderlineError(ctx, input);
    String sugg = "";
    if (!suggestion.isEmpty()) {
      sugg = "Suggestion: " + suggestion;
    }

    if (actualType.equals("STRING")) {
      semanticErrors.add(
          "Semantic error at line " + ctx.getStart().getLine()
              + ":" + ctx.getStart().getCharPositionInLine()
              + " - Incompatible type at \"" + input
              + "\". Expected type: " + expectedType
              + ". Actual type: " + actualType + "."
              + underlineError + sugg);
    } else {
      semanticErrors.add(
          "Semantic error at line " + ctx.getStart().getLine()
              + ":" + ctx.getStart().getCharPositionInLine()
              + " - Incompatible type at '" + input
              + "'. Expected type: " + expectedType
              + ". Actual type: " + actualType + "."
              + underlineError + sugg);
    }
  }

  /* Adds an error message for mismatched types
   * together with its line and character position.
   * Appends careted code and suggestion (if not empty)
   * Appends the operator or statement where the error occurred */

  public void addTypeException(ParserRuleContext ctx, String input,
      String expectedType, String actualType, String suggestion,
      String errorOperation) {

    String underlineError = getUnderlineError(ctx, input);
    String sugg = "";
    if (!suggestion.isEmpty()) {
      sugg = "Suggestion: " + suggestion;
    }

    if (actualType.equals("STRING")) {
      semanticErrors.add(
          "Semantic error at line " + ctx.getStart().getLine()
              + ":" + ctx.getStart().getCharPositionInLine()
              + " - Incompatible type at \"" + input
              + "\"" + " for " + errorOperation + ".Expected type: "
              + expectedType
              + ". Actual type: " + actualType + "."
              + underlineError + sugg);
    } else {
      semanticErrors.add(
          "Semantic error at line " + ctx.getStart().getLine()
              + ":" + ctx.getStart().getCharPositionInLine()
              + " - Incompatible type at '" + input + "' for " + errorOperation
              + ". Expected type: " + expectedType
              + ". Actual type: " + actualType + "."
              + underlineError + sugg);
    }
  }

  /* Adds an error message for invalid character tokens
   * together with its line and character position.
   * Appends careted code*/
  public void addTokenException(ParserRuleContext ctx, String token,
      String input) {
    String underlineError = getUnderlineError(ctx, input);

    semanticErrors.add(
        "Semantic error at line " + ctx.getStart().getLine()
            + ":" + ctx.getStart().getCharPositionInLine()
            + " - Invalid character token '" + token + "' in string: " + input
            + "." + underlineError);
  }

  /* Adds an error message for definition errors
   * together with its line and character position.
   * Appends careted code */
  public void addScopeException(ParserRuleContext ctx, Boolean presentInScope,
      String object, String input) {
    String underlineError = getUnderlineError(ctx, input);
    String defined = presentInScope ? " was already" : " is not";
    semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
        + ":" + ctx.getStart().getCharPositionInLine() + " - "
        + object + " " + input + defined + " defined in this scope."
        + underlineError);
  }

  /* Sorts list of errors according to line and character position */
  public void sortErrors() {
    String[] errors = semanticErrors.toArray(String[]::new);
    Arrays.sort(errors);
    semanticErrors = Arrays.asList(errors);
  }

}
