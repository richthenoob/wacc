package ic.doc.backend.Instructions.operands;

/* e.g. =5, =-16 */
public class ImmediateOperand<T> extends Operand {

  private final T value;
  private boolean hashSymbol = false;

  public ImmediateOperand(T value) {
    this.value = value;
  }

  public ImmediateOperand(boolean hashSymbol, T value) {
    this.value = value;
    this.hashSymbol = true;
  }

  @Override
  public String toString() {
    if (value instanceof Character) {
      if (hashSymbol) {
        return "#" + "'" + value + "'";
      }
      return "=" + "'" + value + "'";
    }
    if (hashSymbol) {
      return "#" + value;
    }
    return "=" + value;
  }
}
