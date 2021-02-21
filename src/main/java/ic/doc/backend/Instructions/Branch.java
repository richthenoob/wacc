package ic.doc.backend.Instructions;

import ic.doc.backend.Label;

public class Branch extends Instruction {
  private Condition condition;
  private Label label;


  @Override
  public String toAssembly() {
    switch(condition){
      case BEQ:
        return "BEQ "+ label.toString();
      case BNE:
        return "BNE "+ label.toString();
      case  BGE:
        return "BGE "+ label.toString();
      case BLT:
        return "BLT "+ label.toString();
      case BGT:
        return "BGT "+ label.toString();
      case BLE:
        return "BLE "+ label.toString();
      default:
        return "B" + label.toString();
    }
  }
}
