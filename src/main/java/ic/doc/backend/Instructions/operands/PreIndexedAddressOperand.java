package ic.doc.backend.Instructions.operands;

/* Adapted from the ARM specification.
 * [Rn]                       offset of zero
 * [Rn, <#expression>]        offset of <expression> bytes
 * [Rn,{+/-}Rm {,<shift>}]    offset of +/- contents of index register,
 *                            shifted by <shift> */
public class PreIndexedAddressOperand extends AddressOperand {

  public enum ShiftTypes {
    LSL, /* Left shift logical     */
    ASL, /* Arithmetic left shift  */
    RSL, /* Right shift logical    */
    ASR, /* Arithmetic right shift */
    NONE;
  }

  private final RegisterOperand rn;
  private final ImmediateOperand expr;
  private final RegisterOperand rm;
  private final boolean isNegativeRm;
  private final ShiftTypes shift;
  private final boolean jump;

  private PreIndexedAddressOperand(
      RegisterOperand rn,
      ImmediateOperand expr,
      RegisterOperand rm, boolean isNegativeRm, ShiftTypes shift,
      boolean jump) {
    this.rn = rn;
    this.expr = expr;
    this.rm = rm;
    this.isNegativeRm = isNegativeRm;
    this.shift = shift;
    this.jump = jump;
  }

  /* Public constructor for operands like
   * [r0] Access memory of address stored in r0
   * [sp] Access memory of address stored in sp */
  public static PreIndexedAddressOperand PreIndexedAddressZeroOffset(
      RegisterOperand rn) {
    return new PreIndexedAddressOperand(rn, null, null,
        false, ShiftTypes.NONE, false);
  }

  /* Public constructor for operands like
   * [r0, #5]   Access memory at address stored in r0 + 5
   * [sp, #-16] Access memory at address stored in sp - 16 */
  public static PreIndexedAddressOperand PreIndexedAddressFixedOffset(
      RegisterOperand rn, ImmediateOperand expr) {
    return new PreIndexedAddressOperand(rn, expr, null,
        false, ShiftTypes.NONE, false);
  }

  /* Public constructor for operands like
   * [r0, #5]!   Access memory at address stored in r0 + 5
   * [sp, #-16]! Access memory at address stored in sp - 16 */
  public static PreIndexedAddressOperand PreIndexedAddressFixedOffsetJump(
      RegisterOperand rn, ImmediateOperand expr) {
    return new PreIndexedAddressOperand(rn, expr, null,
        false, ShiftTypes.NONE, true);
  }

  /* Public constructor for operands like
   * [r0, r1]   Access memory at address stored in r0 + address stored in r1
   * [sp, -r0]  Access memory at address [sp] - [r0]  */
  public static PreIndexedAddressOperand PreIndexedAddressByRegister(
      RegisterOperand rn, RegisterOperand rm, boolean isNegativeRm) {
    return new PreIndexedAddressOperand(rn, null, rm, isNegativeRm,
        ShiftTypes.NONE, false);
  }

  /* Public constructor for operands like
   * [r0, r1, LSL #2] Access memory at address r0 + r1 * 4
   */
  public static PreIndexedAddressOperand PreIndexedAddressShiftRegister(
      RegisterOperand rn, RegisterOperand rm, boolean isNegativeRm,
      ShiftTypes shift) {
    return new PreIndexedAddressOperand(rn, null, rm, isNegativeRm, shift, false);
  }

  @Override
  public String toString() {
    String rmString = rm == null ? "" : "," + rm.toString();
    String exprString = expr == null ? "" : "," + expr.toString();
    String signString = isNegativeRm ? "-" : "";
    String shiftString = shift == ShiftTypes.NONE ? "" : "," + shift.name();
    String jumpString = jump ? "!" : "";

    return "[" + rn.toString() + signString + rmString
        + shiftString + exprString + "]" + jumpString;
  }
}
