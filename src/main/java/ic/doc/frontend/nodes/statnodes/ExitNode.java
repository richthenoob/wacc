package ic.doc.frontend.nodes.statnodes;

import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.types.IntType;
import ic.doc.frontend.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class ExitNode extends StatNode {

  private final ExprNode exprNode;

  public ExitNode(ExprNode exprNode) {
    this.exprNode = exprNode;
  }

  public ExprNode getExprNode() {
    return exprNode;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* The type of the expression given to the exit statement must be an integer. */
    if (!(exprNode.getType() instanceof IntType)) {
      visitor.getSemanticErrorList()
          .addTypeException(ctx, exprNode.getInput(), "INT", exprNode.getType().toString(), "");
    }
  }

}
