package ic.doc.backend.Data;

public class StringData extends Data{

  public StringData(int numChar, String content) {
    super(numChar, content);
  }

  @Override
  public String toAssembly() {
    return "\t" + ".word " + this.getNumChar() + "\n\t" + ".ascii " + this.getContent();
  }
}
