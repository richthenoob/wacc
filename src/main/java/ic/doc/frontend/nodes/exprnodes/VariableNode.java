package ic.doc.frontend.nodes.exprnodes;

import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class VariableNode extends ExprNode {

  private final String identifier;

  public VariableNode(String identifier) {
    this.identifier = identifier;
  }

  public String getName() {
    return identifier;
  }

  @Override
  public String getInput() {
    return getName();
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    SymbolKey key = new SymbolKey(getName(), false);
    /* Checks if name was defined in symbol table */
    Identifier id = visitor.getCurrentSymbolTable().lookupAll(key);
    if (id == null) {
      setType(new ErrorType());
      visitor.getSemanticErrorList().addScopeException(ctx, false, "Variable", getName());
      return;
    }

    setType(id.getType());
  }
}
