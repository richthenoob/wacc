package ic.doc;

import ic.doc.antlr.*;
import ic.doc.semantics.Visitor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class WaccFrontend {

  public static final Integer SYNTAX_EXIT_CODE = 100;
  public static final Integer SEMANTIC_EXIT_CODE = 200;

  public static String parseFromString(String string) {
    return parse(CharStreams.fromString(string));
  }

  public static String parseFromInputStream(InputStream inputStream)
      throws IOException {
    return parse(CharStreams.fromStream(inputStream));
  }

  private static String parse(CharStream input) {
    // create a lexer that feeds off of input CharStream
    BasicLexer lexer = new BasicLexer(input);

    // create a buffer of tokens pulled from the lexer
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    // create a parser that feeds off the tokens buffer
    BasicParser parser = new BasicParser(tokens);

    // remove ConsoleErrorListener
    parser.removeErrorListeners();
    // add ours
    parser.addErrorListener(new ErrorListener());

    ParseTree tree = parser.prog(); // begin parsing at prog rule

    Visitor visitor = new Visitor();
    visitor.visit(tree);

    if (!visitor.getSemanticErrorList().getSemanticErrors().isEmpty()) {
      System.err.println("Error messages from compiler:");
      for (int i = visitor.getSemanticErrorList().getSemanticErrors().size() - 1; i > -1; i--) {
        System.err.println(visitor.getSemanticErrorList().getSemanticErrors().get(i));
      }
      throw new SemanticException();
    }

    return tree.toStringTree(parser);
  }

  public static void main(String[] args) throws IOException {

    // Simple command line argument checking
    if (args.length < 1) {
      System.out.println("Please provide a filepath.");
      return;
    }

    // Check file exists before passing filestream to our lexer and parser
    File file = new File(args[0]);
    if (file.exists()) {
      InputStream inputStream = new FileInputStream(file);
      try {
        System.out.println(parseFromInputStream(inputStream));
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
