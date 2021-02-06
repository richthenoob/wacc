package ic.doc.semantics.StatNodes;

import ic.doc.semantics.ExprNodes.ExprNode;

public class ExitNode extends StatNode {

  private ExprNode exprNode;

  public ExitNode(ExprNode exprNode) {
    this.exprNode = exprNode;
  }

}
