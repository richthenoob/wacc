package ic.doc.frontend.nodes.exprnodes;


import ic.doc.frontend.types.ArrayType;
import ic.doc.frontend.types.BoolType;
import ic.doc.frontend.types.CharType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.IntType;
import ic.doc.frontend.types.Type;

import ic.doc.frontend.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class UnaryOperatorNode extends ExprNode {

  private final unaryOperators unaryOperator;
  private final ExprNode expr;
  private Boolean isErrored = false;

  public UnaryOperatorNode(unaryOperators unaryOperator, ExprNode expr) {
    this.unaryOperator = unaryOperator;
    this.expr = expr;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    Type exprType = expr.getType();

    switch (unaryOperator) {
      case LOGICAL_NOT:
        if (!(exprType instanceof BoolType)) {
          visitor.getSemanticErrorList()
              .addTypeException(ctx, expr.getInput(), "BOOL", expr.toString());
          isErrored = true;
        }
        setType(new BoolType());
        break;
      case MATH_NEGATION:
        if (!(exprType instanceof IntType)) {
          visitor.getSemanticErrorList()
              .addTypeException(ctx, expr.getInput(), "INT", expr.toString());
          isErrored = true;
        }
        setType(new IntType());
        break;
      case CHR:
        if (!(exprType instanceof IntType)) {
          visitor.getSemanticErrorList()
              .addTypeException(ctx, expr.getInput(), "INT", expr.toString());
          isErrored = true;
        }
        setType(new CharType());
        break;
      case LEN:
        if (!(exprType instanceof ArrayType)) {
          visitor.getSemanticErrorList()
              .addTypeException(ctx, expr.getInput(), "T[]", expr.toString());
          isErrored = true;
        }
        setType(new IntType());
        break;
      case ORD:
        if (!(exprType instanceof CharType)) {
          visitor.getSemanticErrorList()
              .addTypeException(ctx, expr.getInput(), "CHAR", expr.toString());
          isErrored = true;
        }
        setType(new IntType());
    }

    if (isErrored) {
      setType(new ErrorType());
    }
  }

  @Override
  public String getInput() {
    return expr.getInput();
  }

  public enum unaryOperators {
    LOGICAL_NOT,
    MATH_NEGATION,
    LEN,
    ORD,
    CHR
  }
}
