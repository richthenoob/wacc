package ic.doc.frontend.nodes.exprnodes.Literals;


import ic.doc.frontend.types.CharType;
import ic.doc.frontend.semantics.Visitor;
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
    /* Checks that input character tokens are valid */
    if (!CharType.isValidChar(getValue())) {
      visitor.getSemanticErrorList().addException(ctx, "Invalid character token at " + value);
    }
  }

  @Override
  public String getInput() {
    return value.toString();
  }
}
