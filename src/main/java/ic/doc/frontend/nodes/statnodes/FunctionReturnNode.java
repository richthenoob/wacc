package ic.doc.frontend.nodes.statnodes;

import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.types.Type;
import ic.doc.frontend.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class FunctionReturnNode extends StatNode {

  private final ExprNode exprNode;
  private final Boolean main;
  private final Type functionType;

  public FunctionReturnNode(ExprNode exprNode, Boolean main,
      Type functionType) {
    this.exprNode = exprNode;
    this.main = main;
    this.functionType = functionType;
  }

  public ExprNode getExprNode() {
    return exprNode;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Returns can only be present in the body of a non-main function */
    if (main) {
      visitor.getSemanticErrorList()
          .addException(ctx, "Cannot return from the global scope.");
    } else if (!exprNode.getType().getClass().equals(functionType.getClass())) {
      /* Type of the expression given to the return statement
       * must match the return type of the function */
      visitor.getSemanticErrorList()
          .addTypeException(ctx, exprNode.getInput(),
              functionType.toString(), exprNode.getType().toString(), "");
    }

  }
}