package ic.doc.frontend.types;

public class PairType extends Type {

  private static final int VAR_SIZE = 4;

  public int getVarSize() {
    return VAR_SIZE;
  }

  public static final String CLASS_NAME = "PAIR";
  private final Type type1;
  private final Type type2;

  public PairType(Type type1, Type type2) {
    this.type1 = type1;
    this.type2 = type2;
  }

  public Type getType1() {
    return type1;
  }

  public Type getType2() {
    return type2;
  }

  @Override
  public String toString() {
    return "PAIR" + "(" + getType1() + ", " + getType2() + ")";
  }
}
