package ic.doc.frontend.semantics;

import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.types.BoolType;
import ic.doc.frontend.types.CharType;
import ic.doc.frontend.types.Type;

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

  public void incrementTableSizeInBytes(int amount){
    tableSizeInBytes +=amount;
  }


  public void incrementTableSizeInBytes(Type type){
    int amount;
    if (type instanceof BoolType || type instanceof CharType) {
      amount = 1;
    } else {
      amount = 4;
    }
    tableSizeInBytes +=amount;
  }

  public void incrementOffset(Type type){
    int amount;
    if (type instanceof BoolType || type instanceof CharType) {
      amount = 1;
    } else {
      amount = 4;
    }
    for(Identifier obj: dictionary.values()){
      if(obj instanceof VariableIdentifier){
        ((VariableIdentifier) obj).incrementOffsetStack(amount);
      }
    }
  }

  public void incrementOffset(int amount){
    for(Identifier obj: dictionary.values()){
      if(obj instanceof VariableIdentifier){
        ((VariableIdentifier) obj).incrementOffsetStack(amount);
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
