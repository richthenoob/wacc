package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.Literals.PairLiteralNode;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.PairType;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

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
    if (!(expr instanceof VariableNode) || !(expr.getType() instanceof PairType)) {
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
  public void translate(
      List<Label<Instruction>> instructionLabels,
      List<Label<Data>> dataLabels) {
  }
}
