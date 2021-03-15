package ic.doc.backend.instructions.operands;

import java.util.Objects;

/* Adapted from the ARM specification.
 * [Rn]                       offset of zero
 * [Rn, <#expression>]        offset of <expression> bytes
 * [Rn,{+/-}Rm {,<shift>}]    offset of +/- contents of index register,
 *                            shifted by <shift> */
public class PreIndexedAddressOperand extends AddressOperand {

  private RegisterOperand rn;
  private ImmediateOperand expr;
  private RegisterOperand rm;
  private boolean isNegativeRm;
  private ShiftTypes shift;
  private boolean jump;

  public PreIndexedAddressOperand(RegisterOperand rn) {
    this.rn = rn;
    this.isNegativeRm = false;
    this.shift = ShiftTypes.NONE;
    this.jump = false;
  }

  public PreIndexedAddressOperand withRN(RegisterOperand rn) {
    this.rn = rn;
    return this;
  }

  public PreIndexedAddressOperand withRM(RegisterOperand rm) {
    this.rm = rm;
    return this;
  }

  public PreIndexedAddressOperand withNegative() {
    this.isNegativeRm = true;
    return this;
  }

  public PreIndexedAddressOperand withShift(ShiftTypes shift) {
    this.shift = shift;
    return this;
  }

  public PreIndexedAddressOperand withExpr(ImmediateOperand expr) {
    this.expr = expr;
    return this;
  }

  public PreIndexedAddressOperand withJump() {
    this.jump = true;
    return this;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rn, rm, isNegativeRm, shift, expr);
  }

  @Override
  public boolean equals(Object obj) {
    boolean compareRm;
    if (obj instanceof PreIndexedAddressOperand) {
      if(rm == null){
        compareRm = ((PreIndexedAddressOperand) obj).rm == null;
      }
      else {
        compareRm = rm.equals(((PreIndexedAddressOperand) obj).rm);
      }
      return rn.equals(((PreIndexedAddressOperand) obj).rn)
              && compareRm
              && isNegativeRm == ((PreIndexedAddressOperand) obj).isNegativeRm
              && shift.equals(((PreIndexedAddressOperand) obj).shift)
              && expr.equals(((PreIndexedAddressOperand) obj).expr);
    }
    return false;
  }


  @Override
  public String toString() {
    String rmString = rm == null ? "" : ", " + rm.toString();
    String exprString = expr == null ? "" : ", " + expr.toString();
    String signString = isNegativeRm ? "-" : "";
    String shiftString = shift == ShiftTypes.NONE ? "" : ", " + shift.name();
    String jumpString = jump ? "!" : "";

    return "[" + rn.toString() + signString + rmString
        + shiftString + exprString + "]" + jumpString;
  }
}
