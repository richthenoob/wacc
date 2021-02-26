package ic.doc.frontend.nodes;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.Stack;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.statnodes.StatNode;
import ic.doc.frontend.semantics.Visitor;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class ProgNode extends Node {

  private final List<FunctionNode> functions;
  private final StatNode stat;

  public ProgNode(List<FunctionNode> functions, StatNode stat) {
    this.functions = functions;
    this.stat = stat;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public void translate(Context context) {

    /* Add all function labels first. */
    for (FunctionNode node : functions) {
      node.translate(context);
    }

    /* Create main label, the entry point of the program. */
    Label<Instruction> inst = new Label<>("main");
    inst.addToBody(Stack.PUSH(RegisterOperand.LR));
    context.getInstructionLabels().add(inst);
    context.setCurrentLabel(inst);

    /* Translate rest of program. */
    stat.translate(context);

    /* Pass control back to kernel code that called it. */
    context.getCurrentLabel().addToBody(Stack.POP(RegisterOperand.PC));
  }
}
