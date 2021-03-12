package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.frontend.identifiers.ClassIdentifier;
import ic.doc.frontend.identifiers.FunctionIdentifier;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.nodes.ArgListNode;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ClassType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.Type;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class CallClassFunctionNode extends CallNode {

  private final String classInstanceName;

  public CallClassFunctionNode(String classInstanceName,
      String functionName, ArgListNode args) {
    super(functionName, args);
    this.classInstanceName = classInstanceName;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    SymbolTable currentSymbolTable = visitor.getCurrentSymbolTable();

    /* Check that class instance exists. */
    SymbolKey classInstanceKey = new SymbolKey(classInstanceName,
        KeyTypes.VARIABLE);
    Identifier classInstanceIdentifier = currentSymbolTable
        .lookupAll(classInstanceKey);

    /* Class identifier does not exist at all. */
    if (classInstanceIdentifier == null) {
      setType(new ErrorType());
      visitor.getSemanticErrorList()
          .addScopeException(ctx, false, "Variable", classInstanceName);
      return;
    }

    /* Since we looked up using a key with KeyTypes.VARIABLE, this should never
     * return an identifier that IS NOT a VariableIdentifier. */
    assert (classInstanceIdentifier instanceof VariableIdentifier);

    /* If we reached here, it means we have found a variable identifier.
     * Now we need to check if the class contains function functionName. */

    /* Found VariableIdentifier but it was not a ClassType. */
    Type identifierType = classInstanceIdentifier.getType();
    if (!(identifierType instanceof ClassType)) {
      setType(new ErrorType());
      visitor.getSemanticErrorList()
          .addTypeException(ctx, classInstanceName, "CLASS",
              identifierType.toString(), "");
      return;
    }

    /* First find the class in the symbol table. */
    ClassType classType = (ClassType) identifierType;
    String className = classType.getClassName();
    SymbolKey classKey = new SymbolKey(className, KeyTypes.CLASS);
    Identifier classIdentifier = currentSymbolTable.lookupAll(classKey);

    /* Somehow found a variable that had a classType, but it contains a
     * class that doesn't exist. */
    if (classIdentifier == null) {
      setType(new ErrorType());
      visitor.getSemanticErrorList()
          .addScopeException(ctx, false, "Class", className);
      return;
    }

    /* Since we looked up using a key with KeyTypes.CLASS, this should never
     * return an identifier that IS NOT a ClassIdentifier. */
    assert (classIdentifier instanceof ClassIdentifier);

    /* We can now check if the class actually has a function with
     * name functionName. */
    ClassIdentifier classIdentifier_ = (ClassIdentifier) classIdentifier;
    SymbolTable classSymbolTable = classIdentifier_.getClassSymbolTable();

    String functionName = getIdentifier();
    SymbolKey functionKey = new SymbolKey(functionName, KeyTypes.FUNCTION);
    Identifier functionIdentifier = classSymbolTable.lookup(functionKey);

    /* Cannot find function defined in class. */
    if (functionIdentifier == null) {
      setType(new ErrorType());
      visitor.getSemanticErrorList()
          .addScopeException(ctx, false, "Function", functionName);
      return;
    }

    /* Since we looked up using a key with KeyTypes.FUNCTION, this should never
     * return an identifier that IS NOT a FunctionIdentifier. */
    assert (functionIdentifier instanceof FunctionIdentifier);

    /* Check that function is passed correct argument types. */
    FunctionIdentifier functionId = ((FunctionIdentifier) functionIdentifier);
    List<Type> inputArgumentTypes = getArgs().getType();
    List<Type> expectedArgumentTypes = functionId.getParamTypeList();

    /* Check number of arguments are the same. */
    if (!(inputArgumentTypes.size() == expectedArgumentTypes.size())) {
      setType(new ErrorType());
      visitor
          .getSemanticErrorList()
          .addException(
              ctx,
              "Incompatible parameter count at "
                  + getInput()
                  + ". Expected count: "
                  + expectedArgumentTypes.size()
                  + ". Actual count: "
                  + inputArgumentTypes.size()
                  + ".");
      return;
    }

    /* Check that each argument matches its expected type. */
    if (!(Type.checkTypeListCompatibility(inputArgumentTypes,
        expectedArgumentTypes))) {
      setType(new ErrorType());
      visitor
          .getSemanticErrorList()
          .addTypeException(ctx, getArgs().getInput(), functionId.printTypes(),
              getArgs().printTypes(), "");
      return;
    }

    /* If all the checks pass, then we are done! */
    setType(functionId.getType());
  }

  @Override
  public void translate(Context context) {

  }

  @Override
  public String getInput() {
    return "call" + classInstanceName + "." + getIdentifier() + "(" + getArgs()
        .getInput() + ")";
  }
}
