package ic.doc.frontend.nodes;

import ic.doc.backend.Context;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

public class ParamNode extends Node {

  private final Type type;
  private final String input;

  public Type getType() {
    return type;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public void translate(Context context) {
    /* No translation needed. */
  }

  public ParamNode(Type type, String input) {
    this.type = type;
    this.input = input;
  }

  public String getInput() {
    return input;
  }
}
