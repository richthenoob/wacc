package ic.doc.backend.Instructions;

public class DataProcessing extends Instruction {
  private Operand operand1;
  private Operand operand2;
  private Operand operand3; // Optional
  private Operation operation;

  @Override
  public String toAssembly() {
    switch (operation) {
      case ADDS:
        return "ADDS "
            + operand1.toString()
            + ", "
            + operand2.toString()
            + ", "
            + operand3.toString();
      case ADD:
        return "ADD "
            + operand1.toString()
            + ", "
            + operand2.toString()
            + ", "
            + operand3.toString();
      case SUB:
        return "SUB "
            + operand1.toString()
            + ", "
            + operand2.toString()
            + ", "
            + operand3.toString();
      case MUL:
        return "MUL "
            + operand1.toString()
            + ", "
            + operand2.toString()
            + ", "
            + operand3.toString();
        //      case OR:
        //        break;
        //      case TST:   // not sure if need
        //        break;
      case CMP:
        return "CMP " + operand1.toString() + ", " + operand2.toString();
      default:
        return null;
    }
  }
}
