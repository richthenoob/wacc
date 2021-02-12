package ic.doc.frontend.errors;

import org.antlr.v4.runtime.*;

/* ErrorListener class adapted from The Definitive Antlr4 Reference,
 * Section 9.2 Altering and Redirecting ANTLR Error Messages. */
public class ErrorListener extends BaseErrorListener {

  @Override
  public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
      int line, int charPositionInLine, String msg,
      RecognitionException e) {
    underlineError(recognizer, (Token) offendingSymbol,
        line, charPositionInLine);
    throw new SyntaxException(msg, line, charPositionInLine);
  }

  protected void underlineError(Recognizer recognizer,
      Token offendingToken, int line,
      int charPositionInLine) {
    CommonTokenStream tokens = (CommonTokenStream) recognizer
        .getInputStream();
    String input = tokens.getTokenSource().getInputStream().toString();
    String[] lines = input.split("\n");

    /* Print line where error was detected. */
    String errorLine;
    if (line - 1 == lines.length) {
      errorLine = lines[line - 2];
    } else {
      errorLine = lines[line - 1];
    }
    System.err.println(errorLine);

    /* Print line containing carets. */
    for (int i = 0; i < charPositionInLine; i++) {
      System.err.print(" ");
    }
    int start = offendingToken.getStartIndex();
    int stop = offendingToken.getStopIndex();
    if (start >= 0 && stop >= 0) {
      for (int i = start; i <= stop; i++) {
        System.err.print("^");
      }
    }
    System.err.println();
  }

}
