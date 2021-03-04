package ic.doc.backend.Instructions;

import ic.doc.backend.Instructions.operands.Operand;
import ic.doc.frontend.types.*;

public class SingleDataTransfer extends Instruction {
  private final boolean loadFlag;
  private String cond; // for conditional returns
  private final Operand dst;
  private final Operand expr;

  private SingleDataTransfer(String cond, boolean loadFlag, Operand dst, Operand expr) {
    this.cond = cond;
    this.loadFlag = loadFlag;
    this.dst = dst;
    this.expr = expr;
  }

  public static SingleDataTransfer LDR(Operand dst, Operand expr) {
    return new SingleDataTransfer("", true, dst, expr);
  }

  public static SingleDataTransfer STR(Operand dst, Operand expr) {
    return new SingleDataTransfer("", false, dst, expr);
  }

  public SingleDataTransfer withCond(String cond) {
    this.cond = cond;
    return this;
  }

  @Override
  public String toAssembly() {
    String action = loadFlag ? "LDR" : "STR";
    return (action + cond + " " + dst.toString() + ", " + expr.toString());
  }
}
