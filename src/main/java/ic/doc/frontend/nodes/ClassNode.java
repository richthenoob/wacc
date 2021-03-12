package ic.doc.frontend.nodes;

import ic.doc.backend.Context;

import ic.doc.backend.Label;
import ic.doc.backend.instructions.Instruction;
import ic.doc.backend.instructions.LoadLiterals;
import ic.doc.backend.instructions.Stack;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.antlr.v4.runtime.ParserRuleContext;

public class ClassNode extends Node {

  private final String className;
  private final SymbolTable classSymbolTable;
  private final ParamListNode classFields;
  private final List<FunctionNode> classFunctions;

  public ClassNode(String className,
      SymbolTable classSymbolTable,
      ParamListNode classFields,
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

    for (ParamNode field : classFields.getParams()) {
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

    /* Add class init label. */
    context.setScope(classSymbolTable);
    createClassInit(context);

    classFields.translate(context);

    context.setCurrentClass(className);
    for (FunctionNode node : classFunctions) {
      node.translate(context);
    }
    context.setCurrentClass("");
  }

  /* Initialise a class and make sure all values are set to 0.
   * Assume that when this function is called, the address of the class
   * is in r0. */
  private void createClassInit(Context context) {
    Label<Instruction> classInitLabel = new Label<>("c_" + className + "_init");
    context.getInstructionLabels().add(classInitLabel);
    classInitLabel.addToBody(Stack.PUSH(RegisterOperand.LR));



    classInitLabel.addToBody(Stack.POP(RegisterOperand.PC));
    classInitLabel.addToBody(new LoadLiterals());
  }
}
