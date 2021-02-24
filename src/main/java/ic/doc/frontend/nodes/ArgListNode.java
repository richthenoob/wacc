package ic.doc.frontend.nodes;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.Type;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ParserRuleContext;

public class ArgListNode extends Node {

  private final int numArgs;
  private final List<ExprNode> exprs;
  private final List<Type> types;

  public ArgListNode(List<ExprNode> exprs) {
    this.numArgs = exprs.size();
    this.exprs = exprs;
    types = exprs.stream().map(ExprNode::getType).collect(Collectors.toList());
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

  public String printTypes() {
    StringBuilder typesString = new StringBuilder();
    typesString.append("(");
    for (int i = 0; i < types.size(); i++) {
      typesString.append(types.get(i).toString());
      typesString.append(", ");
    }
    if (types.size() > 0) {
      typesString.delete(typesString.length() - 2, typesString.length());
    }
    typesString.append(")");
    return typesString.toString();
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
    /* No checks needed. */
  }

  @Override
  public void translate(Context context) {

  }


}
