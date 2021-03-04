package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.backend.PredefinedFunctions;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.nodes.exprnodes.ArrayElementNode;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.nodes.exprnodes.PairElementNode;
import ic.doc.frontend.nodes.exprnodes.VariableNode;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.CharType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.IntType;
import ic.doc.frontend.types.Type;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

import static ic.doc.backend.Instructions.Branch.BL;
import static ic.doc.backend.Instructions.DataProcessing.ADD;
import static ic.doc.backend.Instructions.Move.MOV;
import static ic.doc.backend.PredefinedFunctions.READ_CHAR_FUNC;
import static ic.doc.backend.PredefinedFunctions.READ_INT_FUNC;

public class ReadNode extends StatNode {

  /* A readNode may contain an expression, or it may just be an identifier i.e. variable */
  private final ExprNode expr;

  public ReadNode(ExprNode expr) {
    this.expr = expr;
  }

  public ExprNode getExpr() {
    return expr;
  }

  /* Notes */
//  Readnode is either an IDENT or pairelem/arrayelem
//  If it is an ident, then we need to find the dudes location in the ST
//  (VariableNode) expr
//
//
//
//
//
//        VariableNode lhsVar = ((ArrayElementNode) lhs).getIdentNode();
//      String name = lhsVar.getName();
//      SymbolKey key = new SymbolKey(name, false);
//      VariableIdentifier id = (VariableIdentifier) symbolTable.lookupAll(key);
//      int indexOffset = ((ArrayElementNode) lhs).getIndex(0);
//      offset = new ImmediateOperand<>(true, id.getOffsetStack() + indexOffset);

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    Type type = expr.getType();

    if (type instanceof ErrorType) {
      /* Other errors should have already been caught, no need to print again */
      return;
    }
    if (!(type instanceof CharType || type instanceof IntType)) {
      /* Type of expression should be Char or Int */
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx, expr.getInput(), "CHAR or INT", type.toString(), "", "'read' statement");
    }
  }

  @Override
  public void translate(Context context) {
    expr.translate(context);
    /* Expr is guaranteed to have type Char or Int from semantic analysis */
    Type exprType = expr.getType();
    RegisterOperand reg = expr.getRegister();
    Label<Instruction> curr = context.getCurrentLabel();

//    if(expr instanceof PairElementNode){
//      PairElementNode.PairPosition pos = ((PairElementNode) expr).getPos();
//      /* SND is always at an offset of 4, regardless of type (according to ref compiler) */
//      curr.addToBody(LDR(reg, PreIndexedAddressZeroOffset(reg)));
//    } else if(expr instanceof ArrayElementNode){
//      curr.addToBody(LDR(reg, PreIndexedAddressZeroOffset(reg)));
    /* When expr is translated */
    if(expr instanceof VariableNode) {
      /* Variable node here !*/
      String varName = ((VariableNode) expr).getName();
      SymbolKey key = new SymbolKey(varName, false);
      VariableIdentifier id = (VariableIdentifier) context.getCurrentSymbolTable().lookupAll(key);
      int offset = id.getOffsetStack(context.getCurrentSymbolTable(), key);
      curr.addToBody(ADD(reg, RegisterOperand.SP, new ImmediateOperand<>(offset).withPrefixSymbol("#")));
    }

    curr.addToBody(MOV(RegisterOperand.R0, reg));
    if(exprType instanceof CharType){
      PredefinedFunctions.addReadTypeFunction(context, "char");
      curr.addToBody(BL(READ_CHAR_FUNC));
    } else {
      PredefinedFunctions.addReadTypeFunction(context, "int");
      curr.addToBody(BL(READ_INT_FUNC));
    }
    context.freeRegister(reg.getValue());
  }
}
