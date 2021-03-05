package ic.doc.frontend.nodes.exprnodes.Literals;

import ic.doc.backend.Context;
import ic.doc.backend.Data;
import ic.doc.backend.instructions.SingleDataTransfer;
import ic.doc.backend.instructions.operands.LabelAddressOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.CharType;
import ic.doc.frontend.types.StringType;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

/* Multiple ASCII character between two " symbols. A '\' can be used to escape the character
 * immediately following the '\'. */
public class StringLiteralNode extends LiteralNode {

  private final String value;

  public StringLiteralNode(String value) {
    this.value = value.substring(1, value.length() - 1);
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
  public void translate(Context context) {
    /* Always add to the data regardless of declaration or not */
    RegisterOperand register = new RegisterOperand(context.getFreeRegister());
    setRegister(register);

    /* Special size function that ignores escape char */
    int size = strSize(value);
    List<Label<Data>> dataLabels = context.getDataLabels();

    /* Find out next index of msg_ which corresponds to current size*/
    int newIndex = dataLabels.size();
    Label<Data> newLabel = new Label<>("msg_" + newIndex);

    /* Adds the data labels and loads label to register */
    dataLabels.add(newIndex, newLabel);
    newLabel.addToBody(new Data(size, value));
    LabelAddressOperand operand = new LabelAddressOperand(newLabel.getFunctionLabel());
    context.getCurrentLabel().addToBody(SingleDataTransfer.LDR(register, operand));
  }

  @Override
  public String getInput() {
    return value;
  }

  // TODO: Use this for all stuff in data, such as for the placeholders in PredefinedFunctions
  private int strSize(String s){
    int size = 0;
    for(int i = 0; i < s.length(); i++){
      if(s.charAt(i) == '\\'){
        i++;
      }
      size++;
    }
    return size;
  }
}
