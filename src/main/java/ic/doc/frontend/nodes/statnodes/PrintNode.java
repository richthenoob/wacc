package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.Visitor;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class PrintNode extends StatNode {

  private final ExprNode exprNode;
  private final Boolean newLine;

  public PrintNode(ExprNode exprNode, Boolean newLine) {
    this.exprNode = exprNode;
    this.newLine = newLine;
  }

  public ExprNode getExprNode() {
    return exprNode;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public void translate(Context context) {}
}
