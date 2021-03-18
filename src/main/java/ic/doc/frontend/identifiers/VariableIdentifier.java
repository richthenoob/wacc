package ic.doc.frontend.identifiers;

import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.types.Type;

public class VariableIdentifier extends Identifier {

  private int offsetStack;
  private boolean activated;
  private int arraySize;
  private boolean isClassVariable;

  public VariableIdentifier(Type type) {
    super(type);
    offsetStack = 0;
    activated = false;
  }

  public boolean isClassVariable() {
    return isClassVariable;
  }

  public void setClassVariable() {
    isClassVariable = true;
  }

  public boolean isActivated() {
    return activated;
  }

  public int getOffsetStack(SymbolTable symbolTableCalledFrom, SymbolKey symbolKey ) {
    SymbolTable currSymbolTable = symbolTableCalledFrom;
    int tableOffset = 0;
    while(currSymbolTable.lookup(symbolKey) == null){
      tableOffset+= currSymbolTable.getTableSize();
      currSymbolTable = currSymbolTable.getParentSymbolTable();
    }
    return offsetStack + tableOffset;
  }

  public int getArraySize() {
    return arraySize;
  }

  public void setArraySize(int arraySize) {
    this.arraySize = arraySize;
  }

  public void incrementOffsetStack(int offset) {
    this.offsetStack += offset;
  }

  public void setOffsetStack(int offsetStack) {
    this.offsetStack = offsetStack;
  }

  public void incrementOffsetStack() {
    this.offsetStack += 4;
  }

  public void decrementOffsetStack(int offset) {
    this.offsetStack -= offset;
  }

  public void setActivated() {
    this.activated = true;
  }

  @Override
  public String toString() {
    return super.getType().toString();
  }

  @Override
  public Identifier getNewCopy() {
    return new VariableIdentifier(getType());
  }
}
