package ic.doc.frontend.semantics;

import java.util.Objects;

public class SymbolKey {

  public enum KeyTypes {
    VARIABLE,
    FUNCTION,
    CLASS
  }

  private final String name;
  private final KeyTypes keyType;

  public SymbolKey(String name, KeyTypes keyType) {
    this.name = name;
    this.keyType = keyType;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SymbolKey symbolKey = (SymbolKey) o;
    return Objects.equals(name, symbolKey.name) && Objects.equals(keyType, symbolKey.keyType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, keyType);
  }
}
