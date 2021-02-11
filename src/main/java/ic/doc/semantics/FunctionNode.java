package ic.doc.semantics;

import ic.doc.SyntaxException;
import ic.doc.semantics.IdentifierObjects.FunctionIdentifier;
import ic.doc.semantics.StatNodes.*;
import ic.doc.semantics.Types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class FunctionNode extends Node {

  private final SymbolTable funcSymbolTable;
  private final SymbolTable parentSymbolTable;
  private final String funcName;
  private final Type returnType;
  private final ParamListNode paramListNode;
  private final StatNode functionBody;

  public FunctionNode(SymbolTable funcSymbolTable, String funcName,
      Type returnType, ParamListNode paramListNode, StatNode functionBody) {
//    this.parentSymbolTable = parentSymbolTable;
    this.funcSymbolTable = funcSymbolTable;
    this.parentSymbolTable = funcSymbolTable.getParentSymbolTable();
    this.funcName = funcName;
    this.returnType = returnType;
    this.paramListNode = paramListNode;
    this.functionBody = functionBody;
  }

  public SymbolTable getFuncSymbolTable() {
    return funcSymbolTable;
  }

  public SymbolTable getParentSymbolTable() {
    return parentSymbolTable;
  }

  public String getFuncName() {
    return funcName;
  }

  public Type getDeclaredReturnType() {
    return returnType;
  }

  public ParamListNode getParamList() {
    return paramListNode;
  }

  public StatNode getFunctionBody() { return functionBody; }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Check if the last stat ends with return or exit */
    if(!endsWithReturnOrExit(functionBody)){
      throw new SyntaxException("Function " + getInput() + " is not ended with a return or exit statement",
              ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine());
    }
  }

  private boolean endsWithReturnOrExit(StatNode stat){
    if(stat instanceof FunctionReturnNode || stat instanceof ExitNode){
      return true;
    }
    if(stat instanceof SequentialCompositionNode){
      SequentialCompositionNode seqNode = (SequentialCompositionNode) stat;
      List<StatNode> stats = seqNode.getStatements();
      StatNode lastStat = stats.get(stats.size() - 1);
      return endsWithReturnOrExit(lastStat);
    }

    if(stat instanceof ConditionalBranchNode){
      ConditionalBranchNode condNode = (ConditionalBranchNode) stat;
      StatNode trueBody = condNode.getTrueBody();
      StatNode falseBody = condNode.getFalseBody();
      return endsWithReturnOrExit(trueBody) && endsWithReturnOrExit(falseBody);
    }

    return false;
  }

  public String getInput() {
    return getFuncName();
  }

}
