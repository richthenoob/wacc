package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.*;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.PairType;
import org.antlr.v4.runtime.ParserRuleContext;

public class PairNode extends ExprNode {

  private final ExprNode fst;
  private final ExprNode snd;

  public PairNode(ExprNode fst, ExprNode snd) {
    this.fst = fst;
    this.snd = snd;
    setType(new PairType(fst.getType(), snd.getType()));
  }

  public ExprNode getFst() {
    return fst;
  }

  public ExprNode getSnd() {
    return snd;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public void translate(Context context) {
    Label<Instruction> label = context.getCurrentLabel();
    int bytesToAllocate = 4 * 2;
    int firstRegisterNum = context.getFreeRegister();
    label
        .addToBody(
            SingleDataTransfer.LDR(new RegisterOperand(0),
                new ImmediateOperand<>(bytesToAllocate).withPrefixSymbol("=")))
        .addToBody(Branch.BL("malloc"))
        .addToBody(
            new Move(new RegisterOperand(firstRegisterNum), new RegisterOperand(0), Condition.B));
    fst.translate(context);
    label
        .addToBody(SingleDataTransfer.LDR(new RegisterOperand(0),
            new ImmediateOperand<>(4).withPrefixSymbol("=")))
        .addToBody(Branch.BL("malloc"))
        .addToBody(
            SingleDataTransfer.STR(
                fst.getRegister(),
                new PreIndexedAddressOperand(new RegisterOperand(0))))
        .addToBody(
            SingleDataTransfer.STR(
                new RegisterOperand(0),
                new PreIndexedAddressOperand(new RegisterOperand(firstRegisterNum))));
    context.freeRegister(fst.getRegister().getValue());
    snd.translate(context);
    label
        .addToBody(SingleDataTransfer.LDR(new RegisterOperand(0),
            new ImmediateOperand<>(4).withPrefixSymbol("=")))
        .addToBody(Branch.BL("malloc"))
        .addToBody(
            SingleDataTransfer.STR(
                snd.getRegister(),
                new PreIndexedAddressOperand(new RegisterOperand(0))))
        .addToBody(
            SingleDataTransfer.STR(
                new RegisterOperand(0),
                new PreIndexedAddressOperand(
                    new RegisterOperand(firstRegisterNum))
                    .withExpr(new ImmediateOperand<>(4).withPrefixSymbol("#"))));
    context.freeRegister(snd.getRegister().getValue());
    setRegister(new RegisterOperand(firstRegisterNum));
  }

  @Override
  public String getInput() {
    return "(" + fst.getInput() + ", " + snd.getInput() + ")";
  }
}
