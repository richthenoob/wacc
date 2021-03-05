package ic.doc.frontend.nodes.statnodes;

import static ic.doc.backend.Instructions.Move.MOV;
import static ic.doc.backend.Instructions.Stack.POP;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

public class FunctionReturnNode extends StatNode {

  private final ExprNode exprNode;
  private final String functionName;
  private final Type functionType;

  public FunctionReturnNode(ExprNode exprNode, String functionName, Type functionType) {
    this.exprNode = exprNode;
    this.functionName = functionName;
    this.functionType = functionType;
  }

  public ExprNode getExprNode() {
    return exprNode;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Returns can only be present in the body of a non-main function */
    if (functionName.equals("main")) {
      visitor.getSemanticErrorList().addException(ctx, "Cannot return from the global scope.");
    } else if (!exprNode.getType().getClass().equals(functionType.getClass())) {
      /* Type of the expression given to the return statement
       * must match the return type of the function */
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx,
              exprNode.getInput(),
              functionType.toString(),
              exprNode.getType().toString(),
              "",
              "'return' statement");
    }
  }

  @Override
  public void translate(Context context) {
    SymbolTable currentSymbolTable = context.getCurrentSymbolTable();

    /* Evaluate expression and place into Register R0, the register to return
     * values from functions from. */
    exprNode.translate(context);
    context.addToCurrentLabel(MOV(RegisterOperand.R0, exprNode.getRegister()));
    context.freeRegister(exprNode.getRegister().getValue());

    /* Correctly restore scope to function symbol table so that we can
     * properly add to the stack pointer to correctly account for temporary
     * variables declared within the function scope. */
    for (SymbolTable currTable = context.getCurrentSymbolTable();
        currTable.getParentSymbolTable() != null;
        currTable = currTable.getParentSymbolTable()) {
      context.setScope(currTable);
      context.restoreScope();
    }
    context.addToCurrentLabel(POP(RegisterOperand.PC));

    context.setScope(currentSymbolTable);
  }
}
