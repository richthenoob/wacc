package ic.doc.backend;

import java.util.ArrayList;
import java.util.List;

public class Label<E> {
  private final String functionLabel;
  private final List<E> body;

  public Label(String functionLabel) {
    this.functionLabel = functionLabel;
    this.body = new ArrayList<>();
  }

  public String getFunctionLabel() {
    return functionLabel;
  }

  public void addToBody(E item) {
    body.add(item);
  }

  public List<E> getBody() {
    return body;
  }

  @Override
  public String toString() {
    return functionLabel;
  }
}
