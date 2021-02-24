package ic.doc.frontend.nodes.exprnodes.Literals;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.SingleDataTransfer;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.BoolType;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

/* Either 'true' or 'false' */
public class BooleanLiteralNode extends LiteralNode {

  private final boolean value;

  public BooleanLiteralNode(boolean value) {
    this.value = value;
    setType(new BoolType());
  }

  public boolean getValue() {
    return value;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public void translate(Context context) {
    List<Label<Instruction>> instructionLabels = context.getInstructionLabels();
    int bool = value ? 1 : 0;
    ImmediateOperand operand = new ImmediateOperand(bool);
    RegisterOperand register = new RegisterOperand(context.getFreeRegister());
    instructionLabels
        .get(instructionLabels.size() - 1)
        .addToBody(SingleDataTransfer.LDR(register, operand));
  }

  @Override
  public String getInput() {
    return Boolean.toString(value);
  }
}
