package ic.doc.semantics.IdentifierObjects;

import ic.doc.semantics.Node;
import ic.doc.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class IdentifierNode extends Node {

  private final String name;

  public IdentifierNode(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
  }

  public String getInput() {
    return getName();
  }
}
