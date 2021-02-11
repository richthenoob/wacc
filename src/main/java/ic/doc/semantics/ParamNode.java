package ic.doc.semantics;

import ic.doc.semantics.Types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

public class ParamNode extends Node{

    private Type type;
    private String input;

    public Type getType() {
        return type;
    }

    @Override
    public void check(Visitor visitor, ParserRuleContext ctx) {

    }

    public ParamNode(Type type, String input) {
        this.type = type;
        this.input = input;
    }

    public String getInput() {
        return input;
    }
}
