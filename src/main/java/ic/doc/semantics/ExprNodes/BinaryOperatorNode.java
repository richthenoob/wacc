package ic.doc.semantics.ExprNodes;

import ic.doc.SemanticException;
import ic.doc.semantics.SymbolTable;
import ic.doc.semantics.Types.*;
import ic.doc.semantics.Types.ErrorType;
import ic.doc.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.lang.String;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class BinaryOperatorNode extends ExprNode {

  private final binaryOperators binaryOperator;
  private final ExprNode leftExpr;
  private final ExprNode rightExpr;
  private boolean isErrored;

  public BinaryOperatorNode(binaryOperators binaryOperator,
      ExprNode leftExpr, ExprNode rightExpr) {
    this.binaryOperator = binaryOperator;
    this.leftExpr = leftExpr;
    this.rightExpr = rightExpr;
    this.isErrored = false;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

    if (leftExpr == null || rightExpr == null) {
      throw new IllegalStateException(
          "null expression passed to BinaryOperatorNode!");
    }

    switch (binaryOperator) {
      /* Arithmetic operators. */
      case MUL:
      case DIV:
      case MOD:
      case PLUS:
      case MINUS:
        checkExprTypesMatch(leftExpr, rightExpr,
            Collections.singletonList(IntType.class), ctx,visitor);
        setType(new IntType());
        break;

      /* Comparison operators. */
      case GT:
      case GTE:
      case LT:
      case LTE:
        checkExprTypesMatch(leftExpr, rightExpr,
            Arrays.asList(IntType.class, CharType.class), ctx,visitor);
        setType(new BoolType());
        break;

      /* Equality operators. */
      case EQ:
      case NEQ:
        checkExprTypesMatch(leftExpr, rightExpr,
            Arrays.asList(IntType.class, BoolType.class, CharType.class,
                ArrayType.class, PairType.class, StringType.class), ctx,visitor);
        setType(new BoolType());
        break;

      /* Boolean operators. */
      case AND:
      case OR:
        checkExprTypesMatch(leftExpr, rightExpr,
            Collections.singletonList(BoolType.class), ctx,visitor);
        setType(new BoolType());
    }

    if (isErrored) {
      setType(new ErrorType());
    }
  }

  /* Given two expression nodes and a list of valid types,
   * checks that both nodes have a type specified in the list,
   * and that both have the same type. */
  private void checkExprTypesMatch(ExprNode expr1, ExprNode expr2,
      List<Class<? extends Type>> validTypes, ParserRuleContext ctx, Visitor visitor) {

    Type type1 = expr1.getType();
    Type type2 = expr2.getType();

    if (type1 instanceof ErrorType || type2 instanceof ErrorType) {
      return;
    }

    /* Checks both expressions if they have a valid type. */
    boolean type1IsValid = validTypes.stream()
        .anyMatch(x -> x.isInstance(type1));
    boolean type2IsValid = validTypes.stream()
        .anyMatch(x -> x.isInstance(type2));

    /* Invalid type1 but valid type2. */
    if (!type1IsValid && type2IsValid) {
       visitor.addTypeException(ctx, expr1.getInput(), type2.toString(), type1.toString());
      isErrored = true;
    }

    /* Valid type1 but invalid type2. */
    if (type1IsValid && !type2IsValid) {
      visitor.addTypeException(ctx, expr2.getInput(), type1.toString(), type2.toString());
      isErrored = true;
    }

    /* Both invalid types. */
    if (!type1IsValid && !type2IsValid) {
      visitor.addTypeException(ctx, expr1.getInput(), "INT or CHAR", type1.toString());
      visitor.addTypeException(ctx, expr2.getInput(), "INT or CHAR", type2.toString());
      isErrored = true;
    }

    /* Both valid types but are different from one another. */
    if (type1IsValid && type2IsValid && !type1.equals(type2)) {
      visitor.addTypeException(ctx, expr2.getInput(), expr1.getType().toString(), type2.toString());
      isErrored = true;
    }
  }

  @Override
  public String getInput() {
    return leftExpr.getInput() + " " + binaryOperator.toString() + " "
        + rightExpr.getInput();
  }

  public enum binaryOperators {
    MUL(1), DIV(1), MOD(1),
    PLUS(2), MINUS(2),
    GT(3), GTE(3), LT(3), LTE(3),
    EQ(4), NEQ(4),
    AND(4),
    OR(4);

    private final int precedence;

    binaryOperators(int precedence) {
      this.precedence = precedence;
    }

    public int getPrecedence() {
      return precedence;
    }
  }
}
