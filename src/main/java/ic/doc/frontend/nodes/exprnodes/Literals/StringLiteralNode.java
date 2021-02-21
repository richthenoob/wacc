package ic.doc.frontend.nodes.exprnodes.Literals;

import ic.doc.backend.Instructions.Instruction;
import ic.doc.frontend.types.CharType;
import ic.doc.frontend.types.StringType;
import ic.doc.frontend.semantics.Visitor;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

/* Multiple ASCII character between two " symbols. A '\' can be used to escape the character
 * immediately following the '\'. */
public class StringLiteralNode extends LiteralNode {

  private final String value;

  public StringLiteralNode(String value) {
    this.value = value;
    setType(new StringType());
  }

  public String getValue() {
    return value;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Check that all characters in a string are valid. */
    for (char c : getValue().toCharArray()) {
      if (!CharType.isValidChar(c)) {
        visitor.getSemanticErrorList().addTokenException(ctx, Character.toString(c), getValue());
      }
    }
  }

  @Override
  public List<Instruction> translate() {
    return null;
  }

  @Override
  public String getInput() {
    return value.substring(1, value.length() - 1);
  }
}
