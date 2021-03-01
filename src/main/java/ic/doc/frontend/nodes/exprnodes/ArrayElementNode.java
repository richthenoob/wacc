package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ArrayType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.IntType;
import ic.doc.frontend.types.Type;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ParserRuleContext;

/* Parser definition: arrayElem: IDENT (OPEN_BRACKETS expr CLOSE_BRACKETS)+
 * IDENT MUST be of type T[], expr MUST be of type INT
 * Valid examples: arr[1] (arr is of type INT[]),
 *                 arr[1][2] (assuming arr is of type INT[][]) */
public class ArrayElementNode extends ExprNode {

  private final List<ExprNode> exprNodes;
  private final VariableNode identNode;
  private boolean isErrored = false;

  public List<ExprNode> getExprNodes() {
    return exprNodes;
  }

  public ArrayElementNode(List<ExprNode> exprNodes, VariableNode identNode) {
    this.exprNodes = exprNodes;
    this.identNode = identNode;
  }

  public int getDimensions() {
    return exprNodes.size();
  }

  public ExprNode getFirstIndex



          (){
    return exprNodes.get(0);
  }

  public VariableNode getIdentNode() {
    return identNode;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

    /* Should never have empty exprNode list,
     * if not it should be a syntactic error. */
    if (exprNodes.isEmpty()) {
      throw new IllegalStateException("");
    }

    /* Identifier checks. */
    SymbolKey key = new SymbolKey(identNode.getName(), false);
    Identifier entry = visitor.getCurrentSymbolTable().lookupAll(key);
    if (entry == null) {
      isErrored = true;
      /* Identifier should have already been defined. */
      visitor
          .getSemanticErrorList()
          .addScopeException(ctx, false, "Variable", identNode.getInput());
    } else if (!(entry.getType() instanceof ArrayType)) {
      isErrored = true;
      /* Identifier should be of type "Array". */
      visitor
          .getSemanticErrorList()
          .addTypeException(ctx, identNode.getInput(), "T[]", identNode.getType().toString(), "");
    }

    /* Go through all exprNodes to check index is of correct type, INT. */
    List<ExprNode> mismatchedTypeNodes =
        exprNodes.stream()
            .filter(x -> !(x.getType() instanceof IntType))
            .collect(Collectors.toList());

    for (ExprNode mismatchedTypeNode : mismatchedTypeNodes) {
      isErrored = true;
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx,
              mismatchedTypeNode.getInput(),
              "INT",
              mismatchedTypeNode.getType().toString(),
              "");
    }

    if (isErrored) {
      setType(new ErrorType());
    } else {
      Type type = identNode.getType();

      /* Iterate through number of expressions to find underlying
       * expression type. */
      for (int i = 0; i < exprNodes.size(); i++) {
        if (type instanceof ArrayType) {
          type = ((ArrayType) type).getInternalType();
        } else {
          visitor
              .getSemanticErrorList()
              .addTypeException(ctx, getInput(), "T[]", type.toString(), "");
        }
      }
      setType(type);
    }
  }

  @Override
  public void translate(Context context) {


  }

  @Override
  public String getInput() {
    return identNode.getInput();
  }
}
