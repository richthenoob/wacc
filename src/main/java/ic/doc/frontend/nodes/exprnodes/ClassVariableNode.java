package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ErrorType;
import org.antlr.v4.runtime.ParserRuleContext;

public class ClassVariableNode extends VariableNode {

  private final String className;
  private final String varName;

  public ClassVariableNode(String className, String identifier) {
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
    /* Checks if class was defined in symbol table */
    SymbolKey classKey = new SymbolKey(getClassName(), KeyTypes.CLASS);
    Identifier classId = visitor.getCurrentSymbolTable().lookupAll(classKey);
    if (classId == null) {
      setType(new ErrorType());
      visitor.getSemanticErrorList().addScopeException(ctx, false, "Class", getName());
      return;
    }

    /* Checks if variable was defined in symbol table for class */

    setType(classId.getType());
  }

  @Override
  public void translate(Context context) {

  }

  @Override
  public String getInput() {
    return getName();
  }
}
