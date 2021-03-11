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
import ic.doc.frontend.types.Type;
import org.antlr.v4.runtime.ParserRuleContext;

public class ClassAssignmentNode extends AssignmentNode {

  private final ClassVariableNode classIdentLHS;

  public ClassAssignmentNode(ExprNode lhs,
      ExprNode rhs, boolean isDeclaration,
      SymbolTable symbolTable, ClassVariableNode classIdentLHS) {
    super(lhs, rhs, isDeclaration, symbolTable);
    this.classIdentLHS = classIdentLHS;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Check that class type declared matches constructor name.
     * e.g. for ClassA a = new ClassA, check that type ClassA = ClassA */

    assert (classIdentLHS.getType() != null);
    assert (getRhs().getType() != null);

    String classIdentLHSName = classIdentLHS.getInput();
    String classIdentRHSName = getRhs().getInput();
    Type classIdentLHSType = classIdentLHS.getType();
    Type classIdentRHSType = getRhs().getType();
    String classInstanceName = getLhs().getInput();

    /* Check LHS and RHS classes are both of ClassType. */
    if (!(classIdentLHSType instanceof ClassType)) {
      visitor.getSemanticErrorList().addTypeException(ctx,
          classIdentLHSName, "CLASS " + classIdentLHSName,
          classIdentLHSType.toString(), "");
    }
    if (!(classIdentRHSType instanceof ClassType)) {
      visitor.getSemanticErrorList().addTypeException(ctx,
          classIdentRHSName, "CLASS " + classIdentRHSName,
          classIdentRHSType.toString(), "");
    }

    /* Check that LHS and RHS classes are the same class (same name). */
    if (!Type
        .checkTypeCompatibility(classIdentLHSType, classIdentRHSType)) {
      visitor.getSemanticErrorList().addTypeException(ctx,
          classIdentRHSName, classIdentLHSType.toString() ,
          classIdentRHSType.toString(), "");
    }

    /* Check that class instance hasn't been declared before in this scope. */
    SymbolKey key = new SymbolKey(classInstanceName, KeyTypes.CLASS);
    if (getSymbolTable().lookup(key) != null) {
      visitor.getSemanticErrorList()
          .addScopeException(ctx, true, "Variable", classInstanceName);
    } else {
      Type classType = classIdentLHS.getType();
      getLhs().setType(classType);
      getSymbolTable().add(key, new VariableIdentifier(classType));
    }
  }

  @Override
  public void translate(Context context) {

  }

}
