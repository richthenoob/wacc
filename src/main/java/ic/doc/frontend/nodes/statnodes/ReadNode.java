package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.CharType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.IntType;
import ic.doc.frontend.types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

public class ReadNode extends StatNode {

  /* A readNode may contain an expression, or it may just be an identifier i.e. variable */
  private final ExprNode expr;

  public ReadNode(ExprNode expr) {
    this.expr = expr;
  }

  public ExprNode getExpr() {
    return expr;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    Type type = expr.getType();
    if (type instanceof ErrorType) {
      /* Other errors should have already been caught, no need to print again */
      return;
    }
    if (!(type instanceof CharType || type instanceof IntType)) {
      /* Type of expression should be Char or Int */
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx, expr.getInput(), "CHAR or INT", type.toString(), "", "'read' statement");
    }
  }

  @Override
  public void translate(
      Label<Instruction> instructionLabels,
      Label<Data> dataLabels) {
  }
}
