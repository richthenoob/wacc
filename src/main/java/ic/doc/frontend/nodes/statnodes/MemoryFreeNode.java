package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.Instruction;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.backend.PredefinedFunctions;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ArrayType;
import ic.doc.frontend.types.ClassType;
import ic.doc.frontend.types.PairType;

import ic.doc.frontend.types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

import static ic.doc.backend.instructions.Branch.BL;
import static ic.doc.backend.instructions.Move.MOV;

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
    if (!(exprNode.getType() instanceof PairType || exprNode.getType() instanceof ArrayType
        || exprNode.getType() instanceof ClassType)) {
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx,
              exprNode.getInput(),
              "PAIR, ARRAY or CLASS",
              exprNode.getType().toString(),
              "",
              " 'free' statement");
    }
  }

  @Override
  public void translate(Context context) {
    exprNode.translate(context);
    RegisterOperand reg = exprNode.getRegister();
    Label<Instruction> curr = context.getCurrentLabel();

    /* Move previous contents of register to R0. */
    curr.addToBody(MOV(RegisterOperand.R0, reg));

    PredefinedFunctions.addThrowRuntimeErrorFunction(context);

    Type exprType = exprNode.getType();
    /* Branch to predefined functions according to type of expr. */
    if(exprType instanceof PairType){
      PredefinedFunctions.addFreePairFunction(context);
      curr.addToBody(BL(PredefinedFunctions.FREE_PAIR_FUNC));
    } else if(exprType instanceof ArrayType){
      PredefinedFunctions.addFreeArrayFunction(context);
      curr.addToBody(BL(PredefinedFunctions.FREE_ARRAY_FUNC));
    } else if(exprType instanceof ClassType){
      PredefinedFunctions.addFreeClassFunction(context);
      curr.addToBody(BL(PredefinedFunctions.FREE_CLASS_FUNC));
    }

    /* Register occupied by expression is freed. */
    context.freeRegister(reg.getValue());
  }
}
