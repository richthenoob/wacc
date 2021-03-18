package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.DataProcessing;
import ic.doc.backend.instructions.SingleDataTransfer;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.frontend.identifiers.ClassIdentifier;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ClassType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

public class ClassFieldVariableNode extends VariableNode {

  private final String classInstance;
  private final String varName;

  /* Corresponds to references to objects in classes. i.e. c.x in "int j = c.x" */
  public ClassFieldVariableNode(String classInstance, String identifier) {
    super(classInstance + "." + identifier);
    this.classInstance = classInstance;
    this.varName = identifier;
  }

  public String getClassInstance() {
    return classInstance;
  }

  public String getVarName() {
    return varName;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Checks if class instance was defined in symbol table */
    Identifier classVarId = checkIdentifier(visitor, ctx,
        getClassInstance(), KeyTypes.VARIABLE, "Class instance");
    if (classVarId == null) {
      return;
    }

    /* Checks if class was defined in symbol table */
    Type classType = classVarId.getType();
    String classGeneralName = ((ClassType) classType).getClassName();
    Identifier classId = checkIdentifier(visitor, ctx,
        classGeneralName, KeyTypes.CLASS, "Class");
    if (classId == null) {
      return;
    }

    /* Checks if variable was defined in symbol table for class */
    SymbolTable classSymbolTable = ((ClassIdentifier) classId)
        .getClassSymbolTable();
    Identifier varId = findIdentifier(classSymbolTable, getVarName(), KeyTypes.VARIABLE);
    if (varId == null) {
      setType(new ErrorType());
      visitor.getSemanticErrorList()
          .addScopeException(ctx, false, "Variable", getName());
      return;
    }

    /* Sets type to type of variable */
    setType(varId.getType());
  }

  @Override
  public void translate(Context context) {
    /* Find instance of class in current symbol table. */
    SymbolTable currentSymbolTable = context.getCurrentSymbolTable();
    SymbolKey classInstanceKey = new SymbolKey(classInstance,
        KeyTypes.VARIABLE);
    VariableIdentifier classInstanceIdentifier =
        (VariableIdentifier) currentSymbolTable.lookupAll(classInstanceKey);

    /* Find class in symbol table that corresponds to this instance. */
    SymbolKey classKey = new SymbolKey(
        ((ClassType) classInstanceIdentifier.getType()).getClassName(),
        KeyTypes.CLASS);
    SymbolTable classSymbolTable = ((ClassIdentifier) currentSymbolTable
        .lookupAll(classKey)).getClassSymbolTable();

    /* Find field in class symbol table. */
    SymbolKey fieldKey = new SymbolKey(varName, KeyTypes.VARIABLE);
    VariableIdentifier fieldIdentifier = (VariableIdentifier) classSymbolTable
        .lookup(fieldKey);

    /* Obtain offsets accordingly.*/
    int classInstanceOffset = classInstanceIdentifier
        .getOffsetStack(currentSymbolTable, classInstanceKey);
    int fieldOffset = fieldIdentifier
        .getOffsetStack(classSymbolTable, fieldKey);

    /* First load address of class instance into a free register.
     * LDR [instanceReg] [sp, #classInstOffset] */
    RegisterOperand classInstanceRegister = new RegisterOperand(
        context.getFreeRegister());
    PreIndexedAddressOperand classInstanceAddrOperand =
        new PreIndexedAddressOperand(RegisterOperand.SP)
            .withExpr(new ImmediateOperand<>(classInstanceOffset)
                .withPrefixSymbol("#"));
    SingleDataTransfer loadClassInstanceInst =
        SingleDataTransfer.LDR(classInstanceRegister, classInstanceAddrOperand);
    context.addToCurrentLabel(loadClassInstanceInst);

    /* Read and load value into new register.
     * LDR [outputReg] [instanceReg, #fieldOffset] */
    RegisterOperand outputRegister = new RegisterOperand(
        context.getFreeRegister());
    PreIndexedAddressOperand addressOperand = new PreIndexedAddressOperand(
        classInstanceRegister)
        .withExpr(new ImmediateOperand<>(fieldOffset).withPrefixSymbol("#"));
    SingleDataTransfer loadValueInst = SingleDataTransfer
        .LDR(outputRegister, addressOperand);

    /* Only load 1 byte if field is char or bool. */
    if (fieldIdentifier.getType().getVarSize() == 1) {
      loadValueInst = loadValueInst.withCond("B");
    }

    /* Actually add instructions to label. */
    context.addToCurrentLabel(loadValueInst);
    setRegister(outputRegister);
    context.freeRegister(classInstanceRegister.getValue());
  }

  /* Use this function if the class field variable occurs on the LHS of
   * an assignment, e.g. c.x = 1
   * This loads the address of the field into a new register. */
  public int translateClassFieldVariableLHS(Context context) {
    /* Find instance of class in current symbol table. */
    SymbolTable currentSymbolTable = context.getCurrentSymbolTable();
    SymbolKey classInstanceKey = new SymbolKey(classInstance,
        KeyTypes.VARIABLE);
    VariableIdentifier classInstanceIdentifier =
        (VariableIdentifier) currentSymbolTable.lookupAll(classInstanceKey);

    /* Find class in symbol table that corresponds to this instance. */
    SymbolKey classKey = new SymbolKey(
        ((ClassType) classInstanceIdentifier.getType()).getClassName(),
        KeyTypes.CLASS);
    SymbolTable classSymbolTable = ((ClassIdentifier) currentSymbolTable
        .lookupAll(classKey)).getClassSymbolTable();

    /* Find field in class symbol table. */
    SymbolKey fieldKey = new SymbolKey(varName, KeyTypes.VARIABLE);
    VariableIdentifier fieldIdentifier = (VariableIdentifier) classSymbolTable
        .lookup(fieldKey);

    /* Obtain offsets accordingly.*/
    int classInstanceOffset = classInstanceIdentifier
        .getOffsetStack(currentSymbolTable, classInstanceKey);
    int fieldOffset = fieldIdentifier
        .getOffsetStack(classSymbolTable, fieldKey);

    /* Load address on heap of class instance into a free register.
     * LDR [instanceReg] [sp, #classInstOffset] */
    RegisterOperand classInstanceRegister = new RegisterOperand(
        context.getFreeRegister());
    PreIndexedAddressOperand classInstanceAddrOperand =
        new PreIndexedAddressOperand(RegisterOperand.SP)
            .withExpr(new ImmediateOperand<>(classInstanceOffset)
                .withPrefixSymbol("#"));
    SingleDataTransfer loadClassInstanceInst =
        SingleDataTransfer.LDR(classInstanceRegister, classInstanceAddrOperand);
    context.addToCurrentLabel(loadClassInstanceInst);

    setRegister(classInstanceRegister);
    return fieldOffset;
  }

  /* Use this function if the class field variable occurs on the LHS of
   * an assignment, e.g. int i = c.x
   * This loads the value of the field into a new register. */
  public void translateClassFieldVariableRHS(Context context) {
    translate(context);
  }

  @Override
  public String getInput() {
    return getName();
  }

}
