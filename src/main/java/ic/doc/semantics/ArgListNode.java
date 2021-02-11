package ic.doc.semantics;

import ic.doc.semantics.ExprNodes.ExprNode;
import ic.doc.semantics.Types.Type;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

public class ArgListNode extends Node {

  private final int numArgs;
  private final List<ExprNode> exprs;
  private final List<Type> types;

  public ArgListNode(List<ExprNode> exprs) {
    this.numArgs = exprs.size();
    this.exprs = exprs;
    types = exprs.stream().map(ExprNode::getType)
            .collect(Collectors.toList());
  }

  public int getNumParas() {
    return numArgs;
  }

  public List<ExprNode> getParams() {
    return exprs;
  }

  public List<Type> getType() {
    return types;
  }

  public String getInput() {
    StringBuilder input = new StringBuilder();
    for (int i = 0; i < numArgs; i++) {
      input.append(exprs.get(i).getInput());
      input.append(", ");
    }
    if (numArgs > 0) {
      input.delete(input.length() - 2, input.length());
    }
    return input.toString();
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

  }
}
