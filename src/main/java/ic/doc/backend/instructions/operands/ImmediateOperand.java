package ic.doc.backend.instructions.operands;

import java.util.Objects;

/* e.g. =5, =-16 */
public class ImmediateOperand<T> extends Operand {

  private final T value;
  private String prefixSymbol;

  public ImmediateOperand(T value) {
    this.value = value;
  }

  public ImmediateOperand<T> withPrefixSymbol(String prefixSymbol) {
    this.prefixSymbol = prefixSymbol;
    return this;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value,prefixSymbol);
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof ImmediateOperand){
      return value.equals(((ImmediateOperand<?>) obj).value) && prefixSymbol.equals(((ImmediateOperand<?>) obj).prefixSymbol);
    }
    return false;
  }

  @Override
  public String toString() {
    if (value instanceof Character) {
      return prefixSymbol + "'" + value + "'";
    }
    return (prefixSymbol + value);
  }
}
