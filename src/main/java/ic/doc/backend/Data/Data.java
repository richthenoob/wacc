package ic.doc.backend.Data;

public class Data {
    private int numChar;
    private String content;

    public String toAssembly(){
        return "    .word "+ numChar + "\n    .ascii  +" + content;
    }


}
