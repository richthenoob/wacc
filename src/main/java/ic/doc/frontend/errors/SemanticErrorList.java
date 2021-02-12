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

  private void printUnderlineError(ParserRuleContext ctx, String input) {
    int line = ctx.getStart().getLine() - 1;

    Pattern pattern = Pattern.compile(".*(" + input + ").*");
    Matcher matcher = pattern.matcher(programLines[line]);

    if (!matcher.find()) {
      return;
    }

    String errorLine = programLines[line];
    System.err.println(errorLine);
    int start = matcher.start(1);
    int stop = matcher.end(1) - 1;
    for (int i = 0; i < start; i++) {
      System.err.print(" ");
    }
    if (start >= 0 && stop >= 0) {
      for (int i = start; i <= stop; i++) {
        System.err.print("^");
      }
    }
    System.err.println();
  }

  public void addException(ParserRuleContext ctx, String errorMessage) {
    semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
        + ":" + ctx.getStart().getCharPositionInLine() + " - " + errorMessage);
  }

  public void addTypeException(ParserRuleContext ctx, String input,
      String expectedType, String actualType) {

    printUnderlineError(ctx, input);

    if (actualType.equals("STRING")) {
      semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
          + ":" + ctx.getStart().getCharPositionInLine()
          + " - Incompatible type at \"" + input
          + "\". Expected type: " + expectedType
          + ". Actual type: " + actualType + ".");
    } else {
      semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
          + ":" + ctx.getStart().getCharPositionInLine()
          + " - Incompatible type at '" + input
          + "'. Expected type: " + expectedType
          + ". Actual type: " + actualType + ".");
    }
  }

  public void addSuggestion(ParserRuleContext ctx, String suggestion) {
    semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
        + ":" + ctx.getStart().getCharPositionInLine()
        + " ---- Suggestion: " + suggestion);
  }

  public void addTokenException(ParserRuleContext ctx, String token,
      String input) {
    printUnderlineError(ctx, input);
    semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
        + ":" + ctx.getStart().getCharPositionInLine()
        + " - Invalid character token '" + token + "' in string: " + input
        + ".");
  }

  public void addScopeException(ParserRuleContext ctx, Boolean presentInScope,
      String object, String input) {
    printUnderlineError(ctx, input);
    String defined = presentInScope ? " was already" : " is not";
    semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
        + ":" + ctx.getStart().getCharPositionInLine() + " - "
        + object + " " + input + defined + " defined in this scope.");
  }

  public void sortErrors() {
    String[] errors = semanticErrors.toArray(String[]::new);
    Arrays.sort(errors);
    semanticErrors = Arrays.asList(errors);
  }

}
