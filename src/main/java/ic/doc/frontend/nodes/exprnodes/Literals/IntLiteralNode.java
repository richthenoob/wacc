package ic.doc.frontend.nodes.exprnodes.Literals;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.SingleDataTransfer;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.frontend.errors.SyntaxException;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.IntType;
import org.antlr.v4.runtime.ParserRuleContext;

/* Consists of a sequence of decimal digits, optionally preceded
 * by a '+' or a '-' symbol. */
public class IntLiteralNode extends BasicLiteralNode {

  /* Store a larger value than the WACC specification, in case we are passed in
   * a very large value. */
  private final Long value;

  public IntLiteralNode(Long value) {
    this.value = value;
    setType(new IntType());
  }

  public Long getValue() {
    return value;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Check that input integer is within valid bounds */
    if (getValue() > IntType.INT_MAX || getValue() < IntType.INT_MIN) {
      throw new SyntaxException(
          "Integer is of value: "
              + getValue()
              + ", but must be between "
              + IntType.INT_MIN
              + " and "
              + IntType.INT_MAX,
          ctx.getStart().getLine(),
          ctx.getStart().getCharPositionInLine());
    }
  }

  @Override
  public void translate(Context context) {
    /* Load integer value into register */
    ImmediateOperand operand = new ImmediateOperand<>(value.intValue()).withPrefixSymbol("=");
    RegisterOperand register = new RegisterOperand(context.getFreeRegister());
    context.addToCurrentLabel(SingleDataTransfer.LDR(register, operand));
    setRegister(register);
  }

  @Override
  public String getInput() {
    return value.toString();
  }
}
