package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ArrayType;
import ic.doc.frontend.types.PairType;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class MemoryFreeNode extends StatNode {

  private final ExprNode exprNode;

  public MemoryFreeNode(ExprNode exprNode) {
    this.exprNode = exprNode;
  }

  public ExprNode getExpr() {
    return exprNode;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Expression must be of type 'pair(T1, T2)' or 'T[]' (for some T, T1, T2) */
    if (!(exprNode.getType() instanceof PairType || exprNode.getType() instanceof ArrayType)) {
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx,
              exprNode.getInput(),
              "PAIR or ARRAY",
              exprNode.getType().toString(),
              "",
              " 'free' statement");
    }
  }

  @Override
  public void translate(
      List<Label<Instruction>> instructionLabels,
      List<Label<Data>> dataLabels) {
  }
}
