package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.*;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
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
    /* Allocate 4 bytes for each element of pair */
    int bytesToAllocate = 4 * 2;
    int firstRegisterNum = context.getFreeRegister();

    /* Move value into r0 for call to malloc */
    context.getCurrentLabel()
        .addToBody(
            SingleDataTransfer.LDR(new RegisterOperand(0),
                new ImmediateOperand<>(bytesToAllocate).withPrefixSymbol("=")))
        .addToBody(Branch.BL("malloc"))
        .addToBody(
            new Move(new RegisterOperand(firstRegisterNum), new RegisterOperand(0), Condition.B));

    /* translate the two pair elements individually */
    translatePair(context, fst,
        new PreIndexedAddressOperand(new RegisterOperand(firstRegisterNum)));

    translatePair(context, snd,
        new PreIndexedAddressOperand(new RegisterOperand(firstRegisterNum))
            .withExpr(new ImmediateOperand<>(4).withPrefixSymbol("#")));

    setRegister(new RegisterOperand(firstRegisterNum));
  }

  /* Stores element value at address returned by malloc */
  private void translatePair(Context context, ExprNode elem, PreIndexedAddressOperand reg) {
    elem.translate(context);

    context.getCurrentLabel()
        /* Mallocs space for element */
        .addToBody(SingleDataTransfer.LDR(new RegisterOperand(0),
            new ImmediateOperand<>(4).withPrefixSymbol("=")))
        .addToBody(Branch.BL("malloc"))
        .addToBody(
            SingleDataTransfer.STR(
                elem.getRegister(),
                new PreIndexedAddressOperand(new RegisterOperand(0))))
        .addToBody(
            SingleDataTransfer.STR(new RegisterOperand(0), reg));

    /* Free register originally used for individual element */
    context.freeRegister(elem.getRegister().getValue());
  }

  @Override
  public String getInput() {
    return "(" + fst.getInput() + ", " + snd.getInput() + ")";
  }
}
