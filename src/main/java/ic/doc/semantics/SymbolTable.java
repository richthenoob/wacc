package ic.doc.semantics;

import ic.doc.semantics.IdentifierObjects.Identifier;
import java.util.LinkedHashMap;
import java.util.Map;

public class SymbolTable {

  private SymbolTable parentSymbolTable;
  private Map<SymbolKey, Identifier> dictionary;

  public SymbolTable(SymbolTable parentSymbolTable) {
    this.parentSymbolTable = parentSymbolTable;
    dictionary = new LinkedHashMap<>();
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
