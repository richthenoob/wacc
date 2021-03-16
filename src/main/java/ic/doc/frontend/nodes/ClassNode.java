package ic.doc.frontend.nodes;

import ic.doc.backend.Context;
import ic.doc.backend.Label;
import ic.doc.backend.VirtualTable;
import ic.doc.backend.instructions.Instruction;
import ic.doc.backend.instructions.LoadLiterals;
import ic.doc.backend.instructions.Move;
import ic.doc.backend.instructions.SingleDataTransfer;
import ic.doc.backend.instructions.Stack;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.frontend.identifiers.ClassIdentifier;
import ic.doc.frontend.identifiers.FunctionIdentifier;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ClassType;
import ic.doc.frontend.types.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.v4.runtime.ParserRuleContext;

public class ClassNode extends Node {

  private final String className;
  private final String immediateSuperclass;
  private final SymbolTable classSymbolTable;
  private final ParamListNode classFields;
  private final List<FunctionNode> classFunctions;
  private final Map<String, SymbolTable> functionTables;
  private final VirtualTable classVirtualTable;

  public ClassNode(String className,
      SymbolTable classSymbolTable,
      ParamListNode classFields,
      List<FunctionNode> classFunctions,
      String immediateSuperclass) {
    this.className = className;
    this.classSymbolTable = classSymbolTable;
    this.classFields = classFields;
    this.classFunctions = classFunctions;
    this.immediateSuperclass = immediateSuperclass;
    this.functionTables = new HashMap<>();
    this.classVirtualTable = new VirtualTable();
  }

  public String getClassName() {
    return className;
  }

  public Map<String, SymbolTable> getFunctionTables() {
    return functionTables;
  }

  public SymbolTable getClassSymbolTable() {
    return classSymbolTable;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {
    /* Check that there are no repeated field names. */
    Set<String> duplicateFields = new HashSet<>();
    SymbolTable currentSymbolTable = visitor.getCurrentSymbolTable();

    for (ParamNode field : classFields.getParams()) {
      String fieldName = field.getInput();
      if (!duplicateFields.add(fieldName)) {
        visitor.getSemanticErrorList()
            .addScopeException(ctx, true, field.getType().toString(),
                fieldName);
      }
    }

    /* Check for class inheritance cycles. */
    if (ClassType.isSubclassOf(className, className, currentSymbolTable)) {
      visitor.getSemanticErrorList().addException(ctx,
          "Cyclic inheritance detected for class " + className);
      return;
    }

    /* Attempt to unify super class symbol table and this one if it exists. */
    String currentSuperClass = immediateSuperclass;
    while (!currentSuperClass.isEmpty()) {
      SymbolKey superclassSymbolKey = new SymbolKey(currentSuperClass,
          KeyTypes.CLASS);
      ClassIdentifier superclassIdentifier = (ClassIdentifier) currentSymbolTable
          .lookupAll(superclassSymbolKey);
      SymbolTable superclassSymbolTable = superclassIdentifier
          .getClassSymbolTable();

      unifySymbolTables(classSymbolTable, superclassSymbolTable,
          currentSuperClass, classVirtualTable);

      currentSuperClass = superclassIdentifier.getImmediateSuperClass();
    }

    /* Note:
     * Checking of repeated function names
     * is handled when we call declareFunction() in visitClass_().
     * Checking of repeated class names is handled when we call
     * declareClass in visitProg. */
  }

  /* Adds all fields in the super class to the class, adding a semantic error
   * if there are overlaps.
   * Also adds any functions that are in super class but not in class. If a
   * function is in super class and in class, then keep the class's function. */
  private void unifySymbolTables(SymbolTable classSymbolTable,
      SymbolTable superclassSymbolTable, String superclassName,
      VirtualTable virtualTable) {

    for (Map.Entry<SymbolKey, Identifier> entry :
        superclassSymbolTable.getDictionary().entrySet()) {

      SymbolKey entryKey = entry.getKey();
      String entryName = entryKey.getName();
      Identifier entryIdentifier = entry.getValue();
      Type entryType = entryIdentifier.getType();

      /* Don't add a super class's field if it already exists. */
      if (entryIdentifier instanceof VariableIdentifier) {
        if (classSymbolTable.lookup(entryKey) == null) {
          classFields.addParam(new ParamNode(entryType, entryName));
          classSymbolTable.add(entryKey, entryIdentifier);
        }
      }

      /* Don't add super class's function definition to this class
       * if it already exists. */
      if (entryIdentifier instanceof FunctionIdentifier) {
        if (classSymbolTable.lookup(entryKey) == null) {
          virtualTable.addClassFunction(entryName, superclassName);
          classSymbolTable.add(entryKey, entryIdentifier);
        }
      }
    }
  }

  @Override
  public void translate(Context context) {

    // Add virtualTable to context for code generation

    /* Add class init label. */
    context.setScope(classSymbolTable);

    context.setCurrentClass(className);
    classFields.translateHelper(context, true);
    createClassInit(context);

    for (FunctionNode node : classFunctions) {
      functionTables.put(node.getFuncName(), node.getFuncSymbolTable());
      node.translateParameters(context);
    }

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

    /* Store zero into R1, so that we can write this to the relevant addresses later on. */
    ImmediateOperand<Integer> zeroOperand = new ImmediateOperand<>(0)
        .withPrefixSymbol("#");
    classInitLabel.addToBody(Move.MOV(RegisterOperand.R1, zeroOperand));

    /* Go through each field and make sure values are set to 0. */
    for (ParamNode field : classFields.getParams()) {

      /* First find offset of field within class. */
      SymbolKey fieldKey = new SymbolKey(field.getInput(), KeyTypes.VARIABLE);
      VariableIdentifier fieldIdentifier =
          (VariableIdentifier) classSymbolTable.lookup(fieldKey);
      int offset = fieldIdentifier.getOffsetStack(classSymbolTable, fieldKey);

      /* Store contents of r1 (previously set to 0) to
       * address of r0 (instance address) + offset of field within address
       * STR r1, [r0], #offset */
      PreIndexedAddressOperand addressOperand = new PreIndexedAddressOperand(
          RegisterOperand.R0)
          .withExpr(new ImmediateOperand<>(offset).withPrefixSymbol("#"));
      SingleDataTransfer initialiseFieldZeroInst =
          SingleDataTransfer.STR(RegisterOperand.R1, addressOperand);

      int fieldSize = field.getType().getVarSize();

      /* Only store 1 byte if writing to char or bool type field.
       * No conditions needed if fieldSize == 4. */
      if (fieldSize == 1) {
        initialiseFieldZeroInst = initialiseFieldZeroInst.withCond("B");
      } else if (fieldSize != 4) {
        throw new IllegalStateException(
            "Field \"" + field.getType() + "\" in class " + className
                + " has invalid field size!");
      }

      classInitLabel.addToBody(initialiseFieldZeroInst);
    }

    classInitLabel.addToBody(Stack.POP(RegisterOperand.PC));
    classInitLabel.addToBody(new LoadLiterals());
  }
}
