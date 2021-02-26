package ic.doc.backend.Instructions.operands;

/* e.g. =5, =-16 */
public class ImmediateOperand extends Operand {

  private final int value;
  private char prefix = '=';

  public ImmediateOperand(int value) {
    this.value = value;
  }

  public ImmediateOperand(char prefix, int value) {
    this.value = value;
    this.prefix = prefix;
  }
  //TODO: refactor?

  @Override
  public String toString() {
    return Character.toString(prefix) + value;
  }
}
