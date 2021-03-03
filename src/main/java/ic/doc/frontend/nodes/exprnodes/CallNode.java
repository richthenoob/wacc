package ic.doc.frontend.nodes.exprnodes;

import static ic.doc.backend.Instructions.Branch.BL;
import static ic.doc.backend.Instructions.Move.MOV;
import static ic.doc.backend.Instructions.SingleDataTransfer.STR;
import static ic.doc.backend.Instructions.operands.PreIndexedAddressOperand.PreIndexedAddressFixedOffsetJump;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.DataProcessing;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.frontend.identifiers.FunctionIdentifier;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.nodes.ArgListNode;
import ic.doc.frontend.semantics.SymbolKey;
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

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    String functionName = identifier;
    SymbolKey key = new SymbolKey(functionName, true);
    Identifier id = visitor.getCurrentSymbolTable().lookupAll(key);

    if (id == null) {
      /* Checks if function was defined in scope */
      setType(new ErrorType());
      visitor.getSemanticErrorList()
          .addScopeException(ctx, false, "Function", functionName);
    } else if (!(id instanceof FunctionIdentifier)) {
      /* Checks if id is an instance of function */
      setType(new ErrorType());
      visitor
          .getSemanticErrorList()
          .addException(
              ctx,
              "Identifier Variable is not an instance of function. Expected: Function. Actual: Variable.");
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
          .checkTypeListCompatibility(args.getType(), expectedParamListType))) {
        /* Checks if types of parameters passed in matches expected types */
        visitor
            .getSemanticErrorList()
            .addTypeException(ctx, args.getInput(), functionId.printTypes(),
                args.printTypes(), "");
      }
    }
  }

  @Override
  public void translate(Context context) {

    /* Save the previous symbol table so that we can restore it
     * after the function call. */
    SymbolTable previousSymbolTable = context.getCurrentSymbolTable();

    /* Look up function symbol table from func name. Use counter to track
     * the size of parameters that have been pushed onto the stack, ensuring
     * to restore this at the end of the function call. */
    SymbolTable funcTable = context.getFunctionTables().get(identifier);
    int counter = 0;

    for (int i = 0; i < args.getNumParas(); i++) {
      ExprNode arg = args.getParams().get(i);

      int offset;
      // calculate new stack pointer offset after storing each argument
      // bool and char means +1 and not +4
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
      funcTable.incrementOffset(offset);
      counter += offset;

      /* Store argument onto stack for the function to use. */
      RegisterOperand reg = arg.getRegister();
      PreIndexedAddressOperand shiftStack = PreIndexedAddressFixedOffsetJump(
          RegisterOperand.SP,
          new ImmediateOperand<>(true, -offset));
      context.addToCurrentLabel(STR(shiftCond, reg, shiftStack));

      /* Free register used for loading. */
      context.freeRegister(reg.getValue());
    }

    /* Finally, restore the changes we have made to the function symbol table
     * after the call. This ensures that the function symbol table is exactly
     * the same as when we entered the call. */
    funcTable.decrementOffset(counter);

    /* Call the function then restore to previous scope.
     * Also restore any stack space used by parameters to the function. */
    context.addToCurrentLabel(BL("f_" + identifier));
    context.setScope(previousSymbolTable);
    context.addToCurrentLabel(DataProcessing
        .ADD(RegisterOperand.SP(), RegisterOperand.SP(),
            new ImmediateOperand<>(true,
                funcTable.getFunctionParametersSizeInBytes())));

    /* Move result of function call from R0 to free register */
    setRegister(new RegisterOperand(context.getFreeRegister()));
    context.addToCurrentLabel(MOV(getRegister(), RegisterOperand.R0));
  }

  @Override
  public String getInput() {
    return "call " + identifier + "(" + args.getInput() + ")";
  }
}
