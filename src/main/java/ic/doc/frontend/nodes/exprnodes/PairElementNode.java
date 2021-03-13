package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.Instruction;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.backend.PredefinedFunctions;
import ic.doc.frontend.nodes.exprnodes.Literals.PairLiteralNode;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.PairType;

import org.antlr.v4.runtime.ParserRuleContext;

import static ic.doc.backend.instructions.Branch.BL;
import static ic.doc.backend.instructions.Move.MOV;
import static ic.doc.backend.instructions.SingleDataTransfer.LDR;

public class PairElementNode extends ExprNode {

  public enum PairPosition {
    FST,
    SND
  }

  private final PairPosition pos;
  private final ExprNode expr;

  public PairElementNode(PairPosition pos, ExprNode expr) {
    this.pos = pos;
    this.expr = expr;
  }

  public ExprNode getExpr() {
    return expr;
  }

  public PairPosition getPos() {
    return pos;
  }

  public String getInput() {
    return expr.getInput();
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Check if null pair */
    if (expr instanceof PairLiteralNode) {
      visitor.getSemanticErrorList().addException(ctx, "Cannot access element of null pair");
      setType(new ErrorType());
      return;
    }

    /* Must be identifier or array elem with type pair. */
    if (!(expr instanceof VariableNode || expr instanceof ArrayElementNode) || !(expr.getType() instanceof PairType)) {
      visitor
          .getSemanticErrorList()
          .addException(
              ctx,
              pos.toString()
                  + " has to be called on identifier with type PAIR. Actual type: "
                  + getExpr().getType().toString());
      setType(new ErrorType());
      return;
    }

    /* Get inner type of pair. */
    PairType type = (PairType) getExpr().getType();
    if (pos == PairPosition.FST) {
      setType(type.getType1());
    } else {
      setType(type.getType2());
    }
  }

  @Override
  public void translate(Context context) {
    throw new UnsupportedOperationException("Do not use translate() on"
        + "PairElementNode. Use its helper functions instead.");
  }

  public void translatePairElementNodeRHS(Context context) {
    /* Get register in which expression valued is stored, and set it as the register of this node. */
    expr.translate(context);
    RegisterOperand exprRegister = expr.getRegister();
    Label<Instruction> curr = context.getCurrentLabel();

    /* Retrieve address of element from memory at offset according to position in pair */
    int offset = pos.equals(PairPosition.FST) ? 0 : Context.SIZE_OF_ADDRESS;
    curr.addToBody(LDR(exprRegister,
        new PreIndexedAddressOperand(exprRegister)
            .withExpr(new ImmediateOperand<>(offset).withPrefixSymbol("#"))));

    /* Move expression to R0 for null pointer check */
    curr.addToBody(MOV(RegisterOperand.R0, exprRegister));
    PredefinedFunctions.addCheckNullPointerFunction(context);
    curr.addToBody(BL("p_check_null_pointer"));

    /* Dereference heap address to find actual value. */
    curr.addToBody(LDR(exprRegister,
        new PreIndexedAddressOperand(exprRegister)));

    setRegister(exprRegister);
  }

  /* Helper method for translating pair element node */
  public int translatePairElementNodeLHS(Context context) {
    /* Translate expression within this node first. This should
     * always return an address at a specified register.
     * This address is the address of the pair, not elements of the value in
     * the pair. */
    expr.translate(context);
    RegisterOperand exprRegister = expr.getRegister();
    setRegister(exprRegister);

    boolean isFst = getPos().equals(PairElementNode.PairPosition.FST);
    int offset = isFst ? 0 : 4;

    /* Load memory address of the pair back into the same register */
    context.addToCurrentLabel(
        LDR(exprRegister,
            new PreIndexedAddressOperand(exprRegister)
                .withExpr(new ImmediateOperand<>(offset).withPrefixSymbol("#"))));

    /* Check whether the address of the pair points to a null value */
    context.addToCurrentLabel(MOV(RegisterOperand.R0, exprRegister));
    PredefinedFunctions.addCheckNullPointerFunction(context);
    context.addToCurrentLabel(BL(PredefinedFunctions.CHECK_NULL_POINTER_FUNC));

    return 0;
  }
}
