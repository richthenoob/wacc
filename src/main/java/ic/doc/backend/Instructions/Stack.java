package ic.doc.backend.Instructions;

public class Stack extends Instruction{
  private final boolean pushFlag;
  private final Operand value;

  public Stack(boolean pushFlag, Operand value) {
    this.pushFlag = pushFlag;
    this.value = value;
  }

  @Override
  public String toAssembly() {
    String action = pushFlag ? "PUSH " : "POP ";
    return (action + value);
  }
}
