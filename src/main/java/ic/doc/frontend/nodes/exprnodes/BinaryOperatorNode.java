package ic.doc.frontend.nodes.exprnodes;

import ic.doc.frontend.types.*;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.semantics.Visitor;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ParserRuleContext;

import java.lang.String;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BinaryOperatorNode extends ExprNode {

  private final binaryOperators binaryOperator;
  private final ExprNode leftExpr;
  private final ExprNode rightExpr;

  public BinaryOperatorNode(binaryOperators binaryOperator,
      ExprNode leftExpr, ExprNode rightExpr) {
    this.binaryOperator = binaryOperator;
    this.leftExpr = leftExpr;
    this.rightExpr = rightExpr;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

    /* Indicates an error that has occurred in our visit functions,
     * does NOT give any information about the program itself.  */
    if (leftExpr == null || rightExpr == null) {
      throw new IllegalStateException(
          "null expression passed to BinaryOperatorNode!");
    }

    /* Default type if type matching fails. */
    Type type = new ErrorType();
    boolean typesMatch = false;

    switch (binaryOperator) {
      /* Arithmetic operators. */
      case MUL:
      case DIV:
      case MOD:
      case PLUS:
      case MINUS:
        typesMatch = exprTypesAreValid(leftExpr, rightExpr,
            Collections.singletonList(IntType.class), ctx, visitor);
        type = new IntType();
        break;

      /* Comparison operators. */
      case GT:
      case GTE:
      case LT:
      case LTE:
        typesMatch = exprTypesAreValid(leftExpr, rightExpr,
            Arrays.asList(IntType.class, CharType.class), ctx, visitor);
        type = new BoolType();
        break;

      /* Equality operators. */
      case EQ:
      case NEQ:
        typesMatch = exprTypesAreValid(leftExpr, rightExpr,
            Arrays.asList(IntType.class, BoolType.class, CharType.class,
                ArrayType.class, PairType.class, StringType.class), ctx,
            visitor);
        type = new BoolType();
        break;

      /* Boolean operators. */
      case AND:
      case OR:
        typesMatch = exprTypesAreValid(leftExpr, rightExpr,
            Collections.singletonList(BoolType.class), ctx, visitor);
        type = new BoolType();
    }

    if (!typesMatch) {
      setType(new ErrorType());
    } else {
      setType(type);
    }
  }

  /* Given two expression nodes and a list of valid types,
   * checks that both nodes have a type specified in the list,
   * and that both have the same type. */
  private boolean exprTypesAreValid(ExprNode expr1, ExprNode expr2,
      List<Class<? extends Type>> validTypes, ParserRuleContext ctx,
      Visitor visitor) {

    Type type1 = expr1.getType();
    Type type2 = expr2.getType();

    /* Short-circuiting if we encounter an error type. */
    if (type1 instanceof ErrorType || type2 instanceof ErrorType) {
      return false;
    }

    /* Generates string of valid types. */
    List<String> listOfValidTypes = validTypes.stream()
        .map(x -> {
          try {
            return x.getField("CLASS_NAME").get(null).toString();
          } catch (Exception e) {
            throw new IllegalStateException(
                "Something went wrong when parsing class name in"
                    + "BinaryOpeartorNode.java!");
          }
        }).collect(Collectors.toList());

    String stringValidTypes = String.join(" OR ", listOfValidTypes);

    /* Checks both expressions if they have a valid type. */
    boolean type1IsValid = validTypes.stream()
        .anyMatch(x -> x.isInstance(type1));
    boolean type2IsValid = validTypes.stream()
        .anyMatch(x -> x.isInstance(type2));

    /* Invalid type1 but valid type2. */
    if (!type1IsValid && type2IsValid) {
      visitor.getSemanticErrorList()
          .addTypeException(ctx, expr1.getInput(),
              type2.toString(), type1.toString());
      return false;
    }

    /* Valid type1 but invalid type2. */
    if (type1IsValid && !type2IsValid) {
      visitor.getSemanticErrorList()
          .addTypeException(ctx, expr2.getInput(),
              type1.toString(), type2.toString());
      return false;
    }

    /* Both invalid types. */
    if (!type1IsValid && !type2IsValid) {
      visitor.getSemanticErrorList()
          .addTypeException(ctx, expr1.getInput(),
              stringValidTypes, type1.toString());
      visitor.getSemanticErrorList()
          .addTypeException(ctx, expr2.getInput(),
              stringValidTypes, type2.toString());
      return false;
    }

    /* Both valid types but are different from one another. */
    if (type1IsValid && type2IsValid && !type1.equals(type2)) {
      visitor.getSemanticErrorList()
          .addTypeException(ctx, expr2.getInput(),
              expr1.getType().toString(), type2.toString());
      return false;
    }

    /* Otherwise, types are valid! */
    return true;
  }

  @Override
  public String getInput() {
    return leftExpr.getInput() + " " + binaryOperator.toString() + " "
        + rightExpr.getInput();
  }

  public enum binaryOperators {
    MUL(1, "*"), DIV(1, "/"), MOD(1, "%"),
    PLUS(2, "+"), MINUS(2, "-"),
    GT(3, ">"), GTE(3, ">="), LT(3, "<"), LTE(3, "<="),
    EQ(4, "=="), NEQ(4, "!="),
    AND(4, "&&"),
    OR(4, "||");

    private final int precedence;
    private final String symbol;

    binaryOperators(int precedence, String symbol) {
      this.precedence = precedence;
      this.symbol = symbol;
    }

    public int getPrecedence() {
      return precedence;
    }

    @Override
    public String toString() {
      return symbol;
    }
  }
}
