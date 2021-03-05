package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.*;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ArrayType;
import ic.doc.frontend.types.BoolType;
import ic.doc.frontend.types.CharType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.IntType;
import ic.doc.frontend.types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

import static ic.doc.backend.instructions.Branch.BLVS;
import static ic.doc.backend.instructions.DataProcessing.EOR;
import static ic.doc.backend.instructions.DataProcessing.RSBS;
import static ic.doc.backend.instructions.SingleDataTransfer.LDR;
import static ic.doc.backend.PredefinedFunctions.addCheckIntegerOverflowFunction;

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
    setRegister(reg);
    Label<Instruction> curr = context.getCurrentLabel();

    switch (unaryOperator) {
      case LOGICAL_NOT:
        /* EOR operand with IMM #1 */
        curr.addToBody(EOR(reg, reg, new ImmediateOperand<>(1).withPrefixSymbol("#")));
        break;
      case MATH_NEGATION:
        /* RSBS operand with IMM #0 */
        curr.addToBody(RSBS(reg, reg, new ImmediateOperand<>(0).withPrefixSymbol("#")));
        addCheckIntegerOverflowFunction(context);
        curr.addToBody(BLVS("p_throw_overflow_error"));
        break;
      case LEN:
        /* Load length of operand into reg */
        curr.addToBody(LDR(reg, new PreIndexedAddressOperand(reg)));
        break;
      case ORD:
      case CHR:
        /* Do nothing, expr is translated */
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
