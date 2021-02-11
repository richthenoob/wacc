package ic.doc.semantics.IdentifierObjects;

import ic.doc.semantics.Types.Type;

public class VariableIdentifier extends Identifier {

  public VariableIdentifier(Type type) {
    super(type);
  }

  @Override
  public String toString() {
    return super.getType().toString();
  }
}
