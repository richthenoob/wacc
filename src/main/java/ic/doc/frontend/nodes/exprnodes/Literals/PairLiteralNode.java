package ic.doc.frontend.nodes.exprnodes.Literals;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.AnyType;
import ic.doc.frontend.types.PairType;
import org.antlr.v4.runtime.ParserRuleContext;

import static ic.doc.backend.instructions.Move.MOV;

/* Only pair literal is 'null'. Can match the type of any pair. */
public class PairLiteralNode extends LiteralNode {

  public PairLiteralNode() {
    setType(new PairType(new AnyType(), new AnyType()));
  }

  @Override
  public void check(Visitor symbolTable, ParserRuleContext ctx) {
    /* No checks needed. */
  }

  @Override
  public void translate(Context context) {
    /* Load value of 0 corresponding to null */
    RegisterOperand reg = new RegisterOperand(context.getFreeRegister());
    context.addToCurrentLabel(MOV(reg,new ImmediateOperand<>(0).withPrefixSymbol("#")));
    setRegister(reg);
  }

  @Override
  public String getInput() {
    return "(nil)";
  }
}
