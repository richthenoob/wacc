package ic.doc.frontend.nodes.exprnodes.Literals;

import ic.doc.frontend.errors.SyntaxException;
import ic.doc.frontend.types.IntType;
import ic.doc.frontend.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

/* Consists of a sequence of decimal digits, optionally preceded
 * by a '+' or a '-' symbol. */
public class IntLiteralNode extends LiteralNode {

  /* Store a larger value than the WACC specification, in case we are passed in
   * a very large value. */
  private final Long value;

  public IntLiteralNode(Long value) {
    this.value = value;
    setType(new IntType());
  }

  public Long getValue() {
    return value;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    if (getValue() > IntType.INT_MAX || getValue() < IntType.INT_MIN) {
      throw new SyntaxException(
          "Integer is of value: " + getValue() +
              ", but must be between " + IntType.INT_MIN +
              " and " + IntType.INT_MAX,
          ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
    }
  }

  @Override
  public String getInput() {
    return value.toString();
  }
}
