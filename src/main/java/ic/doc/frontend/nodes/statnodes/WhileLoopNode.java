package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.BoolType;
import ic.doc.frontend.types.ErrorType;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class WhileLoopNode extends StatNode {

  private final ExprNode cond;
  private final StatNode body;

  public WhileLoopNode(ExprNode cond, StatNode body) {
    this.cond = cond;
    this.body = body;
  }

  public ExprNode getCond() {
    return cond;
  }

  public StatNode getBody() {
    return body;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Expr must be a bool.
     * There is no need to print the type error message
     * if the condition was not present in the symbol table
     * - i.e. if the type was Error. */
    if (!(cond.getType() instanceof BoolType || cond.getType() instanceof ErrorType)) {
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx, cond.getInput(), "BOOL", cond.getType().toString(), "", "'while' condition");
    }
  }

  @Override
  public void translate(Context context) {
//    Label curr = instructionLabels.get(instructionLabels.size() - 1);
//
//    // Need some kind of labelCount in the context
//    String whileBodyLabel = Integer.toString(instructionLabels.size());


//    String whileBodyLabel = labelCount.toString();
//    labelCount++;
//    CodeGen.main.add(new LabelInstr("L" + whileBodyLabel));
//    curr.addToBody(new Label("L" + whileBodyLabel));
//    if (ST.findSize() != 0) {
//      newScope(statement);
//    } else {
//      statement.translate();
//    }
//    condition.translate();

//    CodeGen.main.add(new CMP(getRegister(), new ImmValue(1)));
//    CodeGen.main.add(new Branch("EQ", "L" + whileBodyLabel));
  }
}
