package ic.doc.frontend.errors;

public class SyntaxException extends Error {

    public SyntaxException(String message, int line, int charPos) {
        String formattedMessage =  "Syntax error at line " + line
                + ":" + charPos + ": " + message;
        System.out.println(formattedMessage);
    }

}
