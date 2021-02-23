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
      case ADD:
      case SUB:
      case MUL:
        return operation.toString()
            + operand1.toString()
            + " , "
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
