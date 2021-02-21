package ic.doc.backend;

import java.util.List;

public class Label<E> {
  private String function_label;
  private List<E> body;

  @Override
  public String toString() {
    return function_label;
  }
}
