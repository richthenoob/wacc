package ic.doc.backend.Instructions.operands;

/* e.g. =msg_0 */
public class LabelAddressOperand extends AddressOperand {
  private final String label;

  public LabelAddressOperand(String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return "=" + label;
  }
}
