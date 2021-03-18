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
  private List<BasicParser.Class_Context> classCtxs;

  public ImportVisitorNode(){
    funcCtxs = new ArrayList<>();
    classCtxs = new ArrayList<>();
  }

  public List<BasicParser.FuncContext> getFuncCtxs(){
    return funcCtxs;
  }

  public List<BasicParser.Class_Context> getClassCtxs(){
    return classCtxs;
  }

  public void addFuncCtx(BasicParser.FuncContext ctx){
    funcCtxs.add(ctx);
  }

  public void addClassCtx(BasicParser.Class_Context ctx){
    classCtxs.add(ctx);
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

  }

  @Override
  public void translate(Context context) {

  }
}
