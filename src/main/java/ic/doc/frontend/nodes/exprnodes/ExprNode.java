package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Instructions.Operand;
import ic.doc.frontend.nodes.Node;
import ic.doc.frontend.types.Type;

public abstract class ExprNode extends Node {

  private Type type;
  private Operand register;

  public void setRegister(Operand register) {
    this.register = register;
  }

  public Operand getRegister() {
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
}
