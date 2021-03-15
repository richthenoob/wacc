package ic.doc.frontend;

import ic.doc.backend.WaccBackend;
import ic.doc.antlr.BasicLexer;
import ic.doc.antlr.BasicParser;
import ic.doc.frontend.errors.ErrorListener;
import ic.doc.frontend.errors.SemanticException;
import ic.doc.frontend.errors.SyntaxException;
import ic.doc.frontend.nodes.ProgNode;
import ic.doc.frontend.semantics.Visitor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class WaccFrontend {

  /* Optimization tag for comparison */
  public static boolean OPTIMIZE = true;

  public static final Integer SYNTAX_EXIT_CODE = 100;
  public static final Integer SEMANTIC_EXIT_CODE = 200;

  public static ProgNode parse(InputStream inputStream) throws IOException {

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
    ProgNode rootNode = (ProgNode) visitor.visit(tree);

    if (!visitor.getSemanticErrorList().getSemanticErrors().isEmpty()) {
      System.err.println("Error messages from compiler:");
      visitor.getSemanticErrorList().sortErrors();
      for (int i = 0;
          i < visitor.getSemanticErrorList().getSemanticErrors().size();
          i++) {
        System.err.println(visitor.getSemanticErrorList()
            .getSemanticErrors()
            .get(i));
      }
      throw new SemanticException();
    }

    return rootNode;
  }

  public static void main(String[] args) throws IOException {

    /* Simple command line argument checking */
    if (args.length < 1) {
      System.out.println("Please provide a filepath.");
      return;
    }

    /* Check file exists before passing filestream to our lexer and parser */
    String filename = args[0];
    File file = new File(filename);
    if (file.exists()) {
      InputStream inputStream = new FileInputStream(file);
      try {
        ProgNode rootNode = parse(inputStream);
        WaccBackend wrapper = WaccBackend.generateCode(rootNode);
        String output = wrapper.getOutput();
        int instructCount = wrapper.getInstructCount();
        /* Generate code with no optimization */
        OPTIMIZE = false;
        ProgNode oldRootNode = parse(inputStream);
        WaccBackend oldWrapper = WaccBackend.generateCode(oldRootNode);
        int oldInstructCount = oldWrapper.getInstructCount();
        /* Strips .wacc file extension and adds .s before writing to file. */
        Path p = Paths.get(filename);
        String outputFileName = p.getFileName().toString().replaceFirst("[.][^.]+$", "");;
        WaccBackend.writeToFile(outputFileName + ".s", output);
        System.out.println(output);
        System.out.println("Total number of instructions in optimized: " + instructCount);
        System.out.println("Total number of instructions in original: " + oldInstructCount);
        System.out.println("Reduction : " + (oldInstructCount - instructCount));

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
