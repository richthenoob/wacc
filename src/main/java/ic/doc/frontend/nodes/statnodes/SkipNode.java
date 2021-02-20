package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Instructions.Instruction;
import ic.doc.frontend.semantics.Visitor;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class SkipNode extends StatNode {

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public List<Instruction> translate() {
    return null;
  }
}
