package ic.doc.backend.Instructions;

import ic.doc.frontend.types.*;

public class SingleDataTransfer extends Instruction {
    private boolean loadFlag;
    private String cond = ""; //for conditional returns
    private Type type = new AnyType();
    private Operand dst;
    private Operand expr;

    public SingleDataTransfer(boolean loadFlag, Operand dst, Operand expr) {
        this.loadFlag = loadFlag;
        this.dst = dst;
        this.expr = expr;
    }

    @Override
    public String toAssembly() {
        String action = loadFlag ? "LDR " : "STR ";
        String typeStr = loadFlag ? "" : type.toString();
        return (action + cond + typeStr + dst.toString() + ", " + expr.toString());
    }
}
