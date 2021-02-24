package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Label;
import ic.doc.frontend.semantics.Visitor;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class SequentialCompositionNode extends StatNode {

  private final List<StatNode> statements;

  public SequentialCompositionNode() {
    this.statements = new ArrayList<>();
  }

  public List<StatNode> getStatements() {
    return statements;
  }

  public void add(StatNode stat) {
    statements.add(stat);
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public void translate(
      List<Label<Instruction>> instructionLabels,
      List<Label<Data>> dataLabels) {
    for(StatNode stat : statements){
      stat.translate(instructionLabels, dataLabels);
    }
  }
}
