package ic.doc.backend.Instructions;

public class Stack extends Instruction{
  private boolean pushFlag;
  private int value;

  @Override
  public String toAssembly() {
    String action = pushFlag ? "PUSH " : "POP ";
    return (action + value);
  }
}
