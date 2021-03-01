package ic.doc.frontend.nodes;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.LoadLiterals;
import ic.doc.backend.Instructions.SingleDataTransfer;
import ic.doc.backend.Instructions.Stack;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.statnodes.StatNode;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.ParserRuleContext;

public class ProgNode extends Node {

  private final SymbolTable symbolTable;
  private final List<FunctionNode> functions;
  private final StatNode stat;

  public ProgNode(SymbolTable symbolTable,
      List<FunctionNode> functions, StatNode stat) {
    this.symbolTable = symbolTable;
    this.functions = functions;
    this.stat = stat;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public void translate(Context context) {
    /* Create main label, the entry point of the program. */
    Label<Instruction> inst = new Label<>("main");
    context.getInstructionLabels().add(inst);
    context.setCurrentLabel(inst);
    context.setScope(symbolTable);
    inst.addToBody(Stack.PUSH_FOUR(RegisterOperand.LR,
        context.getCurrentSymbolTable()));

    Map<String, SymbolTable> functionTables = context.getFunctionTables();
    /* Add all function labels first. */
    for (FunctionNode node : functions) {
      functionTables.put(node.getFuncName(), node.getFuncSymbolTable());
      node.translate(context);
    }

    /* Translate rest of program. */
    stat.translate(context);

    /* Pass control back to kernel code that called it. */
    context.restoreScope();
    context.addToCurrentLabel(SingleDataTransfer.LDR(RegisterOperand.R0, new
        ImmediateOperand<>(0)));
    context.addToCurrentLabel(Stack.POP_FOUR(RegisterOperand.PC,
        context.getCurrentSymbolTable()));
    context.addToCurrentLabel(new LoadLiterals());
  }
}
