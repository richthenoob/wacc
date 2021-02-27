package ic.doc.backend.Instructions.operands;

/* Class to hold registers, e.g. r0, r1, ... r12, sp, r, pc */
public class RegisterOperand extends Operand {

  /* Predefined registerOperand objects that are used frequently. */
  public static RegisterOperand R0 = new RegisterOperand(0);
  public static RegisterOperand R1 = new RegisterOperand(1);
  public static RegisterOperand SP = new RegisterOperand(13);
  public static RegisterOperand LR = new RegisterOperand(14);
  public static RegisterOperand PC = new RegisterOperand(15);

  private final int value; // register value

  public RegisterOperand(int value) {
    this.value = value;
  }

  public static RegisterOperand SP(){
    return new RegisterOperand(13);
  }

  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    switch (value) {
      case 13:
        return "sp";
      case 14:
        return "lr";
      case 15:
        return "pc";
      default:
        return "r" + value;
    }
  }
}
