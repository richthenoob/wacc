package ic.doc.semantics.ExprNodes.Literals;

import ic.doc.semantics.Types.BoolType;
import ic.doc.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

/* Either 'true' or 'false' */
public class BooleanLiteralNode extends LiteralNode {

  private final boolean value;

  public BooleanLiteralNode(boolean value) {
    this.value = value;
    setType(new BoolType());
  }

  public boolean getValue() {
    return value;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public String getInput() {
    return Boolean.toString(value);
  }
}
