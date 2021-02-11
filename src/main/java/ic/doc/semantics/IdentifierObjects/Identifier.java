package ic.doc.semantics.IdentifierObjects;

import ic.doc.semantics.Types.Type;

public abstract class Identifier {

  private final Type type;

  public Identifier(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

  public abstract String toString();
}
