package ic.doc.backend.Instructions.operands;

import ic.doc.backend.Instructions.operands.PreIndexedAddressOperand.ShiftTypes;

/* Adapted from the ARM specification.
 * [Rn], <#expression>]        offset of <expression> bytes
 * [Rn],{+/-}Rm {,<shift>}     offset of +/- contents of index register,
 *                             shifted by <shift> */
public class PostIndexedAddressOperand {

  private final RegisterOperand rn;
  private final ImmediateOperand expr;
  private final RegisterOperand rm;
  private final boolean isNegativeRm;
  private final ShiftTypes shift;

  private PostIndexedAddressOperand(
      RegisterOperand rn,
      ImmediateOperand expr,
      RegisterOperand rm, boolean isNegativeRm,
      ShiftTypes shift) {
    this.rn = rn;
    this.expr = expr;
    this.rm = rm;
    this.isNegativeRm = isNegativeRm;
    this.shift = shift;
  }

  /* Public constructor for operands like
   * [r0], #5   Access memory at address, r0, then set r0 = r0 + 5 */
  public PostIndexedAddressOperand PreIndexedAddressFixedOffset(
      RegisterOperand rn, ImmediateOperand expr) {
    return new PostIndexedAddressOperand(rn, expr, null, false, ShiftTypes.NONE);
  }

  /* Public constructor for operands like
   * [r2], r4   Access memory at address, R2, then write back R2+R4 to R2 */
  public PostIndexedAddressOperand PreIndexedAddressByRegister(
      RegisterOperand rn, RegisterOperand rm, boolean isNegativeRm) {
    return new PostIndexedAddressOperand(rn, null, rm, isNegativeRm,
        ShiftTypes.NONE);
  }

  /* Public constructor for operands like
   * [r0], r1, LSL #2 Access memory at address r0, then set r0 = r0 + r1 * 4
   */
  public PostIndexedAddressOperand PreIndexedAddressShiftRegister(
      RegisterOperand rn, RegisterOperand rm, boolean isNegativeRm,
      ShiftTypes shift) {
    return new PostIndexedAddressOperand(rn, null, rm, isNegativeRm, shift);
  }

  @Override
  public String toString() {
    String rmString = rm == null ? "" : "," + rm.toString();
    String exprString = expr == null ? "" : "," + expr.toString();
    String signString = isNegativeRm ? "-" : "+";
    String shiftString = shift == ShiftTypes.NONE ? "" : "," + shift.name();

    return "[" + rn.toString() + "]" + signString + rmString + exprString
        + shiftString;
  }
}
