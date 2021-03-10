package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.frontend.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class ClassVariableNode extends VariableNode {
  // TODO: fill in class

  public ClassVariableNode(String identifier) {
    super(identifier);
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

  }

  @Override
  public void translate(Context context) {

  }

  @Override
  public String getInput() {
    return null;
  }
}
