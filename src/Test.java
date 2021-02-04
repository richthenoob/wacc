// import ANTLR's runtime libraries
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

// import antlr package (your code)
import antlr.*;

public class Test {

  public static void main(String[] args) throws Exception {

    // create a CharStream that reads from standard input
    CharStream input = CharStreams.fromStream(System.in);

    // create a lexer that feeds off of input CharStream
    BasicLexer lexer = new BasicLexer(input);

    // create a buffer of tokens pulled from the lexer
    CommonTokenStream tokens = new CommonTokenStream(lexer);

    // create a parser that feeds off the tokens buffer
    BasicParser parser = new BasicParser(tokens);

    ParseTree tree = parser.prog(); // begin parsing at prog rule

    System.out.println(tree.toStringTree(parser)); // print LISP-style tree

  }

}