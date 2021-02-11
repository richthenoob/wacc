package ic.doc.frontend.types;

import java.util.Objects;

public class PairType implements Type {

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
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }

    if (o == null) {
      return false;
    }

    if (o.getClass().equals(AnyType.class)) {
      return true;
    }

    if (getClass() != o.getClass()) {
      return false;
    }

    PairType pair = (PairType) o;

    return Objects.equals(getType1(), pair.getType1())
        && Objects.equals(getType2(), pair.getType2());
  }

  @Override
  public int hashCode() {
    return Objects.hash(type1, type2);
  }

  @Override
  public String toString() {
    return "PAIR" + "(" + getType1() + ", " + getType2() + ")";
  }
}
