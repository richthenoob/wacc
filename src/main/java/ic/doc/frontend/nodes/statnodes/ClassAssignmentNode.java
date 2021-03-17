package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.Branch;
import ic.doc.backend.instructions.DataProcessing;
import ic.doc.backend.instructions.Move;
import ic.doc.backend.instructions.SingleDataTransfer;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.frontend.identifiers.ClassIdentifier;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.nodes.exprnodes.ClassVariableNode;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.nodes.exprnodes.VariableNode;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ClassType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

public class ClassAssignmentNode extends AssignmentNode {

  private final ClassVariableNode classIdent;
  private final boolean isNewClass;

  public ClassAssignmentNode(ExprNode lhs,
      ExprNode rhs, boolean isDeclaration,
      SymbolTable symbolTable, ClassVariableNode classIdent,
      boolean isNewClass) {
    super(lhs, rhs, isDeclaration, symbolTable);
    this.classIdent = classIdent;
    this.isNewClass = isNewClass;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Check that class type declared RHS type.
     * e.g. for ClassA a = new ClassA, check that type ClassA = ClassA
     *      for ClassA a2 = a, check that type a = ClassA */

    assert (classIdent.getType() != null);
    assert (getRhs().getType() != null);

    String classIdentName = classIdent.getInput();
    String classInstanceName = getLhs().getInput();
    String rhsName = getRhs().getInput();
    Type classIdentType = classIdent.getType();
    Type rhsType = getRhs().getType();

    /* Check classIdent and RHS are both of ClassType. */
    if (!(classIdentType instanceof ClassType)
        && !(classIdentType instanceof ErrorType)) {
      visitor.getSemanticErrorList().addTypeException(ctx,
          classIdentName, "CLASS " + classIdentName,
          classIdentType.toString(), "");
    }
    if (!(rhsType instanceof ClassType)
        && !(rhsType instanceof ErrorType)) {
      visitor.getSemanticErrorList().addTypeException(ctx,
          rhsName, "CLASS " + rhsName,
          rhsType.toString(), "");
    }

    /* Check that classIdent and RHS are the same class (same name). */
    if (!Type.checkTypeCompatibility(rhsType, classIdentType,
        visitor.getCurrentSymbolTable())
        && !(classIdentType instanceof ErrorType)
        && !(rhsType instanceof ErrorType)) {
      visitor.getSemanticErrorList().addTypeException(ctx,
          rhsName, classIdentType.toString(),
          rhsType.toString(), "");
    }

    /* Check that class instance hasn't been declared before in this scope. */
    SymbolKey key = new SymbolKey(classInstanceName, KeyTypes.VARIABLE);
    if (getSymbolTable().lookup(key) != null) {
      visitor.getSemanticErrorList()
          .addScopeException(ctx, true, "Variable", classInstanceName);
    } else {
      Type classType = classIdent.getType();
      getLhs().setType(classType);
      getSymbolTable().add(key, new VariableIdentifier(classType));
    }
  }

  @Override
  public void translate(Context context) {

    /* Sanity checks. */
    assert (getLhs() instanceof VariableNode);
    assert (getLhs().getType() instanceof ClassType);
    SymbolTable currentSymbolTable = context.getCurrentSymbolTable();

    /* Special case of e class1 = class2; where class2 is a subclass of or same
     * class as class1. Then the type of class1 needs to be class2's type,
     * not the originally declared one. */
    String lhsName = ((VariableNode) getLhs()).getName();
    SymbolKey lhsKey = new SymbolKey(lhsName, KeyTypes.VARIABLE);
    VariableIdentifier lhsId = (VariableIdentifier) currentSymbolTable
        .lookupAll(lhsKey);

    String className;
    if (isNewClass) {
      /* e.g. e class1 = new class1(); */
      className = classIdent.getName();
    } else {
      /* e.g. e class1 = class2 */
      assert (getRhs() instanceof VariableNode);
      Type newLHSType = getRhs().getType();
      getLhs().setType(newLHSType);
      lhsId.setType(newLHSType);
      className = ((ClassType) newLHSType).getClassName();
    }

    /* Find classNode. */
    SymbolKey classKey = new SymbolKey(className, KeyTypes.CLASS);
    ClassIdentifier classIdentifier = (ClassIdentifier) currentSymbolTable
        .lookupAll(classKey);
    SymbolTable classSymbolTable = classIdentifier.getClassSymbolTable();

    /* Subtract SP by 4 to make space for new instance variable. */
    context.addToCurrentLabel(
        DataProcessing.SUB(
            RegisterOperand.SP,
            RegisterOperand.SP,
            new ImmediateOperand<>(Context.SIZE_OF_ADDRESS)
                .withPrefixSymbol("#")));
    currentSymbolTable.incrementOffset(4);
    currentSymbolTable.incrementTableSizeInBytes(4);

    /* Here, we only malloc space if a new keyword is detected. */
    if (isNewClass) {
      /* Malloc space on heap for class instance. */
      context.addToCurrentLabel(Move.MOV(RegisterOperand.R0,
          new ImmediateOperand<>(classSymbolTable.getParametersSizeInBytes())
              .withPrefixSymbol("#")));
      context.addToCurrentLabel(Branch.BL("malloc"));

      /* Initialise class. */
      context.addToCurrentLabel(Branch.BL("c_" + className + "_init"));
    } else {
      /* Move address of originally declared class instance into R0. */
      SymbolKey rhsKey = new SymbolKey(getRhs().getInput(), KeyTypes.VARIABLE);
      VariableIdentifier rhsId = (VariableIdentifier) currentSymbolTable
          .lookupAll(rhsKey);

      context.addToCurrentLabel(SingleDataTransfer.LDR(RegisterOperand.R0,
          new PreIndexedAddressOperand(RegisterOperand.SP)
              .withExpr(new ImmediateOperand<>(
                  rhsId.getOffsetStack(currentSymbolTable, rhsKey))
                  .withPrefixSymbol("#"))));
    }

    /* Write address of class instance to variable on stack from R0. */
    context.addToCurrentLabel(SingleDataTransfer.STR(RegisterOperand.R0,
        new PreIndexedAddressOperand(RegisterOperand.SP)));

    /* Set class instance to 'activated' in symbol table. */
    SymbolKey classInstanceKey = new SymbolKey(
        ((VariableNode) getLhs()).getName(), KeyTypes.VARIABLE);
    VariableIdentifier classInstanceIdentifier = (VariableIdentifier) currentSymbolTable
        .lookupAll(classInstanceKey);
    classInstanceIdentifier.setActivated();
  }
}