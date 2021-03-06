package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.Branch;
import ic.doc.backend.instructions.DataProcessing;
import ic.doc.backend.instructions.Instruction;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.BoolType;
import org.antlr.v4.runtime.ParserRuleContext;

public class ConditionalBranchNode extends StatNode {

  private final ExprNode cond;
  private final StatNode trueBody;
  private final StatNode falseBody;
  private final SymbolTable trueBodySymbolTable;
  private final SymbolTable falseBodySymbolTable;

  public ConditionalBranchNode(
      ExprNode cond,
      StatNode trueBody,
      StatNode falseBody,
      SymbolTable trueBodySymbolTable,
      SymbolTable falseBodySymbolTable) {
    this.cond = cond;
    this.trueBody = trueBody;
    this.falseBody = falseBody;
    this.trueBodySymbolTable = trueBodySymbolTable;
    this.falseBodySymbolTable = falseBodySymbolTable;
  }

  public ExprNode getCond() {
    return cond;
  }

  public StatNode getTrueBody() {
    return trueBody;
  }

  public StatNode getFalseBody() {
    return falseBody;
  }

  public SymbolTable getTrueBodySymbolTable() {
    return trueBodySymbolTable;
  }

  public SymbolTable getFalseBodySymbolTable() {
    return falseBodySymbolTable;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Expr must be a bool */
    if (!(cond.getType() instanceof BoolType)) {
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx, cond.getInput(), "BOOL", cond.getType().toString(), "",
              "'if' condition");
    }
  }

  @Override
  public void translate(Context context) {
    String falseBodyName = context.getNextAnonymousLabel();
    String nextBodyName = context.getNextAnonymousLabel();
    Label<Instruction> falseBodyLabel = new Label<>(falseBodyName);
    Label<Instruction> nextBodyLabel = new Label<>(nextBodyName);

    /* Evaluate boolean condition. */
    cond.translate(context);
    RegisterOperand register = cond.getRegister();
    context.freeRegister(register.getValue());

    /* Test boolean condition. */
    context.addToCurrentLabel(DataProcessing.CMP(register,
        new ImmediateOperand<>(0).withPrefixSymbol("#")));
    context.addToCurrentLabel(Branch.BEQ(falseBodyName));

    /* Make sure that the symbol tables were well formed in front end.*/
    assert (trueBodySymbolTable.getParentSymbolTable()
        .equals(context.getCurrentSymbolTable()));
    assert (falseBodySymbolTable.getParentSymbolTable()
        .equals(context.getCurrentSymbolTable()));

    /* Evaluate true body. */
    context.setScope(trueBodySymbolTable);
    trueBody.translate(context);
    context.restoreScope();
    context.addToCurrentLabel(Branch.B(nextBodyName));

    /* Evaluate false body. */
    context.setCurrentLabel(falseBodyLabel);
    context.getInstructionLabels().add(falseBodyLabel);
    context.setScope(falseBodySymbolTable);
    falseBody.translate(context);
    context.restoreScope();

    /* Set up next body label. */
    context.setCurrentLabel(nextBodyLabel);
    context.getInstructionLabels().add(nextBodyLabel);
  }
}
