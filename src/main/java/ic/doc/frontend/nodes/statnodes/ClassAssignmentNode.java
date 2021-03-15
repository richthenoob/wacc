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

  public ClassAssignmentNode(ExprNode lhs,
      ExprNode rhs, boolean isDeclaration,
      SymbolTable symbolTable, ClassVariableNode classIdent) {
    super(lhs, rhs, isDeclaration, symbolTable);
    this.classIdent = classIdent;
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

    /* Find classNode. */
    String className = classIdent.getName();
    SymbolKey classKey = new SymbolKey(className, KeyTypes.CLASS);
    ClassIdentifier classIdentifier = (ClassIdentifier) currentSymbolTable
        .lookupAll(classKey);
    SymbolTable classSymbolTable = classIdentifier.getClassSymbolTable();

    /* Subtract SP by 4 to make space for new instance variable. */
    context.addToCurrentLabel(
        DataProcessing.SUB(
            RegisterOperand.SP,
            RegisterOperand.SP,
            new ImmediateOperand<>(Context.SIZE_OF_ADDRESS).withPrefixSymbol("#")));

    /* Malloc space on heap for class instance. */
    context.addToCurrentLabel(Move.MOV(RegisterOperand.R0,
        new ImmediateOperand<>(classSymbolTable.getParametersSizeInBytes())
            .withPrefixSymbol("#")));
    context.addToCurrentLabel(Branch.BL("malloc"));

    /* Initialise class. */
    context.addToCurrentLabel(Branch.BL("c_" + className + "_init"));

    /* Write address of class instance to variable on stack. */
    context.addToCurrentLabel(SingleDataTransfer.STR(RegisterOperand.R0,
        new PreIndexedAddressOperand(RegisterOperand.SP)));
    currentSymbolTable.incrementOffset(4);
    currentSymbolTable.incrementTableSizeInBytes(4);

    /* Set class instance to 'activated' in symbol table. */
    SymbolKey classInstanceKey = new SymbolKey(
        ((VariableNode) getLhs()).getName(), KeyTypes.VARIABLE);
    VariableIdentifier classInstanceIdentifier = (VariableIdentifier) currentSymbolTable.lookupAll(classInstanceKey);
    classInstanceIdentifier.setActivated();
  }
}