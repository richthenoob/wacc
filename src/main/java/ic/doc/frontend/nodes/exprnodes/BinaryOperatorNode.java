package ic.doc.frontend.nodes.exprnodes;

import static ic.doc.backend.Instructions.Branch.*;
import static ic.doc.backend.Instructions.DataProcessing.*;
import static ic.doc.backend.Instructions.Move.*;
import static ic.doc.backend.Instructions.Stack.*;
import static ic.doc.backend.Instructions.operands.PostIndexedAddressOperand.*;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.*;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.Operand;
import ic.doc.backend.Instructions.operands.PostIndexedAddressOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.backend.PredefinedFunctions;
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

  // pFunction strings used in translate()
  private final String OVERFLOW_CHECK = "p_throw_overflow_error";
  private final String DIVIDE_ZERO_CHECK = "p_check_divide_by_zero";
  private final String DIVIDE_PFUNC = "__aeabi_idiv";
  private final String MOD_PFUNC = "__aeabi_idivmod";

  private final int OVERFLOW_SHIFT_AMOUNT = 31;

  //TODO: save symbol table from check

  public BinaryOperatorNode(BinaryOperators binaryOperator, ExprNode leftExpr,
      ExprNode rightExpr) {
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
        typesMatch =
            exprTypesAreValid(
                leftExpr, rightExpr, Collections.singletonList(IntType.class),
                ctx, visitor);
        type = new IntType();
        break;

      /* Comparison operators. */
      case GT:
      case GTE:
      case LT:
      case LTE:
        typesMatch =
            exprTypesAreValid(
                leftExpr, rightExpr,
                Arrays.asList(IntType.class, CharType.class), ctx, visitor);
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
                leftExpr, rightExpr, Collections.singletonList(BoolType.class),
                ctx, visitor);
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
    leftExpr.translate(context);
    rightExpr.translate(context);

    Label<Instruction> curr = context.getCurrentLabel();

    RegisterOperand lReg = leftExpr.getRegister();
    RegisterOperand rReg = rightExpr.getRegister();
    RegisterOperand dstReg = lReg;

    if (lReg.getValue() == rReg.getValue()) {
      //both registers are 10 i.e. all registers are occupied
      lReg = new RegisterOperand(11);
      curr.addToBody(
          POP(new RegisterOperand(11)));
      context.getCurrentSymbolTable().decrementOffset(4);
    }

    switch (binaryOperator) {
      /* Arithmetic operators. */
      case MUL:
        curr.addToBody(SMULL(dstReg, new RegisterOperand(12), lReg, rReg));

        // checking for overflow
        curr.addToBody(CMP(new RegisterOperand(12),
            new PostIndexedAddressOperand()
                .withRM(dstReg)
                .withShift(ShiftTypes.ASR)
                .withExpr(new ImmediateOperand<>(OVERFLOW_SHIFT_AMOUNT).withPrefixSymbol("#"))));

        curr.addToBody(BLNE(OVERFLOW_CHECK));
        PredefinedFunctions.addCheckIntegerOverflowFunction(context);
        break;
      case DIV:
      case MOD:
        curr.addToBody(MOV(RegisterOperand.R0, lReg));
        curr.addToBody(MOV(RegisterOperand.R1, rReg));
        curr.addToBody(BL(DIVIDE_ZERO_CHECK));
        PredefinedFunctions.addCheckDivideByZeroFunction(context);

        String divLabel = binaryOperator == BinaryOperators.DIV ?
            DIVIDE_PFUNC : MOD_PFUNC;
        curr.addToBody(BL(divLabel));

        Operand res = binaryOperator == BinaryOperators.DIV ?
            RegisterOperand.R0 : RegisterOperand.R1;
        curr.addToBody(MOV(dstReg, res));
        break;
      case PLUS:
      case MINUS:
        DataProcessing op = binaryOperator == BinaryOperators.PLUS ?
            ADD(dstReg, lReg, rReg) : SUB(dstReg, lReg, rReg);
        curr.addToBody(op);

        curr.addToBody(BLVS(OVERFLOW_CHECK));
        PredefinedFunctions.addCheckIntegerOverflowFunction(context);
        break;

      /* Comparison operators. */
      case GT:
        addComparisonAssembly(curr, lReg, rReg, dstReg, Condition.BGT,
            Condition.BLE);
        break;
      case GTE:
        addComparisonAssembly(curr, lReg, rReg, dstReg, Condition.BGE,
            Condition.BLT);
        break;
      case LT:
        addComparisonAssembly(curr, lReg, rReg, dstReg, Condition.BLT,
            Condition.BGE);
        break;
      case LTE:
        addComparisonAssembly(curr, lReg, rReg, dstReg, Condition.BLE,
            Condition.BGT);
        break;

      /* Equality operators. */
      case EQ:
        addComparisonAssembly(curr, lReg, rReg, dstReg, Condition.BEQ,
            Condition.BNE);
        break;
      case NEQ:
        addComparisonAssembly(curr, lReg, rReg, dstReg, Condition.BNE,
            Condition.BEQ);
        break;

      /* Boolean operators. */
      case AND:
        curr.addToBody(AND(dstReg, lReg, rReg));
        break;
      case OR:
        curr.addToBody(ORR(dstReg, lReg, rReg));
        break;
    }

    setRegister(dstReg);
    context.freeRegister(rReg.getValue());
  }

  private void addComparisonAssembly(
      Label<Instruction> curr, Operand lReg, Operand rReg, Operand dstReg,
      Condition lCond, Condition rCond) {
    // CMP
    curr.addToBody(CMP(lReg, rReg));
    // left expr
    curr.addToBody(new Move(dstReg, new ImmediateOperand<>(1).withPrefixSymbol("#"), lCond));
    // right expr
    curr.addToBody(new Move(dstReg, new ImmediateOperand<>(0).withPrefixSymbol("#"), rCond));
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
    boolean type1IsValid = validTypes.stream()
        .anyMatch(x -> x.isInstance(type1));
    boolean type2IsValid = validTypes.stream()
        .anyMatch(x -> x.isInstance(type2));

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
    if (type1IsValid && type2IsValid && !Type
        .checkTypeCompatibility(type1, type2)) {
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
    return leftExpr.getInput() + " " + binaryOperator.toString() + " "
        + rightExpr.getInput();
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
