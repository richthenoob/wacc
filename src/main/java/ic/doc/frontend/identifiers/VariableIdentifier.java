package ic.doc.frontend.identifiers;

import ic.doc.frontend.types.Type;

public class VariableIdentifier extends Identifier {

  private int offsetStack;

  public int getOffsetStack() {
    return offsetStack;
  }

  public void incrementOffsetStack() {
    this.offsetStack += 4;
  }

  public VariableIdentifier(Type type) {
    super(type);
    offsetStack = 0;
  }

  @Override
  public String toString() {
    return super.getType().toString();
  }
}
