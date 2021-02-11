package ic.doc.semantics;

import ic.doc.semantics.Types.Type;
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
  }

  public String getInput() {
    return getType().toString();
  }
}
