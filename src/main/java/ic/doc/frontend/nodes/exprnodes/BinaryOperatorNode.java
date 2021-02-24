package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.*;
import ic.doc.backend.Label;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ParserRuleContext;

public class BinaryOperatorNode extends ExprNode {

  private final BinaryOperators binaryOperator;
  private final ExprNode leftExpr;
  private final ExprNode rightExpr;

  public BinaryOperatorNode(BinaryOperators binaryOperator, ExprNode leftExpr, ExprNode rightExpr) {
    this.binaryOperator = binaryOperator;
    this.leftExpr = leftExpr;
    this.rightExpr = rightExpr;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

    /* Indicates an error that has occurred in our visit functions,
     * does NOT give any information about the program itself.  */
    if (leftExpr == null || rightExpr == null) {
      throw new IllegalStateException("null expression passed to BinaryOperatorNode!");
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
        typesMatch =
            exprTypesAreValid(
                leftExpr, rightExpr, Collections.singletonList(IntType.class), ctx, visitor);
        type = new IntType();
        break;

        /* Comparison operators. */
      case GT:
      case GTE:
      case LT:
      case LTE:
        typesMatch =
            exprTypesAreValid(
                leftExpr, rightExpr, Arrays.asList(IntType.class, CharType.class), ctx, visitor);
        type = new BoolType();
        break;

        /* Equality operators. */
      case EQ:
      case NEQ:
        typesMatch =
            exprTypesAreValid(
                leftExpr,
                rightExpr,
                Arrays.asList(
                    IntType.class,
                    BoolType.class,
                    CharType.class,
                    ArrayType.class,
                    PairType.class,
                    StringType.class),
                ctx,
                visitor);
        type = new BoolType();
        break;

        /* Boolean operators. */
      case AND:
      case OR:
        typesMatch =
            exprTypesAreValid(
                leftExpr, rightExpr, Collections.singletonList(BoolType.class), ctx, visitor);
        type = new BoolType();
    }

    if (!typesMatch) {
      setType(new ErrorType());
    } else {
      setType(type);
    }
  }

  @Override
  public void translate(Context context) {
    leftExpr.translate(instructionLabels, dataLabels);
    rightExpr.translate(instructionLabels, dataLabels);
    // if expression was previously declared, value in its register should be preserved.
    // Otherwise, it is safe to overwrite it with the result of this operation.
    Operand lReg = leftExpr instanceof VariableNode ?
        context.getFreeReg() : leftExpr.getRegister();
    Operand rReg = rightExpr instanceof VariableNode ?
        context.getFreeReg() : rightExpr.getRegister();

    Label curr = instructionLabels
        .get(instructionLabels.size() - 1);

    switch (binaryOperator) {
        /* Arithmetic operators. */
      case MUL:
        // SMULL
        curr.addToBody(new DataProcessing(lReg, rReg, lReg, rReg));
        // TODO: CMP which involves shifting?????
        curr.addToBody(new DataProcessing(rReg, null));
        curr.addToBody(new Branch(Condition.BLNE, new Label("p_throw_overflow_error")));
        break;
      case DIV:
      case MOD:
        // TODO: NOT SURE IF B IS THE RIGHT CONDITION TO USE?
        curr.addToBody(new Move(new Operand(OperandType.REG, 0), lReg, Condition.B));
        curr.addToBody(new Move(new Operand(OperandType.REG, 1), rReg, Condition.B));
        curr.addToBody(new Branch(Condition.BL, new Label("p_check_divide_by_zero")));
        String divLabel =
            binaryOperator == BinaryOperators.DIV ? "__aeabi_idiv" : "__aeabi_idivmod";
        curr.addToBody(new Branch(Condition.BL, new Label(divLabel)));
        int result = binaryOperator == BinaryOperators.DIV ? 0 : 1;
        curr.addToBody(new Move(new Operand(OperandType.REG, result), lReg, Condition.B));
        break;
      case PLUS:
      case MINUS:
        Operation op = binaryOperator == BinaryOperators.PLUS ? Operation.ADD : Operation.SUB;
        curr.addToBody(new DataProcessing(lReg, lReg, rReg, op));
        curr.addToBody(new Branch(Condition.BLVS, new Label("p_throw_overflow_error")));
        break;

        /* Comparison operators. */
      case GT:
        addComparisonAssembly(curr, lReg, rReg, Condition.BGT, Condition.BLE);
        break;
      case GTE:
        addComparisonAssembly(curr, lReg, rReg, Condition.BGE, Condition.BLT);
        break;
      case LT:
        addComparisonAssembly(curr, lReg, rReg, Condition.BLT, Condition.BGE);
        break;
      case LTE:
        addComparisonAssembly(curr, lReg, rReg, Condition.BLE, Condition.BGT);
        break;

        /* Equality operators. */
      case EQ:
        addComparisonAssembly(curr, lReg, rReg, Condition.BEQ, Condition.BNE);
        break;
      case NEQ:
        addComparisonAssembly(curr, lReg, rReg, Condition.BNE, Condition.BEQ);
        break;

        /* Boolean operators. */
      case AND:
        curr.addToBody(new DataProcessing(lReg, lReg, rReg, Operation.AND));
        break;
      case OR:
        curr.addToBody(new DataProcessing(lReg, lReg, rReg, Operation.OR));
        break;
    }
  }

  private void addComparisonAssembly(
      Label curr, Operand lReg, Operand rReg, Condition lCond, Condition rCond) {
    // CMP
    curr.addToBody(new DataProcessing(lReg, rReg));
    // left expr
    curr.addToBody(new Move(lReg, new Operand(OperandType.CONST, 1), lCond));
    // right expr
    curr.addToBody(new Move(rReg, new Operand(OperandType.CONST, 0), rCond));
  }

  /* Given two expression nodes and a list of valid types,
   * checks that both nodes have a type specified in the list,
   * and that both have the same type. */
  private boolean exprTypesAreValid(
      ExprNode expr1,
      ExprNode expr2,
      List<Class<? extends Type>> validTypes,
      ParserRuleContext ctx,
      Visitor visitor) {

    Type type1 = expr1.getType();
    Type type2 = expr2.getType();

    /* Short-circuiting if we encounter an error type. */
    if (type1 instanceof ErrorType || type2 instanceof ErrorType) {
      return false;
    }

    /* Generates string of valid types. */
    List<String> listOfValidTypes =
        validTypes.stream()
            .map(
                x -> {
                  try {
                    return x.getField("CLASS_NAME").get(null).toString();
                  } catch (Exception e) {
                    throw new IllegalStateException(
                        "Something went wrong when parsing class name in"
                            + "BinaryOperatorNode.java!");
                  }
                })
            .collect(Collectors.toList());

    String stringValidTypes = String.join(" OR ", listOfValidTypes);

    /* Checks both expressions if they have a valid type. */
    boolean type1IsValid = validTypes.stream().anyMatch(x -> x.isInstance(type1));
    boolean type2IsValid = validTypes.stream().anyMatch(x -> x.isInstance(type2));

    /* Invalid type1 but valid type2. */
    if (!type1IsValid && type2IsValid) {
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx,
              expr1.getInput(),
              type2.toString(),
              type1.toString(),
              "",
              "binary operator '" + binaryOperator + "'");
      return false;
    }

    /* Valid type1 but invalid type2. */
    if (type1IsValid && !type2IsValid) {
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx,
              expr2.getInput(),
              type1.toString(),
              type2.toString(),
              "",
              "binary operator '" + binaryOperator + "'");
      return false;
    }

    /* Both invalid types. */
    if (!type1IsValid && !type2IsValid) {
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx,
              expr1.getInput(),
              stringValidTypes,
              type1.toString(),
              "",
              "binary operator '" + binaryOperator + "'");
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx,
              expr2.getInput(),
              stringValidTypes,
              type2.toString(),
              "",
              "binary operator '" + binaryOperator + "'");
      return false;
    }

    /* Both valid types but are different from one another. */
    if (type1IsValid && type2IsValid && !Type.checkTypeCompatibility(type1, type2)) {
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx,
              expr2.getInput(),
              expr1.getType().toString(),
              type2.toString(),
              "",
              "binary operator '" + binaryOperator + "'");
      return false;
    }

    /* Otherwise, types are valid! */
    return true;
  }

  @Override
  public String getInput() {
    return leftExpr.getInput() + " " + binaryOperator.toString() + " " + rightExpr.getInput();
  }

  public enum BinaryOperators {
    MUL("*"),
    DIV("/"),
    MOD("%"),
    PLUS("+"),
    MINUS("-"),
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<="),
    EQ("=="),
    NEQ("!="),
    AND("&&"),
    OR("||");

    private final String symbol;

    BinaryOperators(String symbol) {
      this.symbol = symbol;
    }

    @Override
    public String toString() {
      return symbol;
    }
  }
}
