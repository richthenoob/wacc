package ic.doc.semantics.StatNodes;

import ic.doc.semantics.ExprNodes.ExprNode;
import ic.doc.semantics.Visitor;
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

  }
}
