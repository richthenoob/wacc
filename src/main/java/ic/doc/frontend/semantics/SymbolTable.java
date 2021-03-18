package ic.doc.frontend.semantics;

import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.VariableIdentifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class SymbolTable {

  private final SymbolTable parentSymbolTable;
  private final Map<SymbolKey, Identifier> dictionary;
  private int tableSizeInBytes;
  private int parametersSizeInBytes;

  public SymbolTable(SymbolTable parentSymbolTable) {
    this.parentSymbolTable = parentSymbolTable;
    dictionary = new LinkedHashMap<>();
    tableSizeInBytes = 0;
    parametersSizeInBytes = 0;
  }

  public Map<SymbolKey, Identifier> getDictionary() {
    return dictionary;
  }

  /* Used for adjusting size of stack used by parameters. */
  public void incrementFunctionParametersSize(int paramSize) {
    parametersSizeInBytes += paramSize;
  }

  public int getParametersSizeInBytes() {
    return parametersSizeInBytes;
  }

  /* Used for tracking the largest offset within a symbol table. */
  public int getTableSize() {
    return tableSizeInBytes;
  }

  public void incrementTableSizeInBytes(int sizeOfVarOnStack) {
    tableSizeInBytes += sizeOfVarOnStack;
  }

  public void decrementTableSizeInBytes(int size) {
    tableSizeInBytes -= size;
  }

  /* Increments/decrements offsets for individual variable identifiers in a
   * symbol table, only if it has been "activated",
   * i.e. has been visited already. */
  public void incrementOffset(int offset) {
    for (Identifier obj : dictionary.values()) {
      if (obj instanceof VariableIdentifier &&
          ((VariableIdentifier) obj).isActivated()) {
        ((VariableIdentifier) obj).incrementOffsetStack(offset);
      }
    }
  }

  public void decrementOffset(int offset) {
    for (Identifier obj : dictionary.values()) {
      if (obj instanceof VariableIdentifier &&
          ((VariableIdentifier) obj).isActivated()) {
        ((VariableIdentifier) obj).decrementOffsetStack(offset);
      }
    }
  }

  /* Special method used to obtain (in order) parameters from a symbol
   * table. */
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
