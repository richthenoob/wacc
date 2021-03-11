package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ErrorType;
import org.antlr.v4.runtime.ParserRuleContext;

public class ClassVariableNode extends VariableNode {

  public ClassVariableNode(String identifier) {
    super(identifier);
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    SymbolKey key = new SymbolKey(getName(), KeyTypes.CLASS);
    /* Checks if name was defined in symbol table */
    Identifier id = visitor.getCurrentSymbolTable().lookupAll(key);
    if (id == null) {
      setType(new ErrorType());
      visitor.getSemanticErrorList().addScopeException(ctx, false, "Class", getName());
      return;
    }

    setType(id.getType());
  }

  @Override
  public void translate(Context context) {

  }

  @Override
  public String getInput() {
    return getName();
  }
}
