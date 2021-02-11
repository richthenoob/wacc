package ic.doc.semantics.ExprNodes.Literals;


import ic.doc.semantics.Types.CharType;
import ic.doc.semantics.Types.StringType;
import ic.doc.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class StringLiteralNode extends LiteralNode {

  private final String value;

  public StringLiteralNode(String value) {
    this.value = value;
    setType(new StringType());
  }

  public String getValue() {
    return value;
  }

  /* Check that all characters in a string are valid. */
  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    for (char c : getValue().toCharArray()) {
      if (!CharType.isValidChar(c)) {
        visitor.getSemanticErrorList().addTokenException(ctx, Character.toString(c), getValue());
      }
    }
  }

  @Override
  public String getInput() {
    return value.substring(1, value.length() - 1);
  }
}
