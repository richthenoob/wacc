package ic.doc.frontend.nodes.exprnodes.Literals;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.SingleDataTransfer;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.BoolType;
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
    /* Load int representation of bool into a register */
    int bool = value ? 1 : 0;
    ImmediateOperand operand = new ImmediateOperand<>(bool).withPrefixSymbol("=");
    RegisterOperand register = new RegisterOperand(context.getFreeRegister());
    context.addToCurrentLabel(SingleDataTransfer.LDR(register, operand));
    setRegister(register);
  }

  @Override
  public String getInput() {
    return Boolean.toString(value);
  }
}
