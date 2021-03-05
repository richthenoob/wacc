package ic.doc.frontend.nodes.statnodes;

import static ic.doc.backend.Instructions.Branch.B;
import static ic.doc.backend.Instructions.Move.MOV;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.Move;
import ic.doc.backend.Instructions.Branch;
import ic.doc.backend.Instructions.operands.RegisterOperand;

import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.IntType;
import java.util.List;
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
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx, exprNode.getInput(), "INT", exprNode.getType().toString(),
              "", "exit statement");
    }
  }

  @Override
  public void translate(Context context) {
    exprNode.translate(context);

    /* Get last label added to instruction labels. */
    List<Label<Instruction>> instructionLabels = context.getInstructionLabels();
    instructionLabels
        .get(instructionLabels.size() - 1)
        /* Move contents of register for expression in exit node to R0 */
        .addToBody(MOV(RegisterOperand.R0, exprNode.getRegister()))
        /* Branch to exit. */
        .addToBody(B("exit"));

    context.freeRegister(exprNode.getRegister().getValue());
  }
}
