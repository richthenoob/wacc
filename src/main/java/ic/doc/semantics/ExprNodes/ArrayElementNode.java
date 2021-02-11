package ic.doc.semantics.ExprNodes;

import ic.doc.semantics.IdentifierObjects.Identifier;
import ic.doc.semantics.SymbolKey;
import ic.doc.semantics.Types.ArrayType;
import ic.doc.semantics.Types.ErrorType;
import ic.doc.semantics.Types.IntType;
import ic.doc.semantics.Types.Type;
import ic.doc.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/* Parser definition: arrayElem: IDENT (OPEN_BRACKETS expr CLOSE_BRACKETS)+
 * IDENT MUST be of type T[], expr MUST be of type INT
 * Valid examples: arr[1] (arr is of type INT[]),
 *                 arr[1][2] (assuming arr is of type INT[][]) */
public class ArrayElementNode extends ExprNode {

  private final List<ExprNode> exprNodes;
  private final VariableNode identNode;
  private boolean isErrored = false;

  public ArrayElementNode(List<ExprNode> exprNodes,
      VariableNode identNode) {
    this.exprNodes = exprNodes;
    this.identNode = identNode;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

    List<String> errors = new ArrayList<>();

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
      visitor.getSemanticErrorList().addException(ctx, "Variable " + identNode.getInput()
          + " is not defined in this scope.");
    } else if (!(entry.getType() instanceof ArrayType)) {
      isErrored = true;
      /* Identifier should be of type "Array". */
      visitor.getSemanticErrorList().addException(ctx,
          "Incompatible type at " + identNode.getInput()
              + ". Expected type: T[]. Actual type: "
              + identNode.getType().toString());
    }

    /* Go through all exprNodes to check index is of correct type, INT. */
    List<ExprNode> mismatchedTypeNodes = exprNodes.stream()
        .filter(x -> !(x.getType() instanceof IntType))
        .collect(Collectors.toList());

    for (ExprNode mismatchedTypeNode : mismatchedTypeNodes) {
      isErrored = true;
      visitor.getSemanticErrorList().addTypeException(ctx,
          mismatchedTypeNode.getInput(), "INT", mismatchedTypeNode.getType().toString());
    }

    /* Check index is not longer than length. */
    // Is this check possible at compile time? or only at runtime?

    /* Throw every error we found at once. */
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
          visitor.getSemanticErrorList()
              .addTypeException(ctx, getInput(), "T[]", type.toString());
        }
      }
      setType(type);
    }
  }

  @Override
  public String getInput() {
    return identNode.getInput();
  }
}
