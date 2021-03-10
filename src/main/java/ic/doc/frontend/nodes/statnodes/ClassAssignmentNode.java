package ic.doc.frontend.nodes.statnodes;

import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.SymbolTable;

public class ClassAssignmentNode extends AssignmentNode {

  public ClassAssignmentNode(ExprNode lhs,
      ExprNode rhs, boolean isDeclaration,
      SymbolTable symbolTable) {
    super(lhs, rhs, isDeclaration, symbolTable);
  }

  // TODO: fill in class
}
