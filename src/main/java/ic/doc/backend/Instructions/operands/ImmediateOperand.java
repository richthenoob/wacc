package ic.doc.backend.Instructions.operands;

/* e.g. #5, #-16 */
public class ImmediateOperand extends Operand {

  private final int value;

  public ImmediateOperand(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "#" + value;
  }
}
