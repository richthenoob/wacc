package ic.doc.backend.Data;

public class Data {

  private final int numChar;
  private final String content;

  public int getNumChar() {
    return numChar;
  }

  public String getContent() {
    return content;
  }

  public Data(int numChar, String content) {
    this.numChar = numChar;
    this.content = content;
  }

  public String toAssembly() {
    return "\t" + ".word " + numChar + "\n\t" + ".ascii \"" + content + "\"";
  }
}
