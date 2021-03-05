package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.frontend.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class SkipNode extends StatNode {

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public void translate(Context context) {
    /* No translation needed. */
  }
}
