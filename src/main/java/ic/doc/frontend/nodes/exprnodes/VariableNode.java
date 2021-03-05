package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.SingleDataTransfer;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ErrorType;
import org.antlr.v4.runtime.ParserRuleContext;

public class VariableNode extends ExprNode {

  private final String identifier;

  public VariableNode(String identifier) {
    this.identifier = identifier;
  }

  public String getName() {
    return identifier;
  }

  @Override
  public String getInput() {
    return getName();
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    SymbolKey key = new SymbolKey(getName(), false);
    /* Checks if name was defined in symbol table */
    Identifier id = visitor.getCurrentSymbolTable().lookupAll(key);
    if (id == null) {
      setType(new ErrorType());
      visitor.getSemanticErrorList().addScopeException(ctx, false, "Variable", getName());
      return;
    }

    setType(id.getType());
  }

  @Override
  public void translate(Context context) {
    /* Get a free register to be used to store variable. */
    RegisterOperand register = new RegisterOperand(context.getFreeRegister());
    setRegister(register);

    /* Obtain stack offset of value of variable from entry in symbol table. */
    SymbolKey key = new SymbolKey(getName(), false);
    VariableIdentifier id = (VariableIdentifier) context.getCurrentSymbolTable().lookupAll(key);
    int offset = id.getOffsetStack(context.getCurrentSymbolTable(), key);

    /* If type of variable is Char or Bool, it only needs a stack size of 1.
     * Then we need to use LDRSB to load it. */
    String cond = id.getType().getVarSize() == 1 ? "SB" : "";

    context.addToCurrentLabel(
        SingleDataTransfer.LDR(
            register,
            new PreIndexedAddressOperand(
                RegisterOperand.SP)
                .withExpr(new ImmediateOperand<>(offset)
                    .withPrefixSymbol("#")))
            .withCond(cond));
  }
}
