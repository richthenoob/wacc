package ic.doc.backend.Instructions.operands;

public abstract class AddressOperand extends Operand {

  public enum ShiftTypes {
    LSL, /* Left shift logical     */
    ASL, /* Arithmetic left shift  */
    RSL, /* Right shift logical    */
    ASR, /* Arithmetic right shift */
    NONE;
  }

}
