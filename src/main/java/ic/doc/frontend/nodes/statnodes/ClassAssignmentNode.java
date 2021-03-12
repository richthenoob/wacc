package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.nodes.exprnodes.ClassVariableNode;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
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
    if (!Type.checkTypeCompatibility(classIdentType, rhsType)
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

  }

}
