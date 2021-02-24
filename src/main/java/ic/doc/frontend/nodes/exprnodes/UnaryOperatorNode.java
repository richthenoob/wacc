package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.*;
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
  public void translate(
      List<Label<Instruction>> instructionLabels,
      List<Label<Data>> dataLabels) {
    expr.translate(instructionLabels, dataLabels);
    Operand reg = null;
    Label curr = instructionLabels.get(instructionLabels.size() - 1);

    switch (unaryOperator) {
      case LOGICAL_NOT:
        // Need to EOR with IMM 1
        // MOV r4, #0
        // EOR r4, r4, #1
        curr.addToBody(new DataProcessing(reg, reg, new Operand(OperandType.CONST, 1), Operation.EOR));
        break;
      case MATH_NEGATION:
        // Add RSBS instr
        curr.addToBody(new DataProcessing(reg, reg, new Operand(OperandType.CONST, 1), Operation.RSBS));
        // Add branch instruction for overflows
        /* should this be a string? */
        curr.addToBody(new Branch(Condition.BLVS, new Label("p_throw_overflow_error")));

        // Need to add error message functions if we don't already have it
        /*
        if(!ctx.hasOverflowErrorFunction){
          addOverflowErrorMsg
          addOverflowErrorFunction
        }
        if(!ctx.hasRuntimeErrorFunction){
          addRuntimeErrorFunction
        }
        */

          break;
      case LEN:
        // Just load length of array as an immediate value into the dst reg?
//        curr.addToBody(new SingleDataTransfer(true, reg, ))
//      curr.addToBody(new SingleDataTransfer(true, reg, [reg]))
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
