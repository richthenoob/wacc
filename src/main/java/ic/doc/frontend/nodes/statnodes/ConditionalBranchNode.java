package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.Branch;
import ic.doc.backend.Instructions.DataProcessing;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
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
              ctx, cond.getInput(), "BOOL", cond.getType().toString(), "", "'if' condition");
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

    /* Test boolean condition. */
    Label<Instruction> currentLabel = context.getCurrentLabel();
    currentLabel
        .addToBody(DataProcessing.CMP(register, new ImmediateOperand(0)));
    currentLabel.addToBody(Branch.BEQ(falseBodyName));

    // TODO: account for scopes here

    /* Evaluate true body. */
    // set currentSymbolTable in context to symbol table of true branch
    // set currentLabel in context to current label of true branch
    trueBody.translate(context);
    context.getCurrentLabel().addToBody(Branch.B(nextBodyName));
    // reset any stack usages
    // restore currentSymbolTable and currentLabel

    /* Evaluate false body. */
    // set currentSymbolTable in context to symbol table of true branch
    // set currentLabel in context to current label of true branch
    context.setCurrentLabel(falseBodyLabel);
    context.getInstructionLabels().add(falseBodyLabel);
    falseBody.translate(context);
    // reset any stack usages
    // restore currentSymbolTable and currentLabel

    /* Set up next body label. */
    context.setCurrentLabel(nextBodyLabel);
    context.getInstructionLabels().add(nextBodyLabel);
  }
}
