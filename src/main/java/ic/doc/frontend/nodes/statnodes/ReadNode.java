package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.Instruction;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.backend.PredefinedFunctions;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.nodes.exprnodes.VariableNode;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.CharType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.IntType;
import ic.doc.frontend.types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

import static ic.doc.backend.instructions.Branch.BL;
import static ic.doc.backend.instructions.DataProcessing.ADD;
import static ic.doc.backend.instructions.Move.MOV;
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

    if(expr instanceof VariableNode) {
      /* If expression is a variable, get stack offset corresponding to variable from symbol table
       * and move stack pointer accordingly. */
      String varName = ((VariableNode) expr).getName();
      SymbolKey key = new SymbolKey(varName, false);
      VariableIdentifier id = (VariableIdentifier) context.getCurrentSymbolTable().lookupAll(key);
      int offset = id.getOffsetStack(context.getCurrentSymbolTable(), key);
      curr.addToBody(ADD(reg, RegisterOperand.SP, new ImmediateOperand<>(offset).withPrefixSymbol("#")));
    }

    /* Predefined read functions need contents of register to be moved to R0. */
    curr.addToBody(MOV(RegisterOperand.R0, reg));
    /* Branch to appropriate predefined functions based on type. */
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
