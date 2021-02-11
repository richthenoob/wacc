package ic.doc.semantics.StatNodes;

import ic.doc.SemanticException;
import ic.doc.semantics.ExprNodes.ExprNode;
import ic.doc.semantics.SymbolTable;
import ic.doc.semantics.Types.BoolType;
import ic.doc.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class ConditionalBranchNode extends StatNode {

  private final ExprNode cond;
  private final StatNode trueBody;
  private final StatNode falseBody;
  private final SymbolTable trueBodySymbolTable;
  private final SymbolTable falseBodySymbolTable;

  public ConditionalBranchNode(ExprNode cond, StatNode trueBody,
      StatNode falseBody, SymbolTable trueBodySymbolTable, SymbolTable falseBodySymbolTable) {
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
    //Expr must be a bool
    if (!(cond.getType() instanceof BoolType)) {
      visitor.getSemanticErrorList()
          .addTypeException(ctx, cond.getInput(), "BOOL", cond.getType().toString());
    }
  }
}
