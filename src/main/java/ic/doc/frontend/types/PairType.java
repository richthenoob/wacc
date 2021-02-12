package ic.doc.frontend.types;

import java.util.Objects;

public class PairType extends Type {

  public final static String CLASS_NAME = "PAIR";
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
