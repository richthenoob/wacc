package ic.doc.backend.Instructions;

import ic.doc.backend.Label;

public class Branch extends Instruction {
  private Condition condition;
  private Label label;


  @Override
  public String toAssembly() {
    return condition.toString() + " " + label.toString();
  }
}
