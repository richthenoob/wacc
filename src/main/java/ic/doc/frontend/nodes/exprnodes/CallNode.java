package ic.doc.frontend.nodes.exprnodes;

import static ic.doc.backend.instructions.Branch.BL;
import static ic.doc.backend.instructions.Branch.BLX;
import static ic.doc.backend.instructions.Move.MOV;
import static ic.doc.backend.instructions.SingleDataTransfer.LDR;
import static ic.doc.backend.instructions.SingleDataTransfer.STR;

import ic.doc.backend.Context;
import ic.doc.backend.VirtualTable;
import ic.doc.backend.instructions.DataProcessing;
import ic.doc.backend.instructions.SingleDataTransfer;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.frontend.identifiers.ClassIdentifier;
import ic.doc.frontend.identifiers.FunctionIdentifier;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.nodes.ArgListNode;
import ic.doc.frontend.nodes.ClassNode;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.BoolType;
import ic.doc.frontend.types.CharType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.Type;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class CallNode extends ExprNode {

  private final String identifier;
  private final ArgListNode args;

  public CallNode(String identifier, ArgListNode args) {
    this.identifier = identifier;
    this.args = args;
  }

  public String getIdentifier() {
    return identifier;
  }

  public ArgListNode getArgs() {
    return args;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    String functionName = identifier;
    SymbolKey key = new SymbolKey(functionName, KeyTypes.FUNCTION);
    Identifier id = visitor.getCurrentSymbolTable().lookupAll(key);

    if (!functionIdCheck(visitor, ctx, id)) {
      return;
    } else {
      FunctionIdentifier functionId = (FunctionIdentifier) id;
      Type functionType = functionId.getType();
      setType(functionType);
      List<Type> expectedParamListType = functionId.getParamTypeList();

      /* Checks if number of parameters passed in matches expected count */
      if (!(args.getNumParas() == expectedParamListType.size())) {
        visitor
            .getSemanticErrorList()
            .addException(
                ctx,
                "Incompatible parameter count at "
                    + this.getInput()
                    + ". Expected count: "
                    + expectedParamListType.size()
                    + ". Actual count: "
                    + args.getNumParas()
                    + ".");
      } else if (!(Type
          .checkTypeListCompatibility(args.getType(), expectedParamListType,
              visitor.getCurrentSymbolTable()))) {
        /* Checks if types of parameters passed in matches expected types */
        visitor
            .getSemanticErrorList()
            .addTypeException(ctx, args.getInput(), functionId.printTypes(),
                args.printTypes(), "");
      }
    }
  }

  /* Checks if function was defined in scope,
   * and if identifier is an instance of FunctionIdentifier. */
  protected boolean functionIdCheck(Visitor visitor, ParserRuleContext ctx,
      Identifier id) {
    String functionName = getIdentifier();
    if (id == null) {
      /* Checks if function was defined in scope */
      setType(new ErrorType());
      visitor.getSemanticErrorList()
          .addScopeException(ctx, false, "Function", functionName);
      return false;
    } else if (!(id instanceof FunctionIdentifier)) {
      /* Checks if id is an instance of function */
      setType(new ErrorType());
      visitor
          .getSemanticErrorList()
          .addException(
              ctx,
              "Identifier Variable is not an instance of function. Expected: Function. Actual: Variable.");
      return false;
    }
    return true;
  }

  @Override
  public void translate(Context context) {

    /* Find function table in class if we are in a classNode, else just
     * find function table in context. */
    SymbolTable funcTable;
    String currentClass = context.getCurrentClass();
    SymbolTable currentSymbolTable = context.getCurrentSymbolTable();
    SymbolKey classKey;
    ClassIdentifier classIdentifier = null;
    if (!currentClass.isEmpty()) {
      classKey = new SymbolKey(currentClass, KeyTypes.CLASS);
      classIdentifier = ((ClassIdentifier) currentSymbolTable
          .lookupAll(classKey));
      funcTable = classIdentifier.getClassNode().getFunctionTable(identifier);
    } else {
      funcTable = context.getFunctionTables().get(identifier);
    }

    /* Use counter to track the size of parameters that have been pushed
     * onto the stack, ensuring to restore this at the end of the function call. */
    int counter = 0;

    /* Stores arguments of call onto stack to be accessed by function later. */
    counter = storeArguments(context, counter);

    SymbolKey functionKey = new SymbolKey(identifier, KeyTypes.FUNCTION);
    boolean isExternalFunction = currentClass.isEmpty() ||
        classIdentifier.getClassSymbolTable().lookup(functionKey) == null;
    if (!isExternalFunction) {
      /* After pushing arguments, push the address of the instance so
       * that the function can find class instance fields. */
      SymbolKey classInstanceKey = new SymbolKey("specialname",
          KeyTypes.VARIABLE);
      VariableIdentifier classInstanceIdentifier = (VariableIdentifier) currentSymbolTable
          .lookupAll(classInstanceKey);

      counter = pushInstanceAddress(context, classInstanceIdentifier,
          classInstanceKey, counter, classIdentifier);
    }

    /* Finally, restore the changes we have made to the function symbol table,
     * scope, and stack space after the call. Move result of function call to free register. */
    restoreStateAfterCall(context, currentClass, counter, funcTable, isExternalFunction);
  }

  /* Stores arguments of call onto stack to be accessed by function later. */
  protected int storeArguments(Context context, int counter) {
    SymbolTable currentSymbolTable = context.getCurrentSymbolTable();
    for (int i = getArgs().getParams().size() - 1; i >= 0; i--) {
      ExprNode arg = getArgs().getParams().get(i);

      int offset;
      /* Calculate new stack pointer offset after storing each argument.
       * Bool and char require an offset of +1 and not +4. */
      String shiftCond = "";
      if (arg.getType() instanceof BoolType ||
          arg.getType() instanceof CharType) {
        offset = 1;
        shiftCond = "B";
      } else {
        offset = 4;
      }

      /* Load argument onto a free register */
      arg.translate(context);

      /* Because there is a push instruction here, we must temporarily
       * increment the function table so that if we access anything in the stack
       * in subsequent parameters, this push is accounted for. */
      currentSymbolTable.incrementOffset(offset);
      currentSymbolTable.incrementTableSizeInBytes(offset);
      counter += offset;

      /* Store argument onto stack for the function to use. */
      RegisterOperand reg = arg.getRegister();
      PreIndexedAddressOperand shiftStack = new PreIndexedAddressOperand(
          RegisterOperand.SP)
          .withExpr(new ImmediateOperand<>(-offset).withPrefixSymbol("#"))
          .withJump();
      context.addToCurrentLabel(STR(reg, shiftStack).withCond(shiftCond));

      /* Free register used for loading. */
      context.freeRegister(reg.getValue());
    }
    return counter;
  }

  /* Pushes the address of the instance so
   * that the function can find class instance fields. */
  protected int pushInstanceAddress(Context context,
      VariableIdentifier classInstanceIdentifier, SymbolKey classInstanceKey,
      int counter, ClassIdentifier classIdentifier) {

    SymbolTable currentSymbolTable = context.getCurrentSymbolTable();
    RegisterOperand reg = new RegisterOperand(context.getFreeRegister());

    SingleDataTransfer loadClassInstance = SingleDataTransfer.LDR(reg,
        new PreIndexedAddressOperand(RegisterOperand.SP)
            .withExpr(new ImmediateOperand<>(classInstanceIdentifier
                .getOffsetStack(currentSymbolTable, classInstanceKey))
                .withPrefixSymbol("#")));
    context.addToCurrentLabel(loadClassInstance);

    PreIndexedAddressOperand shiftStack = new PreIndexedAddressOperand(
        RegisterOperand.SP)
        .withExpr(new ImmediateOperand<>(-Context.SIZE_OF_ADDRESS)
            .withPrefixSymbol("#"))
        .withJump();
    context.addToCurrentLabel(STR(reg, shiftStack));

    currentSymbolTable.incrementOffset(4);
    currentSymbolTable.incrementTableSizeInBytes(4);

    /* At this point, the instance address is still in reg. Use this to find
     * the address to this class's instance table. */
    context.addToCurrentLabel(LDR(reg, new PreIndexedAddressOperand(reg)));

    /* Look up the offset of the function being called, and load its address
     * back into reg. Note that we must offset this again by SIZE_OF_ADDRESS,
     * because of how we used .word 0 at the start of every virtual table. */
    VirtualTable classVirtualTable = classIdentifier.getClassNode()
        .getClassVirtualTable();
    int functionOffset = classVirtualTable.getFunctionOffset(identifier);
    assert (functionOffset != -1);

    context.addToCurrentLabel(LDR(reg, new PreIndexedAddressOperand(reg)
        .withExpr(new ImmediateOperand<>(functionOffset * 4 + 4)
            .withPrefixSymbol("#"))));

    /* Branch and link to address stored in this register. */
    context.addToCurrentLabel(BLX(reg.toString()));

    /* Free register used for loading. */
    context.freeRegister(reg.getValue());

    return counter + 4;
  }

  /* Finally, restore the changes we have made to the function symbol table,
   * scope, and stack space after the call. Move result of function call to free register. */
  protected void restoreStateAfterCall(Context context,
      String currentClass, int counter, SymbolTable funcTable,
      boolean isExternalFunction) {
    SymbolTable currentSymbolTable = context.getCurrentSymbolTable();
    /* Finally, restore the changes we have made to the function symbol table
     * after the call. This ensures that the function symbol table is exactly
     * the same as when we entered the call. */
    currentSymbolTable.decrementOffset(counter);
    currentSymbolTable.decrementTableSizeInBytes(counter);

    /* Call the function using virtual tables if necessary. */
    if (isExternalFunction) {
      context.addToCurrentLabel(BL("_f_" + identifier));
    }

    /* Restore any stack space used by parameters to the function. */
    context.addToCurrentLabel(DataProcessing
        .ADD(RegisterOperand.SP, RegisterOperand.SP,
            new ImmediateOperand<>(funcTable.getParametersSizeInBytes())
                .withPrefixSymbol("#")));

    /* Move result of function call from R0 to free register */
    setRegister(new RegisterOperand(context.getFreeRegister()));
    context.addToCurrentLabel(MOV(getRegister(), RegisterOperand.R0));
  }

  @Override
  public String getInput() {
    return "call " + identifier + "(" + args.getInput() + ")";
  }
}
