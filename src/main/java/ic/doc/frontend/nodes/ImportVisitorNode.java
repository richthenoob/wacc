package ic.doc.frontend.nodes;

import ic.doc.antlr.BasicParser;
import ic.doc.backend.Context;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.utils.Pair;
import org.antlr.v4.runtime.ParserRuleContext;
import java.util.ArrayList;
import java.util.List;

public class ImportVisitorNode extends Node {
  /* Pair of the context and the file path so we can display better error msgs*/
  private List<Pair<BasicParser.FuncContext, String>> funcCtxs;
  private List<Pair<BasicParser.Class_Context, String>> classCtxs;

  public ImportVisitorNode(){
    funcCtxs = new ArrayList<>();
    classCtxs = new ArrayList<>();
  }

  public List<Pair<BasicParser.FuncContext, String>> getFuncCtxs(){
    return funcCtxs;
  }

  public List<Pair<BasicParser.Class_Context, String>> getClassCtxs(){
    return classCtxs;
  }

  public void addFuncCtx(BasicParser.FuncContext ctx, String filePath){
    funcCtxs.add(new Pair<>(ctx, filePath));
  }

  public void addClassCtx(BasicParser.Class_Context ctx, String filePath){
    classCtxs.add(new Pair<>(ctx, filePath));
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

  }

  @Override
  public void translate(Context context) {

  }
}
