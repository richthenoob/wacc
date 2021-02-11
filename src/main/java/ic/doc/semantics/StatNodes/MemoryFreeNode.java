package ic.doc.semantics.StatNodes;

import ic.doc.semantics.ExprNodes.ExprNode;
import ic.doc.semantics.Types.PairType;
import ic.doc.semantics.Types.ArrayType;
import ic.doc.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class MemoryFreeNode extends StatNode {

    private final ExprNode exprNode;

    public MemoryFreeNode(ExprNode exprNode) {
        this.exprNode = exprNode;
    }

    public ExprNode getExpr() {
        return exprNode;
    }

    @Override
    public void check(Visitor visitor, ParserRuleContext ctx) {

        // expression must be of type ‘pair(T1, T2)’ or ‘T[]’ (for some T, T1, T2)
        if (!(exprNode.getType() instanceof PairType || exprNode
                .getType() instanceof ArrayType)) {
            visitor.addTypeException(ctx, exprNode.getInput(), "PAIR or ARRAY", exprNode.getType().toString());
        }

    }

}
