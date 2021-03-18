package ic.doc.backend.instructions.operands;

import java.util.Objects;

/* Adapted from the ARM specification.
 * [Rn], <#expression>]        offset of <expression> bytes
 * [Rn],{+/-}Rm {,<shift>}     offset of +/- contents of index register,
 *                             shifted by <shift> */
public class PostIndexedAddressOperand extends AddressOperand {

  private RegisterOperand rn;
  private RegisterOperand rm;
  private boolean isNegativeRm;
  private ShiftTypes shift;
  private ImmediateOperand expr; // Doubles as value shifted by

  public PostIndexedAddressOperand() {
    isNegativeRm = false;
    shift = ShiftTypes.NONE;
  }

  public PostIndexedAddressOperand withRN(RegisterOperand rn) {
    this.rn = rn;
    return this;
  }

  public PostIndexedAddressOperand withRM(RegisterOperand rm) {
    this.rm = rm;
    return this;
  }

  public PostIndexedAddressOperand withNegative() {
    this.isNegativeRm = true;
    return this;
  }

  public PostIndexedAddressOperand withShift(ShiftTypes shift) {
    this.shift = shift;
    return this;
  }

  public PostIndexedAddressOperand withExpr(ImmediateOperand expr) {
    this.expr = expr;
    return this;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rn, rm, isNegativeRm, shift, expr);
  }

  @Override
  public boolean equals(Object obj) {
    boolean compareRm;
    if (obj instanceof PostIndexedAddressOperand) {
      if(rm == null){
        compareRm = ((PostIndexedAddressOperand) obj).rm == null;
      }
      else {
        compareRm = rm.equals(((PostIndexedAddressOperand) obj).rm);
      }
      return rn.equals(((PostIndexedAddressOperand) obj).rn)
          && compareRm
          && isNegativeRm == ((PostIndexedAddressOperand) obj).isNegativeRm
              && shift.equals(((PostIndexedAddressOperand) obj).shift)
              && expr.equals(((PostIndexedAddressOperand) obj).expr);
    }
    return false;
  }

  @Override
  public String toString() {
    String rnString = rn == null ? "" : "[" + rn.toString() + "]" + ", ";
    String rmString = rm == null ? "" : rm.toString() + ", ";
    String signString = isNegativeRm ? "-" : "";
    String shiftString = shift == ShiftTypes.NONE ? "" : shift.name() + " ";
    String exprString = expr == null ? "" : expr.toString();

    return rnString + signString + rmString + shiftString + exprString;
  }
}
