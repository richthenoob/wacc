package ic.doc.frontend.nodes.statnodes;

import static ic.doc.backend.Instructions.Move.MOV;
import static ic.doc.backend.Instructions.Stack.POP_FOUR;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.Type;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class FunctionReturnNode extends StatNode {

  private final ExprNode exprNode;
  private final Boolean main;
  private final Type functionType;

  public FunctionReturnNode(ExprNode exprNode, Boolean main, Type functionType) {
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
      visitor.getSemanticErrorList().addException(ctx, "Cannot return from the global scope.");
    } else if (!exprNode.getType().getClass().equals(functionType.getClass())) {
      /* Type of the expression given to the return statement
       * must match the return type of the function */
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx,
              exprNode.getInput(),
              functionType.toString(),
              exprNode.getType().toString(),
              "",
              "'return' statement");
    }
  }

  @Override
  public void translate(Context context) {
    exprNode.translate(context);
    // Move result of function to R0
    context.addToCurrentLabel(MOV(RegisterOperand.R0, exprNode.getRegister()));
    context.addToCurrentLabel(POP_FOUR(RegisterOperand.PC, context.getCurrentSymbolTable()));
  }
}
