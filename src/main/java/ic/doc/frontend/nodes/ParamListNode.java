package ic.doc.frontend.nodes;

import ic.doc.frontend.types.Type;
import ic.doc.frontend.semantics.Visitor;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ParserRuleContext;

public class ParamListNode extends Node {

  private final int numParas;
  private final List<ParamNode> params;
  private final List<Type> types;

  public ParamListNode(List<ParamNode> params) {
    this.numParas = params.size();
    this.params = params;
    types = params.stream().map(ParamNode::getType)
        .collect(Collectors.toList());
  }

  public int getNumParas() {
    return numParas;
  }

  public List<ParamNode> getParams() {
    return params;
  }

  public List<Type> getType() {
    return types;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

  }

  public String getInput() {
    StringBuilder input = new StringBuilder();
    for (int i = 0; i < numParas; i++) {
      input.append(params.get(i).getInput());
      input.append(", ");
    }
    if (numParas > 0) {
      input.delete(input.length() - 2, input.length() - 1);
    }
    return input.toString();
  }

  public String printTypes() {
    StringBuilder typesString = new StringBuilder();
    for (int i = 0; i < numParas; i++) {
      typesString.append(params.get(i).getType().toString());
      typesString.append(", ");
    }
    if (numParas > 0) {
      typesString.delete(typesString.length() - 2, typesString.length() - 1);
    }
    return typesString.toString();
  }
}
