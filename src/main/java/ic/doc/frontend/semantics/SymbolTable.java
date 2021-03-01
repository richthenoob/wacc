package ic.doc.frontend.semantics;

import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.VariableIdentifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class SymbolTable {

  private final SymbolTable parentSymbolTable;
  private final Map<SymbolKey, Identifier> dictionary;
  private int tableSizeInBytes;

  public SymbolTable(SymbolTable parentSymbolTable) {
    this.parentSymbolTable = parentSymbolTable;
    dictionary = new LinkedHashMap<>();
    tableSizeInBytes = 0;
  }

  public int getTableSize() {
    return tableSizeInBytes;
  }

  public void incrementTableSizeInBytes(int sizeOfVarOnStack) {
    tableSizeInBytes += sizeOfVarOnStack;
  }

  public void incrementOffset(int offset) {
    for (Identifier obj : dictionary.values()) {
      if (obj instanceof VariableIdentifier && ((VariableIdentifier) obj).isActivated()) {
        ((VariableIdentifier) obj).incrementOffsetStack(offset);
      }
    }
  }

  public void decrementOffset(int offset) {
    for (Identifier obj : dictionary.values()) {
      if (obj instanceof VariableIdentifier && ((VariableIdentifier) obj).isActivated()) {
        ((VariableIdentifier) obj).decrementOffsetStack(offset);
      }
    }
  }

  public SymbolTable getParentSymbolTable() {
    return parentSymbolTable;
  }

  public void add(SymbolKey key, Identifier obj) {
    dictionary.put(key, obj);
  }

  public Identifier lookup(SymbolKey key) {
    return dictionary.get(key);
  }

  public Identifier lookupAll(SymbolKey key) {
    SymbolTable curr = this;
    while (curr != null) {
      Identifier obj = curr.lookup(key);
      if (obj != null) {
        return obj;
      }
      curr = curr.parentSymbolTable;
    }
    return null;
  }
}
