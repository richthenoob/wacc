package ic.doc.backend;

import ic.doc.backend.instructions.*;
import ic.doc.backend.instructions.operands.*;

import ic.doc.frontend.semantics.SymbolTable;
import java.util.Map;
import java.util.Set;

import static ic.doc.backend.instructions.Branch.*;
import static ic.doc.backend.instructions.DataProcessing.ADD;
import static ic.doc.backend.instructions.DataProcessing.CMP;
import static ic.doc.backend.instructions.Move.MOV;
import static ic.doc.backend.instructions.SingleDataTransfer.LDR;
import static ic.doc.backend.instructions.Stack.PUSH;
import static ic.doc.backend.instructions.Stack.POP;

public class PredefinedFunctions {
  /* Exit code that is returned by a runtime error*/
  public static final int RUNTIME_ERROR_EXIT_CODE = -1;

  /* All predefined function names */
  public static final String PRINT_STR_FUNC = "p_print_string";
  public static final String PRINT_BOOL_FUNC = "p_print_bool";
  public static final String PRINT_INT_FUNC = "p_print_int";
  public static final String PRINT_REFERENCE_FUNC = "p_print_reference";
  public static final String PRINT_LN_FUNC = "p_print_ln";
  public static final String THROW_RUNTIME_ERROR_FUNC = "p_throw_runtime_error";
  public static final String CHECK_NULL_POINTER_FUNC = "p_check_null_pointer";
  public static final String CHECK_ARRAY_BOUNDS_FUNC = "p_check_array_bounds";
  public static final String CHECK_DIVIDE_BY_ZERO_FUNC = "p_check_divide_by_zero";
  public static final String THROW_OVERFLOW_ERROR_FUNC = "p_throw_overflow_error";
  public static final String FREE_ARRAY_FUNC = "p_free_array";
  public static final String FREE_PAIR_FUNC = "p_free_pair";
  public static final String FREE_CLASS_FUNC = "p_free_class";
  public static final String READ_CHAR_FUNC = "p_read_char";
  public static final String READ_INT_FUNC = "p_read_int";
  /* Reduce code duplication by appending "char" or "int" to READ_TYPE_FUNC */
  public static final String READ_TYPE_FUNC = "p_read_";

  /* All placeholders used for printing / reading*/
  private static final String STRING_PLACEHOLDER = "%.*s\\0";
  private static final String FALSE_PLACEHOLDER = "false\\0";
  private static final String TRUE_PLACEHOLDER = "true\\0";
  private static final String INT_PLACEHOLDER = "%d\\0";
  private static final String CHAR_PLACEHOLDER = " %c\\0";
  private static final String NEWLN_PLACEHOLDER = "\\0";
  private static final String REFERENCE_PLACEHOLDER = "%p\\0";

  /* Adds some data to dataLabels and returns the label */
  private static String getDataLabel(Context ctx, String data) {
    /* Placeholders is a map from placeholder to label string
     * e.g. <"%.*s\0", "msg_5">
    */
    Map<String, String> placeholders = ctx.getDataPlaceHolders();
    if (placeholders.containsKey(data)) {
      return placeholders.get(data);
    }

    String msgLabelStr = ctx.getNextDataLabelString();
    Label<Data> msgLabel = new Label<>(msgLabelStr);
    /* NumChar is length - 1 to account for the escaped characters */
    msgLabel.addToBody(new Data(data.length() - 1, data));
    ctx.addToDataLabels(msgLabel);
    placeholders.put(data, msgLabelStr);
    return msgLabelStr;
  }

  /* =========  Add predefined functions ========== */
  /* These functions will add the predefined function to the end of the program if it does not already exist */
  /* If the function already exists when trying to add it, the function will return and do nothing */


  /* Takes a print function and adds some common instructions to it */
  /* All variations of printX will have these common instructions at the end */
  private static void addCommonPrintInstructions(
      Label<Instruction> instrLabel, SymbolTable symboltable) {
    instrLabel.addToBody(
        ADD(RegisterOperand.R0, RegisterOperand.R0,
            new ImmediateOperand<>(4).withPrefixSymbol("#")));
    instrLabel.addToBody(BL("printf"));
    instrLabel
        .addToBody(MOV(RegisterOperand.R0,
            new ImmediateOperand<>(0).withPrefixSymbol("#")));
    instrLabel.addToBody(BL("fflush"));
    instrLabel.addToBody(POP(RegisterOperand.PC));
  }

  /* Adds the p_print_string predefined function */
  public static void addPrintStringFunction(Context ctx) {
    Set<Label<Instruction>> endFunctions = ctx.getEndFunctions();
    Label<Instruction> printStringLabel = new Label<>(PRINT_STR_FUNC);
    if (endFunctions.contains(printStringLabel)) {
      return;
    }

    printStringLabel
        .addToBody(PUSH(RegisterOperand.LR));
    printStringLabel.addToBody(LDR(RegisterOperand.R1,
        new PreIndexedAddressOperand(RegisterOperand.R0)));
    printStringLabel.addToBody(ADD(new RegisterOperand(2), RegisterOperand.R0,
        new ImmediateOperand<>(4).withPrefixSymbol("#")));

    String msgLabelStr = getDataLabel(ctx, STRING_PLACEHOLDER);

    printStringLabel.addToBody(
        LDR(RegisterOperand.R0, new LabelAddressOperand(msgLabelStr)));

    addCommonPrintInstructions(printStringLabel, ctx.getCurrentSymbolTable());
    endFunctions.add(printStringLabel);
  }

  /* Adds the p_print_bool predefined function */
  public static void addPrintBoolFunction(Context ctx) {
    Set<Label<Instruction>> endFunctions = ctx.getEndFunctions();
    Label<Instruction> printBoolLabel = new Label<>(PRINT_BOOL_FUNC);
    if (endFunctions.contains(printBoolLabel)) {
      return;
    }

    printBoolLabel
        .addToBody(PUSH(RegisterOperand.LR));
    printBoolLabel
        .addToBody(CMP(RegisterOperand.R0,
            new ImmediateOperand<>(0).withPrefixSymbol("#")));
    /* TRUE */
    String trueLabelStr = getDataLabel(ctx, TRUE_PLACEHOLDER);
    /* FALSE */
    String falseLabelStr = getDataLabel(ctx, FALSE_PLACEHOLDER);

    printBoolLabel.addToBody(
        LDR(RegisterOperand.R0, new LabelAddressOperand(trueLabelStr)).withCond("NE"));
    printBoolLabel.addToBody(
        LDR(RegisterOperand.R0, new LabelAddressOperand(falseLabelStr)).withCond("EQ"));

    addCommonPrintInstructions(printBoolLabel, ctx.getCurrentSymbolTable());

    endFunctions.add(printBoolLabel);
  }

  /* Adds the p_print_int predefined function */
  public static void addPrintIntFunction(Context ctx) {
    Set<Label<Instruction>> endFunctions = ctx.getEndFunctions();
    Label<Instruction> printIntLabel = new Label<>(PRINT_INT_FUNC);
    if (endFunctions.contains(printIntLabel)) {
      return;
    }

    printIntLabel
        .addToBody(PUSH(RegisterOperand.LR));
    printIntLabel.addToBody(MOV(RegisterOperand.R1, RegisterOperand.R0));
    /* INT MSG */
    String intLabelStr = getDataLabel(ctx, INT_PLACEHOLDER);
    printIntLabel.addToBody(
        LDR(RegisterOperand.R0, new LabelAddressOperand(intLabelStr)));

    addCommonPrintInstructions(printIntLabel, ctx.getCurrentSymbolTable());

    endFunctions.add(printIntLabel);
  }

  /* Adds the p_print_ln predefined function */
  public static void addPrintLnFunction(Context ctx) {
    Set<Label<Instruction>> endFunctions = ctx.getEndFunctions();
    Label<Instruction> printLnLabel = new Label<>(PRINT_LN_FUNC);
    if (endFunctions.contains(printLnLabel)) {
      return;
    }

    printLnLabel
        .addToBody(PUSH(RegisterOperand.LR));
    String newLnLabelStr = getDataLabel(ctx, NEWLN_PLACEHOLDER);

    printLnLabel.addToBody(
        LDR(RegisterOperand.R0, new LabelAddressOperand(newLnLabelStr)));
    printLnLabel.addToBody(
        ADD(RegisterOperand.R0, RegisterOperand.R0,
            new ImmediateOperand<>(4).withPrefixSymbol("#")));
    printLnLabel.addToBody(BL("puts"));
    printLnLabel.addToBody(MOV(RegisterOperand.R0,
        new ImmediateOperand<>(0).withPrefixSymbol("#")));
    printLnLabel.addToBody(BL("fflush"));
    printLnLabel
        .addToBody(POP(RegisterOperand.PC));

    endFunctions.add(printLnLabel);
  }

  /* Adds the p_print_reference predefined function */
  public static void addPrintReferenceFunction(Context ctx) {
    Set<Label<Instruction>> endFunctions = ctx.getEndFunctions();
    Label<Instruction> printReferenceLabel = new Label<>(PRINT_REFERENCE_FUNC);
    if (endFunctions.contains(printReferenceLabel)) {
      return;
    }

    printReferenceLabel
        .addToBody(PUSH(RegisterOperand.LR));
    printReferenceLabel.addToBody(MOV(RegisterOperand.R1, RegisterOperand.R0));

    String referenceLabelStr = getDataLabel(ctx, REFERENCE_PLACEHOLDER);

    printReferenceLabel.addToBody(
        LDR(RegisterOperand.R0, new LabelAddressOperand(referenceLabelStr)));

    addCommonPrintInstructions(printReferenceLabel,
        ctx.getCurrentSymbolTable());

    endFunctions.add(printReferenceLabel);
  }

  /* Adds the p_check_null_pointer predefined function */
  public static void addCheckNullPointerFunction(Context ctx) {
    Set<Label<Instruction>> endFunctions = ctx.getEndFunctions();
    Label<Instruction> checkNullPointerLabel = new Label<>(
        CHECK_NULL_POINTER_FUNC);
    if (endFunctions.contains(checkNullPointerLabel)) {
      return;
    }

    checkNullPointerLabel
        .addToBody(PUSH(RegisterOperand.LR));
    checkNullPointerLabel
        .addToBody(CMP(RegisterOperand.R0,
            new ImmediateOperand<>(0).withPrefixSymbol("#")));
    String nullReferenceErrorLabelStr = getDataLabel(ctx,
        ErrorMessage.NULL_REFERENCE);

    checkNullPointerLabel.addToBody(LDR(RegisterOperand.R0,
        new LabelAddressOperand(nullReferenceErrorLabelStr))
        .withCond("EQ"));

    /* Since it calls THROW_RUNTIME_ERROR_FUNC, we need to addThrowRuntimeErrorFunction here */
    addThrowRuntimeErrorFunction(ctx);
    checkNullPointerLabel.addToBody(BLEQ(THROW_RUNTIME_ERROR_FUNC));
    checkNullPointerLabel
        .addToBody(POP(RegisterOperand.PC));

    endFunctions.add(checkNullPointerLabel);
  }

  /* Adds the p_throw_runtime_error predefined function */
  public static void addThrowRuntimeErrorFunction(Context ctx) {
    Set<Label<Instruction>> endFunctions = ctx.getEndFunctions();
    Label<Instruction> throwRuntimeErrorLabel = new Label<>(
        THROW_RUNTIME_ERROR_FUNC);
    if (endFunctions.contains(throwRuntimeErrorLabel)) {
      return;
    }
    addPrintStringFunction(ctx);
    throwRuntimeErrorLabel.addToBody(BL(PRINT_STR_FUNC));
    throwRuntimeErrorLabel.addToBody(
        MOV(RegisterOperand.R0, new ImmediateOperand<>(RUNTIME_ERROR_EXIT_CODE).withPrefixSymbol("#")));
    throwRuntimeErrorLabel.addToBody(BL("exit"));

    endFunctions.add(throwRuntimeErrorLabel);
  }

  /* Adds the p_check_array_bounds predefined function */
  public static void addCheckArrayBoundsFunction(Context ctx) {
    Set<Label<Instruction>> endFunctions = ctx.getEndFunctions();
    Label<Instruction> checkArrayBoundsLabel = new Label<>(
        CHECK_ARRAY_BOUNDS_FUNC);
    if (endFunctions.contains(checkArrayBoundsLabel)) {
      return;
    }

    checkArrayBoundsLabel
        .addToBody(PUSH(RegisterOperand.LR));
    checkArrayBoundsLabel
        .addToBody(CMP(RegisterOperand.R0,
            new ImmediateOperand<>(0).withPrefixSymbol("#")));

    /* Declare out of bounds negative error msg */
    String arrayOutOfBoundsNegativeLabelStr = getDataLabel(ctx,
        ErrorMessage.ARRAY_IDX_OUT_OF_BOUNDS_NEGATIVE);
    /* Declare out of bounds large error msg */
    String arrayOutOfBoundsLargeLabelStr = getDataLabel(ctx,
        ErrorMessage.ARRAY_IDX_OUT_OF_BOUNDS_LARGE);

    checkArrayBoundsLabel.addToBody(LDR(RegisterOperand.R0,
        new LabelAddressOperand(arrayOutOfBoundsNegativeLabelStr))
        .withCond("LT"));

    /* Add throw runtime error function since we need to call it */
    addThrowRuntimeErrorFunction(ctx);
    checkArrayBoundsLabel.addToBody(BLLT(THROW_RUNTIME_ERROR_FUNC));
    checkArrayBoundsLabel.addToBody(LDR(RegisterOperand.R1,
        new PreIndexedAddressOperand(RegisterOperand.R1)));

    checkArrayBoundsLabel
        .addToBody(CMP(RegisterOperand.R0, RegisterOperand.R1));
    checkArrayBoundsLabel.addToBody(LDR(RegisterOperand.R0,
        new LabelAddressOperand(arrayOutOfBoundsLargeLabelStr))
        .withCond("CS"));

    checkArrayBoundsLabel.addToBody(BLCS(THROW_RUNTIME_ERROR_FUNC));

    checkArrayBoundsLabel
        .addToBody(POP(RegisterOperand.PC));

    endFunctions.add(checkArrayBoundsLabel);
  }

  /* Adds the p_check_divide_by_zero predefined function */
  public static void addCheckDivideByZeroFunction(Context ctx) {
    Set<Label<Instruction>> endFunctions = ctx.getEndFunctions();
    Label<Instruction> checkDivideByZeroLabel = new Label<>(
        CHECK_DIVIDE_BY_ZERO_FUNC);
    if (endFunctions.contains(checkDivideByZeroLabel)) {
      return;
    }

    checkDivideByZeroLabel
        .addToBody(PUSH(RegisterOperand.LR));
    checkDivideByZeroLabel
        .addToBody(CMP(RegisterOperand.R1,
            new ImmediateOperand<>(0).withPrefixSymbol("#")));

    /* Declare out of bounds negative error msg */
    String divideByZeroLabelStr = getDataLabel(ctx,
        ErrorMessage.DIVIDE_BY_ZERO);

    checkDivideByZeroLabel.addToBody(LDR(RegisterOperand.R0,
        new LabelAddressOperand(divideByZeroLabelStr)).withCond("EQ"));

    /* Add the throw runtime error function since we call it */
    addThrowRuntimeErrorFunction(ctx);
    checkDivideByZeroLabel.addToBody(BLEQ(THROW_RUNTIME_ERROR_FUNC));
    checkDivideByZeroLabel
        .addToBody(POP(RegisterOperand.PC));

    endFunctions.add(checkDivideByZeroLabel);
  }

  /* Adds the p_check_integer_overflow predefined function */
  public static void addCheckIntegerOverflowFunction(Context ctx) {
    Set<Label<Instruction>> endFunctions = ctx.getEndFunctions();
    Label<Instruction> throwOverflowErrorFuncLabel = new Label<>(
        THROW_OVERFLOW_ERROR_FUNC);
    if (endFunctions.contains(throwOverflowErrorFuncLabel)) {
      return;
    }

    /* Declare overflow error msg */
    String integerOverflowLabelStr = getDataLabel(ctx, ErrorMessage.OVERFLOW);
    throwOverflowErrorFuncLabel.addToBody(LDR(RegisterOperand.R0,
        new LabelAddressOperand(integerOverflowLabelStr)));

    /* Add the throw runtime error function since we call it */
    addThrowRuntimeErrorFunction(ctx);
    throwOverflowErrorFuncLabel.addToBody(BL(THROW_RUNTIME_ERROR_FUNC));

    endFunctions.add(throwOverflowErrorFuncLabel);
  }

  /* Adds common instructions between p_free_pair and p_free_array */
  private static void addCommonFreeInstructions(Context ctx,
      Label<Instruction> instrLabel) {
    instrLabel.addToBody(PUSH(RegisterOperand.LR));
    instrLabel.addToBody(CMP(RegisterOperand.R0,
        new ImmediateOperand<>(0).withPrefixSymbol("#")));

    /* Declare nullReferenceErrorMsg in Data */
    String nullReferenceErrorLabelStr = getDataLabel(ctx,
        ErrorMessage.NULL_REFERENCE);

    instrLabel.addToBody(LDR(RegisterOperand.R0,
        new LabelAddressOperand(nullReferenceErrorLabelStr)).withCond("EQ"));

    /* Add the throw runtime error function since we call it */
    addThrowRuntimeErrorFunction(ctx);
    instrLabel.addToBody(BEQ(THROW_RUNTIME_ERROR_FUNC));
  }

  /* Adds the p_free_array predefined function */
  public static void addFreeArrayFunction(Context ctx) {
    Set<Label<Instruction>> endFunctions = ctx.getEndFunctions();
    Label<Instruction> freeArrayFuncLabel = new Label<>(FREE_ARRAY_FUNC);
    if (endFunctions.contains(freeArrayFuncLabel)) {
      return;
    }

    addCommonFreeInstructions(ctx, freeArrayFuncLabel);
    freeArrayFuncLabel.addToBody(BL("free"));
    freeArrayFuncLabel
        .addToBody(POP(RegisterOperand.PC));

    endFunctions.add(freeArrayFuncLabel);
  }

  /* Adds the p_free_pair predefined function */
  public static void addFreePairFunction(Context ctx) {
    Set<Label<Instruction>> endFunctions = ctx.getEndFunctions();
    Label<Instruction> freePairFuncLabel = new Label<>(FREE_PAIR_FUNC);
    if (endFunctions.contains(FREE_PAIR_FUNC)) {
      return;
    }

    addCommonFreeInstructions(ctx, freePairFuncLabel);

    freePairFuncLabel
        .addToBody(PUSH(RegisterOperand.R0));
    freePairFuncLabel.addToBody(LDR(RegisterOperand.R0,
        new PreIndexedAddressOperand(RegisterOperand.R0)));
    freePairFuncLabel.addToBody(BL("free"));
    freePairFuncLabel.addToBody(LDR(RegisterOperand.R0,
        new PreIndexedAddressOperand(RegisterOperand.SP)));
    freePairFuncLabel.addToBody(
        LDR(RegisterOperand.R0, new PreIndexedAddressOperand(RegisterOperand.R0)
            .withExpr(new ImmediateOperand<>(4).withPrefixSymbol("#"))));
    freePairFuncLabel.addToBody(BL("free"));
    freePairFuncLabel
        .addToBody(POP(RegisterOperand.R0));
    freePairFuncLabel.addToBody(BL("free"));
    freePairFuncLabel
        .addToBody(POP(RegisterOperand.PC));

    endFunctions.add(freePairFuncLabel);
  }

  /* Adds the p_free_class predefined function */
  public static void addFreeClassFunction(Context ctx) {
    Set<Label<Instruction>> endFunctions = ctx.getEndFunctions();
    Label<Instruction> freeClassFuncLabel = new Label<>(FREE_CLASS_FUNC);
    if (endFunctions.contains(freeClassFuncLabel)) {
      return;
    }

    addCommonFreeInstructions(ctx, freeClassFuncLabel);

    freeClassFuncLabel.addToBody(BL("free"));
    freeClassFuncLabel
        .addToBody(POP(RegisterOperand.PC));

    endFunctions.add(freeClassFuncLabel);
  }

  /* Adds the p_read_int or p_read_char predefined function
  * We use a common function because the logic is mostly identical
  * type must be: "int" or "char".
  */
  public static void addReadTypeFunction(Context ctx, String type) {
    Set<Label<Instruction>> endFunctions = ctx.getEndFunctions();
    Label<Instruction> readCharFuncLabel = new Label<>(READ_TYPE_FUNC + type);
    if (endFunctions.contains(READ_TYPE_FUNC + type)) {
      return;
    }

    readCharFuncLabel.addToBody(PUSH(RegisterOperand.LR));
    readCharFuncLabel.addToBody(MOV(RegisterOperand.R1, RegisterOperand.R0));

    String typePlaceholderLabel = getDataLabel(ctx,
        type == "int" ? INT_PLACEHOLDER : CHAR_PLACEHOLDER);
    readCharFuncLabel.addToBody(
        LDR(RegisterOperand.R0, new LabelAddressOperand(typePlaceholderLabel)));
    readCharFuncLabel.addToBody(
        ADD(RegisterOperand.R0, RegisterOperand.R0,
            new ImmediateOperand<>(4).withPrefixSymbol("#")));
    readCharFuncLabel.addToBody(BL("scanf"));
    readCharFuncLabel
        .addToBody(POP(RegisterOperand.PC));

    endFunctions.add(readCharFuncLabel);
  }

}
