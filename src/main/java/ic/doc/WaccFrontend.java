package ic.doc;

import ic.doc.antlr.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class WaccFrontend {

    public static String parse(String stringInput) {
        // create a CharStream that reads from standard input
        CharStream input = CharStreams.fromString(stringInput);

        // create a lexer that feeds off of input CharStream
        BasicLexer lexer = new BasicLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        BasicParser parser = new BasicParser(tokens);

        ParseTree tree = parser.prog(); // begin parsing at prog rule

        return tree.toStringTree(parser);
    }

    public static void main(String[] args) {
        System.out.println(parse(args[0]));
    }
}
