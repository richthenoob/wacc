package ic.doc.frontend.nodes.exprnodes;

import static ic.doc.backend.Instructions.Branch.BL;
import static ic.doc.backend.Instructions.Move.MOV;
import static ic.doc.backend.Instructions.SingleDataTransfer.STR;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.identifiers.FunctionIdentifier;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.ParamIdentifier;
import ic.doc.frontend.nodes.ArgListNode;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
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
      String instance = id instanceof ParamIdentifier ? "Param" : "Variable";
      setType(new ErrorType());
      visitor
          .getSemanticErrorList()
          .addException(
              ctx,
              "Identifier "
                  + id
                  + " is not an instance of function. Expected: Function. Actual: "
                  + instance);
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
    // lookup function symbol table from func name
    SymbolTable funcTable = context.getFunctionTables().get(identifier);
    context.setScope(funcTable);

    context.addToCurrentLabel(BL("f_" + identifier));

    for (ExprNode arg : args.getParams()) {
      // push arguments onto stack

      // lookup each argument in function symbol table
      // update entry corresponding to argument to the offset (sp, sp+4, etc)
      // bool and char means +1 and not +4
    }

    context.addToCurrentLabel(MOV(new RegisterOperand(4), RegisterOperand.R0));
    context.addToCurrentLabel(STR(new RegisterOperand(4), RegisterOperand.SP));
  }

  @Override
  public String getInput() {
    return "call " + identifier + "(" + args.getInput() + ")";
  }
}
