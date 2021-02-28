package ic.doc.frontend.nodes.exprnodes.Literals;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.SingleDataTransfer;
import ic.doc.backend.Instructions.operands.LabelAddressOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.CharType;
import java.util.List;
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
  public void translate(Context context) {
    List<Label<Data>> dataLabels = context.getDataLabels();
    int newIndex = dataLabels.size();
    Label<Data> newLabel = new Label<>("msg_" + newIndex);
    dataLabels.add(newIndex, newLabel);
    newLabel.addToBody(new Data(1, value.toString()));

    RegisterOperand register = new RegisterOperand(context.getFreeRegister());
    LabelAddressOperand operand =
        new LabelAddressOperand(
            dataLabels.get(dataLabels.size() - 1).getFunctionLabel()); // dummy value for value
    context.addToCurrentLabel(SingleDataTransfer.LDR(register, operand));
  }

  @Override
  public String getInput() {
    return value.toString();
  }
}
