package ic.doc.frontend.nodes;


import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.nodes.statnodes.StatNode;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class ProgNode extends Node {

    private List<FunctionNode> functions;
    private StatNode stat;


    public ProgNode(List<FunctionNode> functions, StatNode stat) {
        this.functions = functions;
        this.stat = stat;
    }

    @Override
    public void check(Visitor visitor, ParserRuleContext ctx) {
        /* No checks needed. */
    }
}
