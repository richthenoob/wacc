package ic.doc.frontend.nodes.exprnodes;

import static ic.doc.backend.Instructions.Branch.BL;
import static ic.doc.backend.Instructions.Move.MOV;
import static ic.doc.backend.Instructions.SingleDataTransfer.STR;
import static ic.doc.backend.Instructions.operands.PreIndexedAddressOperand.PreIndexedAddressFixedOffsetJump;

import ic.doc.backend.Context;
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
      visitor.getSemanticErrorList().addScopeException(ctx, false, "Function", functionName);
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
      } else if (!(Type.checkTypeListCompatibility(args.getType(), expectedParamListType))) {
        /* Checks if types of parameters passed in matches expected types */
        visitor
            .getSemanticErrorList()
            .addTypeException(ctx, args.getInput(), functionId.printTypes(), args.printTypes(), "");
      }
    }
  }

  @Override
  public void translate(Context context) {
    /* Look up function symbol table from func name */
    SymbolTable funcTable = context.getFunctionTables().get(identifier);
    context.setScope(funcTable);
    int offset;

    for (int i = 0; i < args.getNumParas(); i++) {
      ExprNode arg = args.getParams().get(i);

      // calculate new stack pointer offset after storing each argument
      // bool and char means +1 and not +4
      String shiftCond = "";
      if (arg.getType() instanceof BoolType || arg.getType() instanceof CharType) {
        offset = 1;
        shiftCond = "B";
      } else {
        offset = 4;
      }

      /* Load argument onto a free register */
      arg.translate(context);

      /* Store argument onto stack */
      RegisterOperand reg = arg.getRegister();
      PreIndexedAddressOperand shiftStack = PreIndexedAddressFixedOffsetJump(RegisterOperand.SP,
          new ImmediateOperand<>(true, -offset));
      context.addToCurrentLabel(STR(shiftCond, reg, shiftStack));

      /* Free register used for loading */
      context.freeRegister(reg.getValue());
    }

    context.addToCurrentLabel(BL("f_" + identifier));
    context.restoreScope();

    /* Move result of function call from R0 to free register */
    setRegister(new RegisterOperand(context.getFreeRegister()));
    context.addToCurrentLabel(MOV(getRegister(), RegisterOperand.R0));
  }

  @Override
  public String getInput() {
    return "call " + identifier + "(" + args.getInput() + ")";
  }
}
