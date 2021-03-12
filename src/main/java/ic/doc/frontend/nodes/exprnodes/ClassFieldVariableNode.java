package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.frontend.identifiers.ClassIdentifier;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ClassType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

public class ClassFieldVariableNode extends VariableNode{

  private final String className;
  private final String varName;

  /* Corresponds to references to objects in classes. i.e. c.x in "int j = c.x" */
  public ClassFieldVariableNode(String className, String identifier) {
    super(className + "." + identifier);
    this.className = className;
    this.varName = identifier;
  }

  public String getClassName() {
    return className;
  }

  public String getVarName() {
    return varName;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Checks if class instance was defined in symbol table */
    Identifier classVarId = checkIdentifier(visitor, ctx,
        getClassName(), KeyTypes.VARIABLE, "Class instance");
    if (classVarId == null) return;

    /* Checks if class was defined in symbol table */
    Type classType = classVarId.getType();
    String classGeneralName = ((ClassType) classType).getClassName();
    Identifier classId = checkIdentifier(visitor, ctx,
        classGeneralName, KeyTypes.CLASS, "Class");
    if (classId == null) return;

    /* Checks if variable was defined in symbol table for class */
    SymbolTable classSymbolTable = ((ClassIdentifier) classId).getClassSymbolTable();
    SymbolKey varKey = new SymbolKey(getVarName(), KeyTypes.VARIABLE);
    Identifier varId = classSymbolTable.lookupAll(varKey);
    if (varId == null) {
      setType(new ErrorType());
      visitor.getSemanticErrorList().addScopeException(ctx, false, "Variable", getName());
      return;
    }

    /* Sets type to type of variable */
    setType(varId.getType());
  }

  @Override
  public void translate(Context context) {

  }

  @Override
  public String getInput() {
    return getName();
  }

}
