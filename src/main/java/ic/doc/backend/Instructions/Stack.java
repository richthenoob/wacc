package ic.doc.backend.Instructions;

public class Stack extends Instruction{
  private final boolean pushFlag;
  private final Operand value;

  private Stack(boolean pushFlag, Operand value) {
    this.pushFlag = pushFlag;
    this.value = value;
  }

  public static Stack PUSH(Operand value){
    return new Stack(true, value);
  }

  public static Stack POP(Operand value){
    return new Stack(false, value);
  }

  @Override
  public String toAssembly() {
    String action = pushFlag ? "PUSH " : "POP ";
    return (action + value);
  }
}
