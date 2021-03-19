package ic.doc.frontend.utils;

import ic.doc.antlr.BasicLexer;
import ic.doc.antlr.BasicParser;
import ic.doc.frontend.errors.ErrorListener;
import ic.doc.frontend.errors.SemanticException;
import ic.doc.frontend.nodes.ImportVisitorNode;
import ic.doc.frontend.nodes.ProgNode;
import ic.doc.frontend.semantics.ImportVisitor;
import ic.doc.frontend.semantics.Visitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Set;

public class fsUtils {
  public static ParseTree parseWaccFile(InputStream inputStream) throws IOException {
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
    return tree;
  }

  public static ProgNode parseRootFile(String filePath, InputStream inputStream) throws IOException {

    ParseTree tree = parseWaccFile(inputStream);

    Visitor visitor = new Visitor(filePath);
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

  public static ImportVisitorNode parseImportedFile(String filePath, Set<String> imports) throws IOException, IllegalArgumentException {
    File file = new File(filePath);
    if (!file.exists()){
      String fileName = Paths.get(filePath).getFileName().toString();
      throw new IllegalArgumentException("Imported file not found: " + fileName + ".");
    }
    InputStream inputStream = new FileInputStream(file);
    ParseTree tree = parseWaccFile(inputStream);

    /* baseDirectory */
    String baseDirectory = (Paths.get(filePath)).getParent().toString();

    ImportVisitor visitor = new ImportVisitor(baseDirectory, imports);
    ImportVisitorNode node = (ImportVisitorNode) visitor.visit(tree);

    return node;
  }

}
