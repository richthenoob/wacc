package ic.doc.frontend.identifiers;

import ic.doc.frontend.types.Type;

public abstract class Identifier {

  private final Type type;

  public Identifier(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

  public abstract String toString();

  public abstract Identifier getNewCopy();
}
