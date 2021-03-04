package ic.doc.frontend.nodes.statnodes;

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

    List<Label<Instruction>> instructionLabels = context.getInstructionLabels();
    Move move = Move.MOV(RegisterOperand.R0, exprNode.getRegister());
    Branch branch = Branch.B("exit");

    context.getInstructionLabels()
        .get(instructionLabels.size() - 1)
        .addToBody(move)
        .addToBody(branch);
    context.freeRegister(exprNode.getRegister().getValue());
  }
}
