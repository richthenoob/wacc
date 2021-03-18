package ic.doc.backend.instructions.operands;

import java.util.Objects;

/* Class to hold registers, e.g. r0, r1, ... r12, sp, r, pc */
public class RegisterOperand extends Operand {

  /* Predefined registerOperand objects that are used frequently. */
  public static RegisterOperand R0 = new RegisterOperand(0);
  public static RegisterOperand R1 = new RegisterOperand(1);
  public static RegisterOperand SP = new RegisterOperand(13);
  public static RegisterOperand LR = new RegisterOperand(14);
  public static RegisterOperand PC = new RegisterOperand(15);

  /* Integers 0-15 corresponding to registers 0-15. */
  private final int value;

  public RegisterOperand(int value) {
    this.value = value;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RegisterOperand that = (RegisterOperand) o;
    return value == that.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
