package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.Visitor;

import ic.doc.frontend.types.*;
import org.antlr.v4.runtime.ParserRuleContext;

import static ic.doc.backend.Instructions.Branch.BL;
import static ic.doc.backend.Instructions.Move.MOV;
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
    exprNode.translate(context);
    RegisterOperand reg = exprNode.getRegister();
    Label<Instruction> curr = context.getCurrentLabel();

    curr.addToBody(MOV(RegisterOperand.R0, reg));
    Type exprType = exprNode.getType();

    if (exprType instanceof StringType ||
        (exprType instanceof ArrayType && ((ArrayType) exprType)
            .getInternalType() instanceof CharType)) {
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

    if (newLine) {
      curr.addToBody(BL(PRINT_LN_FUNC));
      addPrintLnFunction(context);
    }
  }
}
