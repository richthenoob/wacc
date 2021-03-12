package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.frontend.identifiers.ClassIdentifier;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ErrorType;
import org.antlr.v4.runtime.ParserRuleContext;

public class ClassVariableNode extends VariableNode {

  /* Corresponds to identifier for class declaration. i.e. id in "class id {...}" */
  public ClassVariableNode(String className) {
    super(className);
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Checks if class was defined in symbol table */
    Identifier id = checkIdentifier(visitor, ctx,
        getName(), KeyTypes.CLASS, "Class");
    if (id != null) {
      setType(id.getType());
    }
  }

  @Override
  public void translate(Context context) {

  }

  @Override
  public String getInput() {
    return getName();
  }
}
