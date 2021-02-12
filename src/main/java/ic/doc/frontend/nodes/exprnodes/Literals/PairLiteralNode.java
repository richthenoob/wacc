package ic.doc.frontend.nodes.exprnodes.Literals;

import ic.doc.frontend.types.AnyType;
import ic.doc.frontend.types.PairType;
import ic.doc.frontend.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

/* Only pair literal is 'null'. Can match the type of any pair. */
public class PairLiteralNode extends LiteralNode {

  public PairLiteralNode() {
    setType(new PairType(new AnyType(), new AnyType()));
  }

  @Override
  public void check(Visitor symbolTable, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public String getInput() {
    return "null";
  }
}
