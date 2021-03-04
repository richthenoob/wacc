package ic.doc.frontend.nodes.exprnodes.Literals;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.*;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.PostIndexedAddressOperand;
import ic.doc.backend.Instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.AnyType;
import ic.doc.frontend.types.BoolType;
import ic.doc.frontend.types.CharType;
import ic.doc.frontend.types.PairType;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

import static ic.doc.backend.Instructions.Move.MOV;

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
    RegisterOperand reg = new RegisterOperand(context.getFreeRegister());
    context.addToCurrentLabel(MOV(reg,new ImmediateOperand<>(0).withPrefixSymbol("#")));
    setRegister(reg);
  }

  @Override
  public String getInput() {
    return "(nil)";
  }
}
