package ic.doc.frontend.nodes.exprnodes.Literals;

import ic.doc.backend.Instructions.Instruction;
import ic.doc.frontend.types.BoolType;
import ic.doc.frontend.semantics.Visitor;
import java.util.List;
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
  public List<Instruction> translate() {
    return null;
  }

  @Override
  public String getInput() {
    return Boolean.toString(value);
  }
}
