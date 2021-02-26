package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
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
  public void translate(Context context) {
    for (StatNode node : statements) {
      node.translate(context);
    }
  }
}
