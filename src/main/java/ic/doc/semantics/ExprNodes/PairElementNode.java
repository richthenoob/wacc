package ic.doc.semantics.ExprNodes;

import ic.doc.semantics.Types.ErrorType;
import ic.doc.semantics.Types.PairType;
import ic.doc.semantics.Visitor;
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

    /* Must be identifier with type pair. */
    if (!(expr instanceof VariableNode) || !(expr.getType() instanceof PairType)) {
      if (pos == PairPosition.FST) {
        visitor.getSemanticErrorList().addException(
            ctx,
            pos.toString()
                + " has to be called on identifier with type PAIR(TYPE,_). Actual type: "
                + getExpr().getType().toString());
        setType(new ErrorType());
        return;
      } else {
        visitor.getSemanticErrorList().addException(
            ctx,
            pos.toString()
                + " has to be called on identifier with type PAIR(T,_). Actual type: "
                + getExpr().getType().toString());
        setType(new ErrorType());
        return;
      }
    }

    /* Get inner type of pair. */
    PairType type = (PairType) getExpr().getType();
    if (pos == PairPosition.FST) {
      setType(type.getType1());
    } else {
      setType(type.getType2());
    }
  }
}
