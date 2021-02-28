package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.*;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ArrayType;
import ic.doc.frontend.types.BoolType;
import ic.doc.frontend.types.CharType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.IntType;
import ic.doc.frontend.types.Type;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

import static ic.doc.backend.Instructions.Branch.BLVS;
import static ic.doc.backend.Instructions.DataProcessing.EOR;
import static ic.doc.backend.Instructions.DataProcessing.RSBS;
import static ic.doc.backend.Instructions.SingleDataTransfer.LDR;
import static ic.doc.backend.Instructions.operands.PreIndexedAddressOperand.PreIndexedAddressZeroOffset;
import static ic.doc.backend.PredefinedFunctions.addCheckIntegerOverflowFunction;
import static ic.doc.backend.PredefinedFunctions.addThrowRuntimeErrorFunction;

public class UnaryOperatorNode extends ExprNode {

  private final UnaryOperators unaryOperator;
  private final ExprNode expr;
  private Boolean isErrored = false;

  public UnaryOperatorNode(UnaryOperators unaryOperator, ExprNode expr) {
    this.unaryOperator = unaryOperator;
    this.expr = expr;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    Type exprType = expr.getType();
    /* Checks if type of expr is valid for each operator */
    switch (unaryOperator) {
      case LOGICAL_NOT:
        if (!(exprType instanceof BoolType)) {
          visitor
              .getSemanticErrorList()
              .addTypeException(
                  ctx,
                  expr.getInput(),
                  "BOOL",
                  expr.toString(),
                  "",
                  "binary operator '" + unaryOperator + "'");
          isErrored = true;
        }
        setType(new BoolType());
        break;
      case MATH_NEGATION:
        if (!(exprType instanceof IntType)) {
          visitor
              .getSemanticErrorList()
              .addTypeException(
                  ctx,
                  expr.getInput(),
                  "INT",
                  expr.toString(),
                  "",
                  "binary operator '" + unaryOperator + "'");
          isErrored = true;
        }
        setType(new IntType());
        break;
      case CHR:
        if (!(exprType instanceof IntType)) {
          visitor
              .getSemanticErrorList()
              .addTypeException(
                  ctx,
                  expr.getInput(),
                  "INT",
                  expr.toString(),
                  "",
                  "binary operator '" + unaryOperator + "'");
          isErrored = true;
        }
        setType(new CharType());
        break;
      case LEN:
        if (!(exprType instanceof ArrayType)) {
          visitor
              .getSemanticErrorList()
              .addTypeException(
                  ctx,
                  expr.getInput(),
                  "T[]",
                  expr.toString(),
                  "",
                  "binary operator '" + unaryOperator + "'");
          isErrored = true;
        }
        setType(new IntType());
        break;
      case ORD:
        if (!(exprType instanceof CharType)) {
          visitor
              .getSemanticErrorList()
              .addTypeException(
                  ctx,
                  expr.getInput(),
                  "CHAR",
                  expr.toString(),
                  "",
                  "binary operator '" + unaryOperator + "'");
          isErrored = true;
        }
        setType(new IntType());
    }

    if (isErrored) {
      /* Sets type of this node to error if type of expr not valid */
      setType(new ErrorType());
    }
  }

  @Override
  public void translate(Context context) {
    expr.translate(context);
    RegisterOperand reg = expr.getRegister();
    Label<Instruction> curr = context.getCurrentLabel();

    switch (unaryOperator) {
      case LOGICAL_NOT:
        // Need to EOR with IMM 1
        // MOV r4, #0
        // EOR r4, r4, #1
        curr.addToBody(EOR(reg, reg, new ImmediateOperand(1)));
        break;
      case MATH_NEGATION:
        // Add RSBS instr
        curr.addToBody(RSBS(reg, reg, new ImmediateOperand(0)));
        addCheckIntegerOverflowFunction(context);
        addThrowRuntimeErrorFunction(context);
        curr.addToBody(BLVS("p_throw_overflow_error"));
        break;
      case LEN:
        curr.addToBody(LDR(reg, PreIndexedAddressZeroOffset(reg)));
        break;
      case ORD:
        // Do nothing, expr is translated
        break;
      case CHR:
        //Do nothing, expr is translated
        break;
    }
  }

  @Override
  public String getInput() {
    return expr.getInput();
  }

  public enum UnaryOperators {
    LOGICAL_NOT,
    MATH_NEGATION,
    LEN,
    ORD,
    CHR
  }
}
