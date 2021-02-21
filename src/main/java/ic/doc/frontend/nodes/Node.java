package ic.doc.frontend.nodes;

import ic.doc.backend.Instructions.Instruction;
import ic.doc.frontend.semantics.Visitor;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class Node {

  /* Function to check if contents of node fulfill semantic rules,
   * and adds a corresponding error message to the visitor's semantic error list
   * if they do not*/
  public abstract void check(Visitor visitor, ParserRuleContext ctx);

  /* Function to translate node contents to language for code generation */
  public abstract List<Instruction> translate();
}
