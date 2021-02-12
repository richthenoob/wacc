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
   * Section 9.2 Altering and Redirecting ANTLR Error Messages. */
  private String getUnderlineError(ParserRuleContext ctx, String input) {

    StringBuilder stringBuilder = new StringBuilder("\n");

    /* Look for string of interest in provided line. */
    int line = ctx.getStart().getLine() - 1;
    String errorLine = programLines[line];
    Pattern pattern = Pattern.compile(".*(" + input + ").*");
    Matcher matcher = pattern.matcher(programLines[line]);

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

  public void addException(ParserRuleContext ctx, String errorMessage) {
    semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
        + ":" + ctx.getStart().getCharPositionInLine() + " - " + errorMessage);
  }

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

  public void addSuggestion(ParserRuleContext ctx, String suggestion) {
    semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
        + ":" + ctx.getStart().getCharPositionInLine()
        + " ---- Suggestion: " + suggestion);
  }

  public void addTokenException(ParserRuleContext ctx, String token,
      String input) {
    String underlineError = getUnderlineError(ctx, input);

    semanticErrors.add(
        "Semantic error at line " + ctx.getStart().getLine()
            + ":" + ctx.getStart().getCharPositionInLine()
            + " - Invalid character token '" + token + "' in string: " + input
            + "." + underlineError);
  }

  public void addScopeException(ParserRuleContext ctx, Boolean presentInScope,
      String object, String input) {
    String underlineError = getUnderlineError(ctx, input);
    String defined = presentInScope ? " was already" : " is not";
    semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
        + ":" + ctx.getStart().getCharPositionInLine() + " - "
        + object + " " + input + defined + " defined in this scope."
        + underlineError);
  }

  public void sortErrors() {
    String[] errors = semanticErrors.toArray(String[]::new);
    Arrays.sort(errors);
    semanticErrors = Arrays.asList(errors);
  }

}
