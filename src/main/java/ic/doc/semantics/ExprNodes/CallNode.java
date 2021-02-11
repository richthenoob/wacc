package ic.doc.semantics.ExprNodes;

import ic.doc.semantics.IdentifierObjects.FunctionIdentifier;
import ic.doc.semantics.IdentifierObjects.Identifier;
import ic.doc.semantics.IdentifierObjects.IdentifierNode;
import ic.doc.semantics.IdentifierObjects.ParamIdentifier;
import ic.doc.semantics.ArgListNode;
import ic.doc.semantics.SymbolKey;
import ic.doc.semantics.Types.ErrorType;
import ic.doc.semantics.Types.Type;
import ic.doc.semantics.Visitor;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;


public class CallNode extends ExprNode {

  private final IdentifierNode identifier;
  private final ArgListNode args;

  public CallNode(IdentifierNode identifier, ArgListNode args) {
    this.identifier = identifier;
    this.args = args;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    String functionName = identifier.getName();
    SymbolKey key = new SymbolKey(functionName, true);
    Identifier id = visitor.getCurrentSymbolTable().lookupAll(key);
    if (id == null) {
      setType(new ErrorType());
      visitor.getSemanticErrorList().addException(ctx,
          "Couldn't find " + functionName + " in symbol table!");
    } else if (!(id instanceof FunctionIdentifier)) {
      String instance = id instanceof ParamIdentifier ? "Param" : "Variable";
      setType(new ErrorType());
      visitor.getSemanticErrorList().addException(ctx,
          "Identifier " + id
              + " is not an instance of function. Expected: Function. Actual: "
              + instance);
    } else {
      FunctionIdentifier functionId = (FunctionIdentifier) id;
      Type functionType = functionId.getType();
      setType(functionType);
      List<Type> expectedParamListType = functionId.getParamTypeList();
      if (!(args.getNumParas() == expectedParamListType.size())) {
        visitor.getSemanticErrorList().addException(ctx,
            "Incompatible parameter count at " + this.getInput()
                + ". Expected count: " + expectedParamListType.size()
                + ". Actual count: " + args.getNumParas()
                + ".");
      } else if (!args.getType().equals(expectedParamListType)) {
        visitor.getSemanticErrorList().addTypeException(ctx,
            args.getInput(), functionId.printTypes(), args.printTypes());
      }
    }
  }

  @Override
  public String getInput() {
    return "call " + identifier.getName() + "(" + args.getInput() + ")";
  }
}
