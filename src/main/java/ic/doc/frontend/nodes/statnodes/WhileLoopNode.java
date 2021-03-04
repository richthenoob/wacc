package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.Branch;
import ic.doc.backend.Instructions.DataProcessing;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.BoolType;
import ic.doc.frontend.types.ErrorType;
import org.antlr.v4.runtime.ParserRuleContext;

public class WhileLoopNode extends StatNode {

  private final ExprNode cond;
  private final StatNode body;
  private final SymbolTable bodySymbolTable;

  public WhileLoopNode(ExprNode cond, StatNode body,
      SymbolTable bodySymbolTable) {
    this.cond = cond;
    this.body = body;
    this.bodySymbolTable = bodySymbolTable;
  }

  public ExprNode getCond() {
    return cond;
  }

  public StatNode getBody() {
    return body;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Expr must be a bool.
     * There is no need to print the type error message
     * if the condition was not present in the symbol table
     * - i.e. if the type was Error. */
    if (!(cond.getType() instanceof BoolType || cond
        .getType() instanceof ErrorType)) {
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx, cond.getInput(), "BOOL", cond.getType().toString(), "",
              "'while' condition");
    }
  }

  @Override
  public void translate(Context context) {
    /* Scoping sanity check. */
    assert (context.getCurrentSymbolTable()
        .equals(bodySymbolTable.getParentSymbolTable()));

    /* Set up labels. */
    String boolCondName = context.getNextAnonymousLabel();
    String bodyName = context.getNextAnonymousLabel();
    Label<Instruction> boolCondLabel = new Label<>(boolCondName);
    Label<Instruction> bodyLabel = new Label<>(bodyName);

    /* Add the body label first, then jump to bool label. */
    context.getInstructionLabels().add(bodyLabel);
    context.addToCurrentLabel(Branch.B(boolCondName));

    /* Evaluate statement. */
    context.setCurrentLabel(bodyLabel);
    context.setScope(bodySymbolTable);
    body.translate(context);
    context.restoreScope();

    /* Evaluate bool condition. */
    context.getInstructionLabels().add(boolCondLabel);
    context.setCurrentLabel(boolCondLabel);
    cond.translate(context);

    /* Only jump back to while body if boolean was evaluated to be true. */
    DataProcessing compareInst = DataProcessing
        .CMP(cond.getRegister(), new ImmediateOperand(1).withPrefixSymbol("#"));
    Branch jumpToBodyInst = Branch.BEQ(bodyName);
    boolCondLabel.addToBody(compareInst);
    boolCondLabel.addToBody(jumpToBodyInst);
    context.freeRegister(cond.getRegister().getValue());
  }
}
