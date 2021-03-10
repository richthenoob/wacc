package ic.doc.frontend.nodes;

import ic.doc.backend.Context;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import java.util.Map;
import org.antlr.v4.runtime.ParserRuleContext;

public class ClassNode extends Node{

  private final String className;
  private final SymbolTable classSymbolTable;
  private final Map<String, ParamNode> classFields;
  private final Map<String, FunctionNode> classFunctions;

  public ClassNode(String className, int sizeOfClass,
      SymbolTable classSymbolTable,
      Map<String, ParamNode> classFields,
      Map<String, FunctionNode> classFunctions) {
    this.className = className;
    this.classSymbolTable = classSymbolTable;
    this.classFields = classFields;
    this.classFunctions = classFunctions;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    // check className not previously declared (symbol table)
  }

  @Override
  public void translate(Context context) {

  }
}
