package ic.doc.frontend.nodes.exprnodes.Literals;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.SingleDataTransfer;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.LabelAddressOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.CharType;
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
    RegisterOperand register = new RegisterOperand(context.getFreeRegister());
    setRegister(register);
    Label<Data> label = context.getSpecificLabel(value.toString());
    /* If label dosnt exist, just load the value as it is non declaration*/
    if (label == null) {
      ImmediateOperand operand = new ImmediateOperand<>(value).withPrefixSymbol("=");
      context.getCurrentLabel().addToBody(SingleDataTransfer.LDR(register, operand));
    }
    /* If label is found, it means it was part of a declaration and need to load the label name eg LDR r4 ,=msg_1 */
    else {
      LabelAddressOperand operand = new LabelAddressOperand(label.getFunctionLabel());
      context.getCurrentLabel().addToBody(SingleDataTransfer.LDR(register, operand));
    }
  }

  @Override
  public String getInput() {
    return value.toString();
  }
}
