package ic.doc.frontend.identifiers;

import ic.doc.frontend.types.Type;

public class ParamIdentifier extends Identifier {

  public ParamIdentifier(Type type) {
    super(type);
  }

  @Override
  public String toString() {
    return super.getType().toString();
  }
}
