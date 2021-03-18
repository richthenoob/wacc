package ic.doc.backend;

import java.util.ArrayList;
import java.util.List;

public class Label<E> {
  private final String functionLabel;
  private List<E> body;

  public void setBody(List<E> body) {
    this.body = body;
  }

  public Label(String functionLabel) {
    this.functionLabel = functionLabel;
    this.body = new ArrayList<>();
  }

  public String getFunctionLabel() {
    return functionLabel;
  }

  public Label<E> addToBody(E item) {
    body.add(item);
    return this;
  }

  public List<E> getBody() {
    return body;
  }

  @Override
  public String toString() {
    return functionLabel;
  }

  @Override
  public boolean equals(Object o){
    if(o == this){
      return true;
    }
    if(!(o instanceof Label)){
      return false;
    }
    Label otherLabel = (Label) o;
    return otherLabel.getFunctionLabel().equals(getFunctionLabel());
  }

  @Override
  public int hashCode(){
    return functionLabel.hashCode();
  }
}
