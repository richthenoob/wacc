package ic.doc.backend.Data;

public class Data {

  private int numChar;
  private String content;

  public Data(int numChar, String content) {
    this.numChar = numChar;
    this.content = content;
  }


  public String toAssembly() {
    return "    .word " + numChar + "\n    .ascii  +" + content;
  }
}
