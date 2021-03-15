package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.nodes.Node;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class ExprNode extends Node {

  private Type type;
  private RegisterOperand register;

  public void setRegister(RegisterOperand register) {
    this.register = register;
  }

  public RegisterOperand getRegister() {
    return register;
  }

  public void setType(Type type) {
    this.type = type;
  }

  /* Returns type of expression. */
  public Type getType() {
    return type;
  }

  /* Returns string representation of left-most expression.
  For use in printing semantic error messages. */
  public abstract String getInput();

  /* Given a name and a keyType, checks if name can be found in current symbol table.
  *  Throws an error with errorLabel if otherwise.*/
  public Identifier checkIdentifier(Visitor visitor, ParserRuleContext ctx,
      String name, KeyTypes keyType, String errorLabel) {
    SymbolKey key = new SymbolKey(name, keyType);
    Identifier id = visitor.getCurrentSymbolTable().lookupAll(key);
    if (id == null) {
      setType(new ErrorType());
      visitor.getSemanticErrorList().addScopeException(ctx, false, errorLabel, name);
      return null;
    }
    return id;
  }
}
