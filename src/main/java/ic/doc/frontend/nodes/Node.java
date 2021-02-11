package ic.doc.frontend.nodes;

import ic.doc.frontend.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class Node {

  public abstract void check(Visitor visitor, ParserRuleContext ctx);
}
