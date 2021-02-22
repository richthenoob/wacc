package ic.doc.frontend.nodes;

import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.Operand;
import ic.doc.backend.Instructions.OperandType;
import ic.doc.backend.Instructions.Stack;
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
  public void translate(
      List<Label<Instruction>> instructionLabels,
      List<Label<Data>> dataLabels) {
    // SAMPLE INSTRUCTIONS ONLY. Remove when implemented
    Stack stack = new Stack(true, new Operand(OperandType.REG,1));
    Label<Instruction> inst = new Label<>("main");
    inst.addToBody(stack);
    instructionLabels.add(inst);
  }
}
