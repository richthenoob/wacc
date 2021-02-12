package ic.doc.frontend;

import ic.doc.antlr.*;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.errors.ErrorListener;
import ic.doc.frontend.errors.SemanticException;
import ic.doc.frontend.errors.SyntaxException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class WaccFrontend {

  public static final Integer SYNTAX_EXIT_CODE = 100;
  public static final Integer SEMANTIC_EXIT_CODE = 200;

  public static String parse(InputStream inputStream) throws IOException {

    CharStream charStream = CharStreams.fromStream(inputStream);

    /* Create a lexer that feeds off of input CharStream */
    BasicLexer lexer = new BasicLexer(charStream);

    /* Create a buffer of tokens pulled from the lexer */
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    /* Create a parser that feeds off the tokens buffer */
    BasicParser parser = new BasicParser(tokens);

    /* Remove ConsoleErrorListener and our custom error listener */
    parser.removeErrorListeners();
    parser.addErrorListener(new ErrorListener());

    /* Begin parsing at prog rule */
    ParseTree tree = parser.prog();

    Visitor visitor = new Visitor();
    visitor.visit(tree);

    if (!visitor.getSemanticErrorList().getSemanticErrors().isEmpty()) {
      System.err.println("Error messages from compiler:");
      visitor.getSemanticErrorList().sortErrors();
      for (int i = 0; i < visitor.getSemanticErrorList().getSemanticErrors().size(); i++) {
        System.err.println(visitor.getSemanticErrorList().getSemanticErrors().get(i));
      }
      throw new SemanticException();
    }

    return tree.toStringTree(parser);
  }

  public static void main(String[] args) throws IOException {

    /* Simple command line argument checking */
    if (args.length < 1) {
      System.out.println("Please provide a filepath.");
      return;
    }

    /* Check file exists before passing filestream to our lexer and parser */
    File file = new File(args[0]);
    if (file.exists()) {
      InputStream inputStream = new FileInputStream(file);
      try {
        System.out.println(parse(inputStream));
      } catch (SyntaxException e) {
        System.err.println(e.toString());
        System.exit(SYNTAX_EXIT_CODE);
      } catch (SemanticException e) {
        System.exit(SEMANTIC_EXIT_CODE);
      }

    } else {
      System.out.println("Invalid filepath provided: " + args[0]);
    }
  }
}
