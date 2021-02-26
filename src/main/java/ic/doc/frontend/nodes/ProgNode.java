package ic.doc.frontend.nodes;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.Operand;
import ic.doc.backend.Instructions.Stack;
import ic.doc.backend.Instructions.Instruction;
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
    // SAMPLE INSTRUCTIONS ONLY. Remove when implemented
    Stack stack = Stack.PUSH(Operand.REG(1));
    Label<Instruction> inst = new Label<>("main");
    inst.addToBody(stack);
    context.getInstructionLabels().add(inst);
  }
}
