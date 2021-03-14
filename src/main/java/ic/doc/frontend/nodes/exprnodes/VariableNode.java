package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.SingleDataTransfer;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.SymbolTable;
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
    SymbolKey key = new SymbolKey(getName(), KeyTypes.VARIABLE);
    /* Checks if name was defined in symbol table */
    Identifier id = visitor.getCurrentSymbolTable().lookupAll(key);
    if (id == null) {
      setType(new ErrorType());
      visitor.getSemanticErrorList()
          .addScopeException(ctx, false, "Variable", getName());
      return;
    }

    setType(id.getType());
  }

  @Override
  public void translate(Context context) {
    SymbolTable currentSymbolTable = context.getCurrentSymbolTable();

    /* Get a free register to be used to store variable. */
    RegisterOperand register = new RegisterOperand(context.getFreeRegister());
    setRegister(register);

    /* Obtain stack offset of value of variable from entry in symbol table. */
    SymbolKey key = new SymbolKey(getName(), KeyTypes.VARIABLE);
    VariableIdentifier id = (VariableIdentifier) currentSymbolTable
        .lookupAll(key);
    int offset = id.getOffsetStack(context.getCurrentSymbolTable(), key);

    /* If type of variable is Char or Bool, it only needs a stack size of 1.
     * Then we need to use LDRSB to load it. */
    String cond = id.getType().getVarSize() == 1 ? "SB" : "";

    RegisterOperand base = RegisterOperand.SP;
    /* If we are in a class context and this variable is a class field
     * (i.e. not on the stack) then we need to load the address of the
     * class into a register so that we can load the field value later. */
    if (!context.getCurrentClass().isEmpty() && id.isClassVariable()) {
      SymbolKey classInstanceKey = new SymbolKey("specialname",
          KeyTypes.VARIABLE);
      VariableIdentifier classInstanceIdentifier
          = (VariableIdentifier) currentSymbolTable.lookupAll(classInstanceKey);

      int classInstanceOffset = classInstanceIdentifier
          .getOffsetStack(currentSymbolTable, classInstanceKey);

      /* Load address of class instance into a new register. */
      base = new RegisterOperand(context.getFreeRegister());
      context.addToCurrentLabel(
          SingleDataTransfer
              .LDR(base, new PreIndexedAddressOperand(RegisterOperand.SP)
                  .withExpr(new ImmediateOperand<>(classInstanceOffset)
                      .withPrefixSymbol("#")))
      );
    }

    context.addToCurrentLabel(
        SingleDataTransfer.LDR(
            register,
            new PreIndexedAddressOperand(
                base)
                .withExpr(new ImmediateOperand<>(offset)
                    .withPrefixSymbol("#")))
            .withCond(cond));

    if (!base.equals(RegisterOperand.SP)) {
      context.freeRegister(base.getValue());
    }
  }
}
