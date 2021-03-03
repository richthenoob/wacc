package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.backend.PredefinedFunctions;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ArrayType;
import ic.doc.frontend.types.PairType;
import java.util.List;

import ic.doc.frontend.types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

import static ic.doc.backend.Instructions.Branch.BL;
import static ic.doc.backend.Instructions.Move.MOV;
import static ic.doc.backend.Instructions.SingleDataTransfer.LDR;
import static ic.doc.backend.Instructions.operands.PreIndexedAddressOperand.PreIndexedAddressZeroOffset;

public class MemoryFreeNode extends StatNode {

  private final ExprNode exprNode;

  public MemoryFreeNode(ExprNode exprNode) {
    this.exprNode = exprNode;
  }

  public ExprNode getExpr() {
    return exprNode;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Expression must be of type 'pair(T1, T2)' or 'T[]' (for some T, T1, T2) */
    if (!(exprNode.getType() instanceof PairType || exprNode.getType() instanceof ArrayType)) {
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx,
              exprNode.getInput(),
              "PAIR or ARRAY",
              exprNode.getType().toString(),
              "",
              " 'free' statement");
    }
  }

  @Override
  public void translate(Context context) {
    /* I don't think we need to translate the exprNode? */
    /* Expr is guaranteed to have type Char or Int from semantic analysis */
    RegisterOperand reg = exprNode.getRegister();
    Label<Instruction> curr = context.getCurrentLabel();
    curr.addToBody(LDR(reg, PreIndexedAddressZeroOffset(RegisterOperand.SP)));
    curr.addToBody(MOV(RegisterOperand.R0, PreIndexedAddressZeroOffset(reg)));
    exprNode.setRegister(RegisterOperand.R0);

    PredefinedFunctions.addThrowRuntimeErrorFunction(context);

    Type exprType = exprNode.getType();
    if(exprType instanceof PairType){
      PredefinedFunctions.addFreePairFunction(context);
      curr.addToBody(BL(PredefinedFunctions.FREE_PAIR_FUNC));
    } else if(exprType instanceof ArrayType){
      PredefinedFunctions.addFreeArrayFunction(context);
      curr.addToBody(BL(PredefinedFunctions.FREE_ARRAY_FUNC));
    }
  }
}