package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.*;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.PairType;
import java.util.List;
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
            SingleDataTransfer.LDR(new RegisterOperand(0), new ImmediateOperand(bytesToAllocate)))
        .addToBody(Branch.BL("malloc"))
        .addToBody(
            new Move(new RegisterOperand(firstRegisterNum), new RegisterOperand(0), Condition.B));
    fst.translate(context);
    label
        .addToBody(SingleDataTransfer.LDR(new RegisterOperand(0), new ImmediateOperand(4)))
        .addToBody(Branch.BL("malloc"))
        .addToBody(
            SingleDataTransfer.STR(
                fst.getRegister(),
                PreIndexedAddressOperand.PreIndexedAddressZeroOffset(new RegisterOperand(0))))
        .addToBody(
            SingleDataTransfer.STR(
                new RegisterOperand(0),
                PreIndexedAddressOperand.PreIndexedAddressZeroOffset(
                    new RegisterOperand(firstRegisterNum))));
    context.freeRegister(fst.getRegister().getValue());
    snd.translate(context);
    label
        .addToBody(SingleDataTransfer.LDR(new RegisterOperand(0), new ImmediateOperand(4)))
        .addToBody(Branch.BL("malloc"))
        .addToBody(
            SingleDataTransfer.STR(
                snd.getRegister(),
                PreIndexedAddressOperand.PreIndexedAddressZeroOffset(new RegisterOperand(0))))
        .addToBody(
            SingleDataTransfer.STR(
                new RegisterOperand(0),
                PreIndexedAddressOperand.PreIndexedAddressFixedOffset(
                    new RegisterOperand(firstRegisterNum), new ImmediateOperand(true,4))));
    context.freeRegister(snd.getRegister().getValue());
    setRegister(new RegisterOperand(firstRegisterNum));
  }

  @Override
  public String getInput() {
    return "(" + fst.getInput() + ", " + snd.getInput() + ")";
  }
}
