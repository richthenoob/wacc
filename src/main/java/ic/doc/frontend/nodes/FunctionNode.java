package ic.doc.frontend.nodes;

import static ic.doc.backend.Instructions.Stack.*;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.LoadLiterals;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.errors.SyntaxException;
import ic.doc.frontend.nodes.statnodes.ConditionalBranchNode;
import ic.doc.frontend.nodes.statnodes.ExitNode;
import ic.doc.frontend.nodes.statnodes.FunctionReturnNode;
import ic.doc.frontend.nodes.statnodes.SequentialCompositionNode;
import ic.doc.frontend.nodes.statnodes.StatNode;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.Type;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class FunctionNode extends Node {

  private final SymbolTable funcSymbolTable;
  private final SymbolTable parentSymbolTable;
  private final String funcName;
  private final Type returnType;
  private final ParamListNode paramListNode;
  private final StatNode functionBody;

  public FunctionNode(
      SymbolTable funcSymbolTable,
      String funcName,
      Type returnType,
      ParamListNode paramListNode,
      StatNode functionBody) {
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

  public StatNode getFunctionBody() {
    return functionBody;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Last stat should end with return or exit */
    if (!endsWithReturnOrExit(functionBody)) {
      throw new SyntaxException(
          "Function " + getInput() + " is not ended with a return or exit statement",
          ctx.getStart().getLine(),
          ctx.getStart().getCharPositionInLine());
    }
  }

  @Override
  public void translate(Context context) {
    /* Create new label for function */
    Label<Instruction> funcLabel = new Label<>("f_" + funcName);
    context.getInstructionLabels().add(funcLabel);
    context.setCurrentLabel(funcLabel);

    /* Set scope to function's symbol table for translation of paramListNodes
     * and statements to add to the correct symbol table. */
    context.setScope(funcSymbolTable);

    /* Translate parameters. */
    paramListNode.translate(context);
    context.addToCurrentLabel(PUSH(RegisterOperand.LR));
    funcSymbolTable.incrementOffset(4);

    /* Translate body of function and pop back to main */
    functionBody.translate(context);
    funcSymbolTable.decrementOffset(4);
    context.restoreScope();
    context.addToCurrentLabel(POP(RegisterOperand.PC));
    context.addToCurrentLabel(POP(RegisterOperand.PC));
    context.addToCurrentLabel(new LoadLiterals());
  }

  private boolean endsWithReturnOrExit(StatNode stat) {
    if (stat instanceof FunctionReturnNode || stat instanceof ExitNode) {
      return true;
    }
    if (stat instanceof SequentialCompositionNode) {
      SequentialCompositionNode seqNode = (SequentialCompositionNode) stat;
      List<StatNode> stats = seqNode.getStatements();
      StatNode lastStat = stats.get(stats.size() - 1);
      return endsWithReturnOrExit(lastStat);
    }

    if (stat instanceof ConditionalBranchNode) {
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
