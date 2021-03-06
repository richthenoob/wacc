package ic.doc.backend.instructions;

import ic.doc.backend.instructions.operands.RegisterOperand;

public class Stack extends Instruction {
  private final boolean pushFlag;
  private final RegisterOperand value;

  private Stack(boolean pushFlag, RegisterOperand value) {
    this.pushFlag = pushFlag;
    this.value = value;
  }

  public static Stack PUSH(RegisterOperand value) {
    return new Stack(true, value);
  }

  public static Stack POP(RegisterOperand value) {
    return new Stack(false, value);
  }

  @Override
  public String toAssembly() {
    String action = pushFlag ? "PUSH " : "POP ";
    return action + "{" + value + "}";
  }
}
