package ic.doc.frontend.nodes.exprnodes.Literals;

import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.Operand;
import ic.doc.backend.Instructions.OperandType;
import ic.doc.backend.Instructions.SingleDataTransfer;
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
  public void translate(List<Label<Instruction>> instructionLabels, List<Label<Data>> dataLabels) {
    int newIndex = dataLabels.size();
    Label<Data> newLabel = new Label<>("msg_" + newIndex);
    dataLabels.add(newIndex, newLabel);
    newLabel.addToBody(new Data(value.length(), value));

    Operand register = new Operand(OperandType.REG, 2);
    Operand operand = new Operand(OperandType.MEM, 0); // dummy value for value

    operand.setVarName(dataLabels.get(dataLabels.size() - 1).getFunctionLabel());
    instructionLabels
        .get(instructionLabels.size() - 1)
        .addToBody(new SingleDataTransfer(true, register, operand));
  }

  @Override
  public String getInput() {
    return value.substring(1, value.length() - 1);
  }
}
