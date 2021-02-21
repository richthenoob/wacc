package ic.doc.backend;

import java.util.List;

public class Label<E> {
  private String functionLabel;
  private List<E> body;

  @Override
  public String toString() {
    return functionLabel;
  }
}
