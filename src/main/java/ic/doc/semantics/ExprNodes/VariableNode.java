package ic.doc.semantics.ExprNodes;

import ic.doc.semantics.IdentifierObjects.Identifier;
import ic.doc.semantics.IdentifierObjects.IdentifierNode;
import ic.doc.semantics.SymbolKey;
import ic.doc.semantics.Types.ErrorType;
import ic.doc.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class VariableNode extends ExprNode {

  private final IdentifierNode identifierNode;

  public VariableNode(IdentifierNode identifierNode) {
    this.identifierNode = identifierNode;
  }

  public String getName() {
    return identifierNode.getName();
  }

  @Override
  public String getInput() {
    // return something like "type: name"
//    return getType().toString() + ":" + getName();
    return getName();
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    SymbolKey key = new SymbolKey(getName(), false);
    Identifier id = visitor.getCurrentSymbolTable().lookupAll(key);
    if (id == null) {
      setType(new ErrorType());
      visitor.addException(ctx, "Variable " + getName() + " is not defined in this scope.");
      return;
    }

    setType(id.getType());
  }

}
