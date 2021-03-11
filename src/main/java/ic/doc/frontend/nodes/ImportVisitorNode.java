package ic.doc.frontend.nodes;

import ic.doc.antlr.BasicLexer;
import ic.doc.antlr.BasicParser;
import ic.doc.backend.Context;
import ic.doc.backend.WaccBackend;
import ic.doc.frontend.errors.ErrorListener;
import ic.doc.frontend.errors.SemanticException;
import ic.doc.frontend.errors.SyntaxException;
import ic.doc.frontend.semantics.ImportVisitor;
import ic.doc.frontend.semantics.Visitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImportVisitorNode extends Node {
  private List<BasicParser.FuncContext> funcCtxs;

  public ImportVisitorNode(){
    funcCtxs = new ArrayList<>();
  }

  public List<BasicParser.FuncContext> getFuncCtxs(){
    return funcCtxs;
  }

  public void addFuncCtx(BasicParser.FuncContext ctx){
    funcCtxs.add(ctx);
  }

  public static List<BasicParser.FuncContext> magicallyParse(String filename) throws IOException, IllegalArgumentException {
    File file = new File(filename);
    if (!file.exists()){
      throw new IllegalArgumentException(filename + "not found.");
    }
    InputStream inputStream = new FileInputStream(file);
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

    /* baseDirectory */
    int idx = 0;
    for(int i = filename.length() - 1; i >= 0; i--){
      char c = filename.charAt(i);
      if(c == '/'){
        idx = i;
        break;
      }
    }

    String baseDirectory = filename.substring(0, idx);
    if(baseDirectory.length() != 0){
      baseDirectory = baseDirectory + "/";
    }
    ImportVisitor visitor = new ImportVisitor(baseDirectory);
    ImportVisitorNode rootNode = (ImportVisitorNode) visitor.visit(tree);

    return rootNode.getFuncCtxs();
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

  }

  @Override
  public void translate(Context context) {

  }
}
