package ic.doc.backend.Instructions.operands;

/* e.g. =5, =-16 */
public class ImmediateOperand extends Operand {

  private final int value;
  private boolean offset = false;

  public ImmediateOperand(int value) {
    this.value = value;
  }

  public ImmediateOperand(boolean offset, int value) {
    this.value = value;
    this.offset = true;
  }

  @Override
  public String toString() {
    if (offset) {
      return "#" +value;
    }
    return "=" +value;
  }
}
