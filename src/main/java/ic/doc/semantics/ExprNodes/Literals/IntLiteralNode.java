package ic.doc.semantics.ExprNodes.Literals;

import ic.doc.SyntaxException;
import ic.doc.semantics.Types.IntType;
import ic.doc.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

/* Consists of a sequence of decimal digits, optionally preceded
 * by a '+' or a '-' symbol. */
public class IntLiteralNode extends LiteralNode {

  private final Long value; // Can be negative

  public IntLiteralNode(Long value) {
    this.value = value;
    setType(new IntType());
  }

  public Long getValue() {
    return value;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    if (getValue() > IntType.INTMAX || getValue() < IntType.INTMIN) {
//      throw new SyntaxException("Integer is of value: " + getValue() + ", but must be between " +
//              IntType.INTMIN + " and " + IntType.INTMAX, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
    }
  }

  @Override
  public String getInput() {
    return value.toString();
  }
}
