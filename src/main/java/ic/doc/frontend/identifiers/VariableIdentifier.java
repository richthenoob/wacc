package ic.doc.frontend.identifiers;

import ic.doc.frontend.types.Type;

public class VariableIdentifier extends Identifier {

  private int offsetStack;
  private boolean activated;

  public boolean isActivated() {
    return activated;
  }

  public int getOffsetStack() {
    return offsetStack;
  }

  public void setOffsetStack(int offsetStack) {
    this.offsetStack = offsetStack;
  }

  public void incrementOffsetStack() {
    this.offsetStack += 4;
  }

  public void decrementOffsetStack() {
    this.offsetStack -= 4;
  }

  public VariableIdentifier(Type type) {
    super(type);
    offsetStack = 0;
    activated = false;
  }

  public void setActivated() {
    this.activated = true;
  }

  @Override
  public String toString() {
    return super.getType().toString();
  }
}
