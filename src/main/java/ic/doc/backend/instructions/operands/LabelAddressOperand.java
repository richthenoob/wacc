package ic.doc.backend.instructions.operands;

import java.util.Objects;

/* e.g. =msg_0 */
public class LabelAddressOperand extends AddressOperand {
  private final String label;

  public LabelAddressOperand(String label) {
    this.label = label;
  }

  @Override
  public int hashCode() {
    return Objects.hash(label);
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof LabelAddressOperand){
      return label.equals(((LabelAddressOperand) obj).label) ;
    }
    return false;
  }



  @Override
  public String toString() {
    return "=" + label;
  }
}
