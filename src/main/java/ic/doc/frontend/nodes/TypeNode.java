package ic.doc.frontend.nodes;

import ic.doc.backend.Context;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

public class TypeNode extends Node {

  private final Type type;

  public TypeNode(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public void translate(Context context) {}

  public String getInput() {
    return getType().toString();
  }
}
