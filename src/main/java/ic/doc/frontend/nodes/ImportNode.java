package ic.doc.frontend.nodes;

import ic.doc.backend.Context;
import ic.doc.frontend.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class ImportNode extends Node {
  private String fileName;

  public ImportNode(String fileName){
    this.fileName = fileName;
  }

  public String getFileName(){
    return fileName;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

  }

  @Override
  public void translate(Context context) {

  }
}
