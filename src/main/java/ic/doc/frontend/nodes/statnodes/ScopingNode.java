package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Label;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class ScopingNode extends StatNode {

  private final SymbolTable symbolTable;
  private final StatNode statNode;

  public ScopingNode(SymbolTable symbolTable, StatNode statNode) {
    this.statNode = statNode;
    this.symbolTable = symbolTable;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public void translate(Context context) {
    /* Set scope to corresponding symbol table before translating statements within scope. */
    context.setScope(symbolTable);
    statNode.translate(context);

    /* Restore scope. */
    context.restoreScope();
  }
}
