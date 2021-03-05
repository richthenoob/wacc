package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.Instruction;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.Visitor;

import ic.doc.frontend.types.*;
import org.antlr.v4.runtime.ParserRuleContext;

import static ic.doc.backend.instructions.Branch.BL;
import static ic.doc.backend.instructions.Move.MOV;
import static ic.doc.backend.PredefinedFunctions.*;

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
  public void translate(Context context) {
    Label<Instruction> curr = context.getCurrentLabel();
    exprNode.translate(context);
    RegisterOperand reg = exprNode.getRegister();

    /* Predefined functions need contents of register to be moved into R0. */
    curr.addToBody(MOV(RegisterOperand.R0, reg));

    /* Branch to appropriate printing functions according to type of expression. */
    Type exprType = exprNode.getType();

    if (exprType instanceof StringType ||
        (exprType instanceof ArrayType &&
            ((ArrayType) exprType).getInternalType() instanceof CharType)) {
      /* If expression is a string, or if expression is an array of chars */
      addPrintStringFunction(context);
      curr.addToBody(BL(PRINT_STR_FUNC));
    } else if (exprType instanceof IntType) {
      addPrintIntFunction(context);
      curr.addToBody(BL(PRINT_INT_FUNC));
    } else if (exprType instanceof BoolType) {
      addPrintBoolFunction(context);
      curr.addToBody(BL(PRINT_BOOL_FUNC));
    } else if (exprType instanceof CharType) {
      curr.addToBody(BL("putchar"));
    } else {
      addPrintReferenceFunction(context);
      curr.addToBody(BL(PRINT_REFERENCE_FUNC));
    }

    /* Branches to new line printing function if necessary. */
    if (newLine) {
      curr.addToBody(BL(PRINT_LN_FUNC));
      addPrintLnFunction(context);
    }

    context.freeRegister(reg.getValue());
  }
}
