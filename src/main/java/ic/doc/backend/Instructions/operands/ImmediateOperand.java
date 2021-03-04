package ic.doc.backend.Instructions.operands;

/* e.g. =5, =-16 */
public class ImmediateOperand<T> extends Operand {

  private final T value;
  private String prefixSymbol;

  public ImmediateOperand(T value) {
    this.value = value;
  }

  public ImmediateOperand withPrefixSymbol(String prefixSymbol) {
    this.prefixSymbol = prefixSymbol;
    return this;
  }

  @Override
  public String toString() {
    if (value instanceof Character) {
      return prefixSymbol + "'" + value + "'";
    }
    return (prefixSymbol + value);
  }
}
