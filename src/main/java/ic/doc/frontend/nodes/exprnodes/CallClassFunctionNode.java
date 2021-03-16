package ic.doc.frontend.nodes.exprnodes;

import static ic.doc.backend.instructions.Branch.BL;
import static ic.doc.backend.instructions.Move.MOV;
import static ic.doc.backend.instructions.SingleDataTransfer.STR;

import ic.doc.backend.Context;
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
    Identifier classInstanceIdentifier = checkIdentifier(visitor, ctx,
        classInstanceName, KeyTypes.VARIABLE, "Variable");

    /* Class identifier does not exist at all. */
    if (classInstanceIdentifier == null) {
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
    Identifier classIdentifier = checkIdentifier(visitor, ctx,
        className, KeyTypes.CLASS, "Class");

    /* Somehow found a variable that had a classType, but it contains a
     * class that doesn't exist. */
    if (classIdentifier == null) {
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

    /* Cannot find function defined in class. Checks if identifier is of type functionIdentifier. */
    if (!functionIdCheck(visitor, ctx, functionIdentifier)) {
      return;
    }

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
        expectedArgumentTypes, visitor.getCurrentSymbolTable()))) {
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
    /* Find class instance in current symbol table. */
    SymbolTable currentSymbolTable = context.getCurrentSymbolTable();
    SymbolKey classInstanceKey = new SymbolKey(classInstanceName,
        KeyTypes.VARIABLE);
    VariableIdentifier classInstanceIdentifier =
        (VariableIdentifier) currentSymbolTable.lookupAll(classInstanceKey);

    /* Find class in symbol table that corresponds to this instance. */
    ClassIdentifier classIdentifier =
        (ClassIdentifier) findIdentifier(currentSymbolTable,
            ((ClassType) classInstanceIdentifier.getType()).getClassName(), KeyTypes.CLASS);

    /* Find function symbol table from class node. */
    ClassNode classNode = classIdentifier.getClassNode();
    SymbolTable funcTable = classNode.getFunctionTables().get(getIdentifier());

    /* Use counter to track the size of parameters that have been pushed
     * onto the stack, ensuring to restore this at the end of the function call. */
    int counter = 0;

    /* Stores arguments of call onto stack to be accessed by function later. */
    counter = storeArguments(context, counter);

    /* After pushing arguments, push the address of the instance so
     * that the function can find class instance fields. */
    counter = pushInstanceAddress(context, classInstanceIdentifier, classInstanceKey, counter, classIdentifier);

    /* Finally, restore the changes we have made to the function symbol table,
     * scope, and stack space after the call. Move result of function call to free register. */
    restoreStateAfterCall(context, classNode.getClassName(), counter, funcTable);
  }

  @Override
  public String getInput() {
    return "call" + classInstanceName + "." + getIdentifier() + "(" + getArgs()
        .getInput() + ")";
  }
}
