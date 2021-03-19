package ic.doc.frontend.errors;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.antlr.v4.runtime.ParserRuleContext;

public class SemanticErrorList {

  private List<String> semanticErrors;
  private final String[] programLines;
  private String currFile;

  public SemanticErrorList(String[] programLines, String currFile) {
    semanticErrors = new ArrayList<>();
    this.programLines = programLines;
    this.currFile = Paths.get(currFile).getFileName().toString();
  }

  public void setCurrFile(String file){
    this.currFile = Paths.get(file).getFileName().toString();
  }

  public List<String> getSemanticErrors() {
    return semanticErrors;
  }

  /* Adapted from The Definitive Antlr4 Reference,
   * Section 9.2 Altering and Redirecting ANTLR Error Messages. */
  private String getUnderlineError(ParserRuleContext ctx, String input) {

    StringBuilder stringBuilder = new StringBuilder("\n");

    /* Look for string of interest in provided line. */
    int line = ctx.getStart().getLine() - 1;
    String errorLine = programLines[line];
    Pattern pattern = Pattern.compile(".*(" + Pattern.quote(input) + ").*");
    Matcher matcher = pattern.matcher(errorLine);

    if (!matcher.find()) {
      return "";
    }

    /* Print line where error was detected. */
    stringBuilder.append(errorLine);
    stringBuilder.append("\n");

    /* Print line containing carets. */
    int start = matcher.start(1);
    int stop = matcher.end(1) - 1;
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
    semanticErrors.add(
        "Semantic error at line "
            + ctx.getStart().getLine()
            + ":"
            + ctx.getStart().getCharPositionInLine()
            + " - "
            + errorMessage
            + " at file: "
            + currFile);
  }

  /* Adds an error message for mismatched types
   * together with its line and character position.
   * Appends careted code and suggestion (if not empty) */
  public void addTypeException(
      ParserRuleContext ctx,
      String input,
      String expectedType,
      String actualType,
      String suggestion) {

    String underlineError = getUnderlineError(ctx, input);
    String sugg = "";
    if (!suggestion.isEmpty()) {
      sugg = "Suggestion: " + suggestion;
    }
    String apostrophe = "'";
    if (actualType.equals("STRING")) {
      apostrophe = "\"";
    }
    semanticErrors.add(
        "Semantic error at line "
            + ctx.getStart().getLine()
            + ":"
            + ctx.getStart().getCharPositionInLine()
            + " at file: "
            + currFile
            + " - Incompatible type at "
            + apostrophe
            + input
            + apostrophe
            + ". Expected type: "
            + expectedType
            + ". Actual type: "
            + actualType
            + "."
            + underlineError
            + sugg);
  }

  /* Adds an error message for mismatched types
   * together with its line and character position.
   * Appends careted code and suggestion (if not empty)
   * Appends the operator or statement where the error occurred */

  public void addTypeException(
      ParserRuleContext ctx,
      String input,
      String expectedType,
      String actualType,
      String suggestion,
      String errorOperation) {

    String underlineError = getUnderlineError(ctx, input);
    String sugg = "";
    if (!suggestion.isEmpty()) {
      sugg = "Suggestion: " + suggestion;
    }
    String apostrophe = "'";
    if (actualType.equals("STRING")) {
      apostrophe = "\"";
    }
    semanticErrors.add(
        "Semantic error at line "
            + ctx.getStart().getLine()
            + ":"
            + ctx.getStart().getCharPositionInLine()
            + " at file: "
            + currFile
            + " - Incompatible type at "
            + apostrophe
            + input
            + apostrophe
            + " for "
            + errorOperation
            + ". Expected type: "
            + expectedType
            + ". Actual type: "
            + actualType
            + "."
            + underlineError
            + sugg);
  }

  /* Adds an error message for invalid character tokens
   * together with its line and character position.
   * Appends careted code*/
  public void addTokenException(ParserRuleContext ctx, String token, String input) {
    String underlineError = getUnderlineError(ctx, input);

    semanticErrors.add(
        "Semantic error at line "
            + ctx.getStart().getLine()
            + ":"
            + ctx.getStart().getCharPositionInLine()
            + " at file: "
            + currFile
            + " - Invalid character token '"
            + token
            + "' in string: "
            + input
            + "."
            + underlineError);
  }

  /* Adds an error message for definition errors
   * together with its line and character position.
   * Appends careted code */
  public void addScopeException(
      ParserRuleContext ctx, Boolean presentInScope, String object, String input) {
    String underlineError = getUnderlineError(ctx, input);
    String defined = presentInScope ? " was already" : " is not";
    semanticErrors.add(
        "Semantic error at line "
            + ctx.getStart().getLine()
            + ":"
            + ctx.getStart().getCharPositionInLine()
            + " at file: "
            + currFile
            + " - "
            + object
            + " "
            + input
            + defined
            + " defined in this scope."
            + underlineError);
  }

  /* Sorts list of errors according to line and character position */
  public void sortErrors() {
    String[] errors = semanticErrors.toArray(String[]::new);
    Arrays.sort(errors);
    semanticErrors = Arrays.asList(errors);
  }
}
