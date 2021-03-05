package ic.doc.backend.instructions;

public class Branch extends Instruction {

  private final Condition condition;
  private final String destinationLabel;

  private Branch(Condition condition, String label) {
    this.condition = condition;
    this.destinationLabel = label;
  }

  public static Branch BNE(String label) {
    return new Branch(Condition.BNE, label);
  }

  public static Branch BEQ(String label) {
    return new Branch(Condition.BEQ, label);
  }

  public static Branch BGE(String label) {
    return new Branch(Condition.BGE, label);
  }

  public static Branch BLT(String label) {
    return new Branch(Condition.BLT, label);
  }

  public static Branch BGT(String label) {
    return new Branch(Condition.BGT, label);
  }

  public static Branch BLEQ(String label) {
    return new Branch(Condition.BLEQ, label);
  }

  public static Branch B(String label) {
    return new Branch(Condition.B, label);
  }

  public static Branch BLNE(String label) {
    return new Branch(Condition.BLNE, label);
  }

  public static Branch BLVS(String label) {
    return new Branch(Condition.BLVS, label);
  }

  public static Branch BL(String label) {
    return new Branch(Condition.BL, label);
  }

  public static Branch BLLT(String label) {
    return new Branch(Condition.BLLT, label);
  }

  public static Branch BLCS(String label) {
    return new Branch(Condition.BLCS, label);
  }

  @Override
  public String toAssembly() {
    return condition.toString() + " " + destinationLabel;
  }
}
