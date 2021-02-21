package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Label;
import ic.doc.frontend.identifiers.FunctionIdentifier;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.ParamIdentifier;
import ic.doc.frontend.nodes.ArgListNode;
import ic.doc.frontend.semantics.SymbolKey;
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
  public void translate(
      List<Label<Instruction>> instructionLabels,
      List<Label<Data>> dataLabels) {
  }

  @Override
  public String getInput() {
    return "call " + identifier + "(" + args.getInput() + ")";
  }
}
