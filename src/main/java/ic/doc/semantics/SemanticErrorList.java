package ic.doc.semantics;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class SemanticErrorList {

  private List<String> semanticErrors;

  public SemanticErrorList() {
    semanticErrors = new LinkedList<>();
  }

  public List<String> getSemanticErrors() {
    return semanticErrors;
  }

  public void addException(ParserRuleContext ctx, String errorMessage) {
    semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
        + ":" + ctx.getStart().getCharPositionInLine() + " - " + errorMessage);
  }

  public void addTypeException(ParserRuleContext ctx, String input,
      String expectedType, String actualType) {
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

  public void addSuggestion(String suggestion) {
    semanticErrors.add(suggestion);
  }

  public void addTokenException(ParserRuleContext ctx, String token,
      String input) {
    semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
        + ":" + ctx.getStart().getCharPositionInLine()
        + " - Invalid character token '" + token + "' in string: " + input
        + ".");
  }

  public void sortErrors() {
    String[] errors = semanticErrors.toArray(String[]::new);
    Arrays.sort(errors);
    semanticErrors = Arrays.asList(errors);
  }

}
