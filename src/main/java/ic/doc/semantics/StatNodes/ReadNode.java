package ic.doc.semantics.StatNodes;

import ic.doc.semantics.ExprNodes.ExprNode;
import ic.doc.semantics.ExprNodes.VariableNode;
import ic.doc.semantics.IdentifierObjects.Identifier;
import ic.doc.semantics.SymbolKey;
import ic.doc.semantics.Types.CharType;
import ic.doc.semantics.Types.ErrorType;
import ic.doc.semantics.Types.IntType;
import ic.doc.semantics.Types.Type;
import ic.doc.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class ReadNode extends StatNode {

  /* A readNode may contain an expression, or it may just be an identifier i.e. variable */
  private ExprNode expr;

  public ReadNode(ExprNode expr){
    this.expr = expr;
  }

  public ExprNode getExpr() {
    return expr;
  }


  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    Type type = expr.getType();
    /* Errors should have already been caught, no need to print again*/
    if(type instanceof ErrorType){
      return;
    }
    if (!(type instanceof CharType || type instanceof IntType)) {
      visitor.getSemanticErrorList()
          .addTypeException(ctx, expr.getInput(), "CHAR or INT", type.toString());
    }
//    Type type = null;
//    String input = "";
//
//    /* If it is a variable node, we need to check if the identifier exists*/
//    if(expr instanceof VariableNode){
//      String name = ((VariableNode) expr).getName();
//      SymbolKey key = new SymbolKey(name, false);
//      Identifier id = visitor.getCurrentSymbolTable().lookupAll(key);
//      if(id == null){
//        /* throw not found in symbol table error*/
//      } else {
//        type = id.getType();
//        input = "Read: " + name;
//      }
//    } else {
//      /* Otherwise, just get the type*/
//        type = expr.getType();
//    }
//
//    String input = "";
//    if (exprNode != null) {
//      type = exprNode.getType();
//      input = "READ:" + exprNode.getInput();
//    } else if (identifierName != null) {
//      Identifier id = visitor.getCurrentSymbolTable().lookupAll(identifierName);
//      type = id.getType();
//      input = "READ" + id.toString();
//    } else {
//      throw new IllegalStateException("Attempting to read something that is not an expr");
//    }
//    if (!(type instanceof CharType || type instanceof IntType)) {
//      visitor.addTypeException(ctx, input, "CHAR or INT", type.toString());
//    }
  }
}
