package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.backend.PredefinedFunctions;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.nodes.exprnodes.Literals.PairLiteralNode;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.PairType;

import java.lang.reflect.Array;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

import static ic.doc.backend.Instructions.Branch.BL;
import static ic.doc.backend.Instructions.Move.MOV;
import static ic.doc.backend.Instructions.SingleDataTransfer.LDR;

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

    /* Must be identifier with type pair. */
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
    /* Get register in which expression valued is stored, and set it as the register of this node. */
    expr.translate(context);
    RegisterOperand reg = expr.getRegister();
    setRegister(reg);
    Label<Instruction> curr = context.getCurrentLabel();

    /* Move expression to R0 for null pointer check */
    curr.addToBody(MOV(RegisterOperand.R0, reg));
    PredefinedFunctions.addCheckNullPointerFunction(context);
    curr.addToBody(BL("p_check_null_pointer"));

    /* Retrieve value of element from memory at offset according to position in pair */
    int offset = pos.equals(PairPosition.FST) ? 0 : 4;
    curr.addToBody(LDR(reg,
        new PreIndexedAddressOperand(reg)
        .withExpr(new ImmediateOperand<>(offset).withPrefixSymbol("#"))));
  }
}
