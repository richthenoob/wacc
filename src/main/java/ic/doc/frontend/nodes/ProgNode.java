package ic.doc.frontend.nodes;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.Instruction;
import ic.doc.backend.instructions.LoadLiterals;
import ic.doc.backend.instructions.SingleDataTransfer;
import ic.doc.backend.instructions.Stack;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
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
    /* Add all function labels and evaluate its parameters first. This
     * ensures that any recursive definitions can properly resolve
     * a function's parameters. */
    Map<String, SymbolTable> functionTables = context.getFunctionTables();
    for (FunctionNode node : functions) {
      functionTables.put(node.getFuncName(), node.getFuncSymbolTable());
      node.translateParameters(context);
    }

    /* Properly evaluate function nodes. */
    for (FunctionNode node : functions) {
      node.translate(context);
    }

    /* Create main label, the entry point of the program. */
    Label<Instruction> inst = new Label<>("main");
    context.getInstructionLabels().add(inst);
    context.setCurrentLabel(inst);
    context.setScope(symbolTable);
    inst.addToBody(Stack.PUSH(RegisterOperand.LR));

    /* Translate rest of program. */
    stat.translate(context);

    /* Pass control back to kernel code that called it. */
    context.restoreScope();
    context.addToCurrentLabel(SingleDataTransfer.LDR(RegisterOperand.R0, new
        ImmediateOperand<>(0).withPrefixSymbol("=")));
    context.addToCurrentLabel(Stack.POP(RegisterOperand.PC));
    context.addToCurrentLabel(new LoadLiterals());
  }
}
