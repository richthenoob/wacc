package ic.doc.semantics.ExprNodes.Literals;


import ic.doc.semantics.Types.CharType;
import ic.doc.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

/* Single ASCII character between two ' symbols. A '\' can be used to escape the character
 * immediately following the '\'. */
public class CharacterLiteralNode extends LiteralNode {

  private final Character value;

  public CharacterLiteralNode(Character value) {
    this.value = value;
    setType(new CharType());
  }

  public Character getValue() {
    return value;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    if (!CharType.isValidChar(getValue())) {
      visitor.addException(ctx, "Invalid character token at " + value);
    }
  }

  @Override
  public String getInput() {
    return value.toString();
  }
}
