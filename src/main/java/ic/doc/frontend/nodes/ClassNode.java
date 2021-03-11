package ic.doc.frontend.nodes;

import ic.doc.backend.Context;

import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.antlr.v4.runtime.ParserRuleContext;

public class ClassNode extends Node {

  private final String className;
  private final SymbolTable classSymbolTable;
  private final List<ParamNode> classFields;
  private final List<FunctionNode> classFunctions;

  public ClassNode(String className,
      SymbolTable classSymbolTable,
      List<ParamNode> classFields,
      List<FunctionNode> classFunctions) {
    this.className = className;
    this.classSymbolTable = classSymbolTable;
    this.classFields = classFields;
    this.classFunctions = classFunctions;
  }

  public SymbolTable getClassSymbolTable() {
    return classSymbolTable;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Check that there are no repeated field names. */
    Set<String> duplicateFields = new HashSet<>();

    for (ParamNode field : classFields) {
      String fieldName = field.getInput();
      if (!duplicateFields.add(fieldName)) {
        visitor.getSemanticErrorList()
            .addScopeException(ctx, true, field.getType().toString(), fieldName);
      }
    }

    /* Note:
     * Checking of repeated function names
     * is handled when we call declareFunction() in visitClass_().
     * Checking of repeated class names is handled when we call
     * declareClass in visitProg. */
  }

  @Override
  public void translate(Context context) {

  }
}
