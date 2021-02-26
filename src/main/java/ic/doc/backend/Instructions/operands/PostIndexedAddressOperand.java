package ic.doc.backend.Instructions.operands;

import ic.doc.backend.Instructions.operands.PreIndexedAddressOperand.ShiftTypes;

/* Adapted from the ARM specification.
 * [Rn], <#expression>]        offset of <expression> bytes
 * [Rn],{+/-}Rm {,<shift>}     offset of +/- contents of index register,
 *                             shifted by <shift> */
public class PostIndexedAddressOperand extends AddressOperand {

  private final RegisterOperand rn;
  private final ImmediateOperand expr;
  private final RegisterOperand rm;
  private final boolean isNegativeRm;
  private boolean signed;
  private final ShiftTypes shift;
  private final Integer shiftVal; // TODO: initialize?

  private PostIndexedAddressOperand(
      RegisterOperand rn,
      ImmediateOperand expr,
      RegisterOperand rm, boolean isNegativeRm,
      ShiftTypes shift,
      Integer shiftVal) {
    this.rn = rn;
    this.expr = expr;
    this.rm = rm;
    this.isNegativeRm = isNegativeRm;
    this.shift = shift;
    this.shiftVal = shiftVal;
    signed = true;
  }

  private PostIndexedAddressOperand withoutSign() {
    signed = false;
    return this;
  }

  /* Public constructor for operands like
   * [r0], #5   Access memory at address, r0, then set r0 = r0 + 5 */
  public static PostIndexedAddressOperand PostIndexedAddressFixedOffset(
      RegisterOperand rn, ImmediateOperand expr) {
    return new PostIndexedAddressOperand(rn, expr,
        null, false, ShiftTypes.NONE, null);
  }

  /* Public constructor for operands like
   * [r2], r4   Access memory at address, R2, then write back R2+R4 to R2 */
  public static PostIndexedAddressOperand PostIndexedAddressByRegister(
      RegisterOperand rn, RegisterOperand rm, boolean isNegativeRm) {
    return new PostIndexedAddressOperand(rn, null, rm, isNegativeRm,
        ShiftTypes.NONE, null);
  }

  /* Public constructor for operands like
   * [r0], r1, LSL #2 Access memory at address r0, then set r0 = r0 + r1 * 4
   */
  public static PostIndexedAddressOperand PostIndexedAddressShiftRegister(
      RegisterOperand rn, RegisterOperand rm, boolean isNegativeRm,
      ShiftTypes shift) {
    return new PostIndexedAddressOperand(rn, null, rm, isNegativeRm, shift, null);
  }

  /* Public constructor for operands like
   * r1, LSL #2 Access r1 * 4
   */
  public static PostIndexedAddressOperand PostIndexedShiftRegister(
      RegisterOperand rm, ShiftTypes shift, int shiftVal) {
    return new PostIndexedAddressOperand(null,
        null, rm, false, shift, shiftVal).withoutSign();
  }

  @Override
  public String toString() {
    String rnString = rn == null ? "" : "[" + rn.toString() + "]";
    String rmString = rm == null ? "" : "," + rm.toString();
    String exprString = expr == null ? "" : "," + expr.toString();
    String signString = "";
    if (signed) {
      signString = isNegativeRm ? "-" : "+";
    }
    String shiftString = shift == ShiftTypes.NONE ? "" : "," + shift.name();
    String shiftValString = shiftVal == null ? "" : " #" + shiftVal;

    return rnString + signString + rmString + exprString
        + shiftString + shiftValString;
  }
}
