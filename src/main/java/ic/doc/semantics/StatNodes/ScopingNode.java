package ic.doc.semantics.StatNodes;

import ic.doc.semantics.SymbolTable;
import ic.doc.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class ScopingNode extends StatNode {

  private SymbolTable symbolTable;
  private StatNode statNode;

  public ScopingNode(SymbolTable symbolTable, StatNode statNode) {
    this.statNode = statNode;
    this.symbolTable = symbolTable;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

  }
}
