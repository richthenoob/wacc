package ic.doc.backend.Instructions;

import ic.doc.backend.Instructions.operands.Operand;

public class Move extends Instruction {

  private final Operand dst;
  private final Operand src;
  private final Condition condition;

  public Move(Operand dst, Operand src, Condition condition) {
    this.dst = dst;
    this.src = src;
    this.condition = condition;
  }

  public static Move MOVEQ(Operand dst, Operand src) {
    return new Move(dst, src, Condition.BEQ);
  }

  public static Move MOVNE(Operand dst, Operand src) {
    return new Move(dst, src, Condition.BNE);
  }

  public static Move MOVGE(Operand dst, Operand src) {
    return new Move(dst, src, Condition.BGE);
  }

  public static Move MOVLT(Operand dst, Operand src) {
    return new Move(dst, src, Condition.BLT);
  }

  public static Move MOVGT(Operand dst, Operand src) {
    return new Move(dst, src, Condition.BGT);
  }

  public static Move MOVLE(Operand dst, Operand src) {
    return new Move(dst, src, Condition.BLE);
  }

  public static Move MOV(Operand dst, Operand src) {
    /* Doesn't matter what condition it is, as long as its not the others. "Default" case */
    return new Move(dst, src, Condition.B);
  }

  @Override
  public String toAssembly() {
    switch (condition) {
      case BEQ:
        return "MOVEQ " + dst.toString() + ", " + src.toString();
      case BNE:
        return "MOVNE " + dst.toString() + ", " + src.toString();
      case BGE:
        return "MOVGE " + dst.toString() + ", " + src.toString();
      case BLT:
        return "MOVLT " + dst.toString() + ", " + src.toString();
      case BGT:
        return "MOVGT " + dst.toString() + ", " + src.toString();
      case BLE:
        return "MOVLE " + dst.toString() + ", " + src.toString();
      default:
        return "MOV " + dst.toString() + ", " + src.toString();
    }
  }
}
