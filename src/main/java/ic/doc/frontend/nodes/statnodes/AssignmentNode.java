package ic.doc.frontend.nodes.statnodes;

import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.nodes.exprnodes.VariableNode;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.types.CharType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.StringType;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

public class AssignmentNode extends StatNode {

  private final ExprNode lhs;
  private final ExprNode rhs;
  private final boolean isDeclaration;

  public AssignmentNode(ExprNode lhs, ExprNode rhs, boolean isDeclaration) {
    this.lhs = lhs;
    this.rhs = rhs;
    this.isDeclaration = isDeclaration;
  }

  public ExprNode getLhs() {
    return lhs;
  }

  public ExprNode getRhs() {
    return rhs;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    if(isDeclaration){
      VariableNode lhsVar = (VariableNode) lhs;
      String name = lhsVar.getName();
      SymbolKey key = new SymbolKey(name, false);
      if (visitor.getCurrentSymbolTable().lookup(key) != null) {
        visitor
                .getSemanticErrorList()
                .addScopeException(ctx, true, "Variable", name);
      } else {
        visitor.getCurrentSymbolTable().add(key, new VariableIdentifier(lhs.getType()));
      }
    }

    if (!(Type.checkTypeCompatibility(lhs.getType(), rhs.getType()))
        && !(lhs.getType() instanceof ErrorType)
        && !(rhs.getType() instanceof ErrorType)) {
      visitor.getSemanticErrorList()
          .addTypeException(ctx, rhs.getInput(),
              lhs.getType().toString(), rhs.getType().toString());

      if (rhs.getType() instanceof StringType) {
        if (lhs.getType() instanceof CharType && rhs.getInput().length() == 1) {
          // e.g. char c = "a"
          visitor.getSemanticErrorList()
              .addSuggestion(ctx,"Did you mean '"
                  + rhs.getInput() + "' instead of \"" + rhs.getInput() + "\"?");
        } else {
          visitor.getSemanticErrorList()
              .addSuggestion(ctx,"Did you mean "
                  + rhs.getInput() + " instead of \"" + rhs.getInput() + "\"?");
        }
      }

      if (lhs.getType() instanceof StringType) {
        if (rhs.getType() instanceof CharType) {
          // e.g. String s = 'a'
          visitor.getSemanticErrorList()
              .addSuggestion(ctx,"Did you mean \""
                  + rhs.getInput() + "\" instead of '" + rhs.getInput() + "'?");
        } else {
          // e.g. String greeting = hey
          visitor.getSemanticErrorList()
              .addSuggestion(ctx,"Did you mean \""
                  + rhs.getInput() + "\" instead of " + rhs.getInput() + "?");
        }
      }
    }
  }

}
