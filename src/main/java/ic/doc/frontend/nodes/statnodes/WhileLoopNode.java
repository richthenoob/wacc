package ic.doc.frontend.nodes.statnodes;

import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.types.BoolType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class WhileLoopNode extends StatNode {

  private ExprNode cond;
  private StatNode body;

  public WhileLoopNode(ExprNode cond, StatNode body) {
    this.cond = cond;
    this.body = body;
  }

  public ExprNode getCond() {
    return cond;
  }

  public StatNode getBody() {
    return body;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Expr must be a bool.
     * There is no need to print the type error message
     * if the condition was not present in the symbol table
     * - i.e. if the type was Error. */
    if (!(cond.getType() instanceof BoolType
        || cond.getType() instanceof ErrorType)) {
      visitor.getSemanticErrorList()
          .addTypeException(ctx, cond.getInput(), "BOOL", cond.getType().toString(), "");
    }
  }

}
