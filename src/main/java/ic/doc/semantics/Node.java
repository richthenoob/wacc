package ic.doc.semantics;

import org.antlr.v4.runtime.ParserRuleContext;

public abstract class Node {

  public abstract void check(Visitor visitor, ParserRuleContext ctx);
}
