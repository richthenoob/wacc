package ic.doc.backend.Instructions;

import ic.doc.backend.Instructions.operands.Operand;
import ic.doc.frontend.types.*;

public class SingleDataTransfer extends Instruction {
    private boolean loadFlag;
    private String cond = ""; //for conditional returns
    private Type type = new AnyType();
    private Operand dst;
    private Operand expr;

    private SingleDataTransfer(String cond, boolean loadFlag, Operand dst, Operand expr) {
        this.cond = cond;
        this.loadFlag = loadFlag;
        this.dst = dst;
        this.expr = expr;
    }

    public static SingleDataTransfer LDR(Operand dst, Operand expr){
        return new SingleDataTransfer("", true, dst, expr);
    }

    public static SingleDataTransfer LDR(String cond, Operand dst, Operand expr){
        return new SingleDataTransfer(cond, true, dst, expr);
    }

    public static SingleDataTransfer STR(Operand dst, Operand expr){
        return new SingleDataTransfer("", false, dst, expr);
    }

    public static SingleDataTransfer STR(String cond, Operand dst, Operand expr){
        return new SingleDataTransfer(cond, true, dst, expr);
    }

    @Override
    public String toAssembly() {
        String action = loadFlag ? "LDR " : "STR ";
        String typeStr = loadFlag ? "" : type.toString();
        return (action + cond + typeStr + dst.toString() + ", " + expr.toString());
    }
}
