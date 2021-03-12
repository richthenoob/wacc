package ic.doc.backend.instructions;

import ic.doc.backend.instructions.operands.Operand;

public class SingleDataTransfer extends Instruction {
  private final boolean loadFlag;
  private String cond; /* For conditional returns */
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

  /* For optimization */
  public boolean optimizable(SingleDataTransfer prev) {
    Operand fst = prev.dst;
    Operand snd = prev.expr;
    boolean b = fst.equals(dst);
    boolean c = snd.equals(expr);
    return (!prev.loadFlag && loadFlag && b && c);
  }

  @Override
  public String toAssembly() {
    String action = loadFlag ? "LDR" : "STR";
    return (action + cond + " " + dst.toString() + ", " + expr.toString());
  }
}
