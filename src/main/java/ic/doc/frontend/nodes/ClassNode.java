package ic.doc.frontend.nodes;

import ic.doc.backend.Context;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.Type;
import java.util.Map;
import org.antlr.v4.runtime.ParserRuleContext;

public class ClassNode extends Node {

  private final String className;
  private final SymbolTable classSymbolTable;
  private final Map<String, Type> classFields;
  private final Map<String, FunctionNode> classFunctions;

  public ClassNode(String className,
      SymbolTable classSymbolTable,
      Map<String, Type> classFields,
      Map<String, FunctionNode> classFunctions) {
    this.className = className;
    this.classSymbolTable = classSymbolTable;
    this.classFields = classFields;
    this.classFunctions = classFunctions;
  }

  public SymbolTable getClassSymbolTable() {
    return classSymbolTable;
  }

  public Map<String, Type> getClassFields() {
    return classFields;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    SymbolKey key = new SymbolKey(className, KeyTypes.CLASS);

    if (visitor.getCurrentSymbolTable().lookup(key) != null) {
      visitor.getSemanticErrorList()
          .addScopeException(ctx, true, "Class", className);
    }
  }

  @Override
  public void translate(Context context) {

  }
}
