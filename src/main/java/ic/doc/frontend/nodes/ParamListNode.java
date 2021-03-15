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

  public ParamListNode(List<ParamNode> params) {
    this.numParas = params.size();
    this.params = params;
  }

  public void addParam(ParamNode node) {
    params.add(node);
  }

  public int getNumParas() {
    return numParas;
  }

  public List<ParamNode> getParams() {
    return params;
  }

  public List<Type> getType() {
    return params.stream().map(ParamNode::getType)
        .collect(Collectors.toList());
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public void translate(Context context) {
    translateHelper(context, false);
  }

  /* Helper method to translate params. Because params can refer to both
   * function arguments and fields within a class, we need to differentiate
   * between these two cases. */
  public void translateHelper(Context context, boolean isClassFields) {
    SymbolTable funcSymbolTable = context.getCurrentSymbolTable();
    /* Look up each parameter in function symbol table。 */
    for (int i = params.size() - 1; i >= 0; i--) {
      ParamNode param = params.get(i);
      VariableIdentifier id = (VariableIdentifier)
          funcSymbolTable.getIdentifier(i);

      /* Increment offsets in symbol table accordingly.*/
      int sizeOfVarOnStack = param.getType().getVarSize();
      funcSymbolTable.incrementOffset(sizeOfVarOnStack);
      funcSymbolTable.incrementFunctionParametersSize(sizeOfVarOnStack);
      id.setActivated();

      /* Mark this variable as a class variable if necessary. */
      if (isClassFields) {
        id.setClassVariable();
      }
    }
  }

  public String getInput() {
    return params.stream()
        .map(ParamNode::getInput)
        .collect(Collectors.joining(", "));
  }
}
