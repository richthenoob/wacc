package ic.doc;

import ic.doc.antlr.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class WaccFrontend {

    public static String parse(InputStream inputStream) throws IOException {
        // create a CharStream that reads from standard input
        CharStream input = CharStreams.fromStream(inputStream);

        // create a lexer that feeds off of input CharStream
        BasicLexer lexer = new BasicLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        BasicParser parser = new BasicParser(tokens);

        ParseTree tree = parser.prog(); // begin parsing at prog rule

        return tree.toStringTree(parser);
    }

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Please provide a filepath.");
            return;
        }

        File file = new File(args[0]);
        if (file.exists()) {
            InputStream inputStream = new FileInputStream(file);
            System.out.println(parse(inputStream));
        } else {
            System.out.println("Invalid filepath provided: " + args[0]);
        }
    }
}
