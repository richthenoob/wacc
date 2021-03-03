package ic.doc.frontend.semantics;

import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.VariableIdentifier;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class SymbolTable {

  private final SymbolTable parentSymbolTable;
  private final Map<SymbolKey, Identifier> dictionary;
  private int tableSizeInBytes;
  private int functionParametersSizeInBytes;

  public SymbolTable(SymbolTable parentSymbolTable) {
    this.parentSymbolTable = parentSymbolTable;
    dictionary = new LinkedHashMap<>();
    tableSizeInBytes = 0;
    functionParametersSizeInBytes = 0;
  }

  public void incrementFunctionParametersSize(int paramSize) {
    functionParametersSizeInBytes += paramSize;
  }

  public int getFunctionParametersSizeInBytes() {
    return functionParametersSizeInBytes;
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

  public Identifier getIdentifier(int i) {
    int curr = 0;
    for (Identifier obj : dictionary.values()) {
      if (curr == i) {
        return obj;
      }
      curr++;
    }
    return null;
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
