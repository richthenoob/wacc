package ic.doc.semantics.StatNodes;

import ic.doc.semantics.ExprNodes.ExprNode;
import ic.doc.semantics.Types.CharType;
import ic.doc.semantics.Types.ErrorType;
import ic.doc.semantics.Types.StringType;
import ic.doc.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class AssignmentNode extends StatNode {

    private final ExprNode lhs;
    private final ExprNode rhs;

    public AssignmentNode(ExprNode lhs, ExprNode rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public ExprNode getLhs() {
        return lhs;
    }

    public ExprNode getRhs() {
        return rhs;
    }

    @Override
    public void check(Visitor visitor, ParserRuleContext ctx) {
        if (!lhs.getType().equals(rhs.getType())
            && !(lhs.getType() instanceof ErrorType)
            && !(rhs.getType() instanceof ErrorType)) {
            visitor.addTypeException(ctx, rhs.getInput(), lhs.getType().toString(), rhs.getType().toString());

            if (rhs.getType() instanceof StringType) {
                if (lhs.getType() instanceof CharType && rhs.getInput().length() == 1) {
                    // e.g. char c = "a"
                    visitor.addSuggestion("Did you mean '" + rhs.getInput() + "' instead of \"" + rhs.getInput() + "\"?");
                } else {
                    visitor.addSuggestion("Did you mean " + rhs.getInput() + " instead of \"" + rhs.getInput() + "\"?");
                }
            }

            if (lhs.getType() instanceof StringType) {
                if (rhs.getType() instanceof CharType) {
                    // e.g. String s = 'a'
                    visitor.addSuggestion("Did you mean \"" + rhs.getInput() + "\" instead of '" + rhs.getInput() + "'?");
                } else {
                    // e.g. String greeting = hey
                    visitor.addSuggestion("Did you mean \"" + rhs.getInput() + "\" instead of " + rhs.getInput() + "?");
                }
            }
        }
    }

}
