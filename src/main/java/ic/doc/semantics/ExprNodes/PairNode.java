package ic.doc.semantics.ExprNodes;

import ic.doc.semantics.Types.PairType;
import ic.doc.semantics.Visitor;
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

  public ExprNode getSnd(){
    return snd;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

  }

  @Override
  public String getInput() {
    return "(" + fst.getInput() + ", " + snd.getInput() + ")";
  }
}
