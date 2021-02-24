package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.BoolType;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class ConditionalBranchNode extends StatNode {

  private final ExprNode cond;
  private final StatNode trueBody;
  private final StatNode falseBody;
  private final SymbolTable trueBodySymbolTable;
  private final SymbolTable falseBodySymbolTable;

  public ConditionalBranchNode(
      ExprNode cond,
      StatNode trueBody,
      StatNode falseBody,
      SymbolTable trueBodySymbolTable,
      SymbolTable falseBodySymbolTable) {
    this.cond = cond;
    this.trueBody = trueBody;
    this.falseBody = falseBody;
    this.trueBodySymbolTable = trueBodySymbolTable;
    this.falseBodySymbolTable = falseBodySymbolTable;
  }

  public ExprNode getCond() {
    return cond;
  }

  public StatNode getTrueBody() {
    return trueBody;
  }

  public StatNode getFalseBody() {
    return falseBody;
  }

  public SymbolTable getTrueBodySymbolTable() {
    return trueBodySymbolTable;
  }

  public SymbolTable getFalseBodySymbolTable() {
    return falseBodySymbolTable;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Expr must be a bool */
    if (!(cond.getType() instanceof BoolType)) {
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx, cond.getInput(), "BOOL", cond.getType().toString(), "", "'if' condition");
    }
  }

  @Override
  public void translate(Context context) {}
}
