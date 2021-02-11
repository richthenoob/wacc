package ic.doc.semantics.IdentifierObjects;

import ic.doc.semantics.Types.Type;

public class ParamIdentifier extends Identifier {

  public ParamIdentifier(Type type) {
    super(type);
  }

  @Override
  public String toString() {
    return super.getType().toString();
  }
}
