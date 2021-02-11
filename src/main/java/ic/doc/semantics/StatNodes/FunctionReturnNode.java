package ic.doc.semantics.StatNodes;

import ic.doc.semantics.ExprNodes.ExprNode;
import ic.doc.semantics.Types.Type;
import ic.doc.semantics.Visitor;
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

    // can only be present in the body of a non-main function
    if (main) {
      visitor.getSemanticErrorList()
          .addException(ctx, "Cannot return from the global scope.");
    } else if (!exprNode.getType().getClass().equals(functionType.getClass())) {
      // type of the expression given to the return statement
      // must match the return type of the function
      visitor.getSemanticErrorList()
          .addTypeException(ctx, exprNode.getInput(),
              functionType.toString(), exprNode.getType().toString());
    }

  }
}