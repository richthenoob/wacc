package ic.doc.semantics;

import java.util.Objects;

public class SymbolKey {
  private final String name;
  private final Boolean isFunction;

  public SymbolKey(String name, Boolean isFunction){
    this.name = name;
    this.isFunction = isFunction;
  }

  public String getName(){
    return name;
  }

  public Boolean getIsFunction(){
    return isFunction;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SymbolKey symbolKey = (SymbolKey) o;
    return Objects.equals(name, symbolKey.name) &&
            Objects.equals(isFunction, symbolKey.isFunction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, isFunction);
  }

}
