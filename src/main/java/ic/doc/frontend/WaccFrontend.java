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

import static ic.doc.frontend.utils.fsUtils.parseRootFile;
import static ic.doc.frontend.utils.fsUtils.parseWaccFile;

public class WaccFrontend {

  /* Optimization tag for comparison */
  public static boolean OPTIMIZE = true;

  public static final Integer SYNTAX_EXIT_CODE = 100;
  public static final Integer SEMANTIC_EXIT_CODE = 200;

  public static void main(String[] args) throws IOException {

    /* Simple command line argument checking */
    if (args.length < 1) {
      System.out.println("Please provide a filepath.");
      return;
    }

    /* Check file exists before passing filestream to our lexer and parser */
    String filename = args[0];
    System.out.println(filename);
    File file = new File(filename);
    if (file.exists()) {
      InputStream inputStream = new FileInputStream(file);
      try {
        /* Run through frontend. */
        ProgNode rootNode = parseRootFile(file.getCanonicalPath(), inputStream);

        /* Run through backend. */
        WaccBackend wrapper = WaccBackend.generateCode(rootNode);
        String output = wrapper.getOutput();
        int instructCount = wrapper.getInstructCount();

        /* Strips .wacc file extension and adds .s before writing to file. */
        Path p = Paths.get(filename);
        String outputFileName = p.getFileName().toString().replaceFirst("[.][^.]+$", "");;
        WaccBackend.writeToFile(outputFileName + ".s", output);

        System.out.println(output);
        System.out.println("Total number of instructions" + instructCount);

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
