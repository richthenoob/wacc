package ic.doc.backend.Instructions;

public class Stack extends Instruction{
  private final boolean pushFlag;
  private final int value;

  public Stack(boolean pushFlag, int value) {
    this.pushFlag = pushFlag;
    this.value = value;
  }

  @Override
  public String toAssembly() {
    String action = pushFlag ? "PUSH " : "POP ";
    return (action + value);
  }
}
