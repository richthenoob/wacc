package ic.doc.backend.Instructions;

import ic.doc.backend.Label;

public class Branch extends Instruction {
  private Condition condition;
  private Label label;

  private Branch(Condition condition, Label label) {
    this.condition = condition;
    this.label = label;
  }

  public static Branch BNE(Label label){
    return new Branch(Condition.BNE, label);
  }

  public static Branch BEQ(Label label){
    return new Branch(Condition.BEQ, label);
  }

  public static Branch BGE(Label label){
    return new Branch(Condition.BGE, label);
  }

  public static Branch BLT(Label label){
    return new Branch(Condition.BLT, label);
  }

  public static Branch BGT(Label label){
    return new Branch(Condition.BGT, label);
  }

  public static Branch BLE(Label label){
    return new Branch(Condition.BLE, label);
  }

  public static Branch B(Label label){
    return new Branch(Condition.B, label);
  }

  public static Branch BLNE(Label label){
    return new Branch(Condition.BLNE, label);
  }

  public static Branch BLVS(Label label){
    return new Branch(Condition.BLVS, label);
  }

  @Override
  public String toAssembly() {
    return condition.toString() + " " + label.toString();
  }
}
