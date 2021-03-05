package ic.doc.frontend.nodes;

import ic.doc.backend.Context;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.Type;
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
    /* No checks needed. */
  }

  @Override
  public void translate(Context context) {
    SymbolTable funcSymbolTable = context.getCurrentSymbolTable();
      /* Look up each parameter in function symbol table。 */
    for (int i = params.size() - 1; i >= 0; i--) {
      ParamNode param = params.get(i);
      VariableIdentifier id = (VariableIdentifier) funcSymbolTable
          .getIdentifier(i);

      /* Increment offsets in symbol table accordingly.*/
      int sizeOfVarOnStack = param.getType().getVarSize();
      funcSymbolTable.incrementOffset(sizeOfVarOnStack);
      funcSymbolTable.incrementFunctionParametersSize(sizeOfVarOnStack);
      id.setActivated();
    }
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
}
