package ic.doc.backend.Instructions;

import ic.doc.backend.Label;

public class Branch extends Instruction {
  private Condition condition;
  private Label label;

  public Branch(Condition condition, Label label) {
    this.condition = condition;
    this.label = label;
  }


  @Override
  public String toAssembly() {
    return condition.toString() + " " + label.toString();
  }
}
