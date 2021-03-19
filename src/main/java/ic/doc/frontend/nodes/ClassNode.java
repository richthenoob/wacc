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
import ic.doc.backend.instructions.operands.LabelAddressOperand;
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
    this.classVirtualTable = new VirtualTable(className);
  }

  public VirtualTable getClassVirtualTable() {
    return classVirtualTable;
  }

  public String getClassName() {
    return className;
  }

  public SymbolTable getFunctionTable(String functionName) {
    SymbolKey functionKey = new SymbolKey(functionName, KeyTypes.FUNCTION);
    FunctionIdentifier functionIdentifier = (FunctionIdentifier) classSymbolTable
        .lookup(functionKey);
    if (functionIdentifier == null) {
      return null;
    }
    return functionIdentifier.getFunctionSymbolTable();
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
            .addScopeException(ctx, true, "VARIABLE",
                fieldName);
      }
    }

    /* Check if super class exists. */
    SymbolKey superclassKey = new SymbolKey(immediateSuperclass, KeyTypes.CLASS);
    if (!immediateSuperclass.isEmpty() && currentSymbolTable.lookupAll(superclassKey) == null) {
      visitor.getSemanticErrorList().addScopeException(ctx, false, "Class", immediateSuperclass);
      return;
    }

    /* Check for class inheritance cycles. */
    if (ClassType
        .hasSuperclassCycle(className, className, currentSymbolTable)) {
      visitor.getSemanticErrorList().addException(ctx,
          "Cyclic inheritance detected for class " + className);
      return;
    }

    /* Add this class's function to its own virtual table. */
    for (Map.Entry<SymbolKey, Identifier> entry :
        currentSymbolTable.getDictionary().entrySet()) {
      if (entry.getValue() instanceof FunctionIdentifier) {
        classVirtualTable.addClassFunction(entry.getKey().getName(), className);
      }
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
          currentSuperClass, classVirtualTable, visitor, ctx);

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
      VirtualTable virtualTable, Visitor visitor, ParserRuleContext ctx) {

    for (Map.Entry<SymbolKey, Identifier> entry :
        superclassSymbolTable.getDictionary().entrySet()) {

      SymbolKey entryKey = entry.getKey();
      String entryName = entryKey.getName();
      Identifier entryIdentifier = entry.getValue();
      Type entryType = entryIdentifier.getType();

      /* Don't add a super class's field if it already exists. */
      if (entryIdentifier instanceof VariableIdentifier) {
        Identifier varIdentifier = classSymbolTable.lookup(entryKey);
        if (varIdentifier == null) {
          classFields.addParam(new ParamNode(entryType, entryName));
          classSymbolTable.add(entryKey, entryIdentifier.getNewCopy());
        } else if (!Type
            .checkTypeCompatibility(varIdentifier.getType(), entryType,
                classSymbolTable)) {
          visitor.getSemanticErrorList()
              .addTypeException(ctx, entryName,
                  entryType.toString(), varIdentifier.getType().toString(), "");
        }
      }

      /* Don't add super class's function definition to this class
       * if it already exists. */
      if (entryIdentifier instanceof FunctionIdentifier) {
        if (classSymbolTable.lookup(entryKey) == null) {
          virtualTable.addClassFunction(entryName,
              ((FunctionIdentifier) entryIdentifier).getOriginalClass());
          classSymbolTable.add(entryKey, entryIdentifier.getNewCopy());
        }
      }
    }
  }

  @Override
  public void translate(Context context) {
    /* Add this class's virtual table to context, so we can print it later. */
    context.addToVirtualTables(classVirtualTable);

    /* Add class init label. */
    context.setScope(classSymbolTable);

    context.setCurrentClass(className);
    classFields.translateHelper(context, true);
    createClassInit(context);

    for (FunctionNode node : classFunctions) {
      node.translateParameters(context);
    }

    for (FunctionNode node : classFunctions) {
      node.translate(context);
    }
    context.setCurrentClass("");
  }

  /* Initialise a class and make sure all values are set to 0. Set the first
   * element of the class instance to this class' virtual table.
   * Assume that when this function is called, the address of the class
   * is in r0. */
  private void createClassInit(Context context) {
    Label<Instruction> classInitLabel = new Label<>("c_" + className + "_init");
    context.getInstructionLabels().add(classInitLabel);
    classInitLabel.addToBody(Stack.PUSH(RegisterOperand.LR));

    /* Store a pointer to this class's virtual table. Use R1 as a temporary
     * register, recalling that R0 stores the address of our instance. */
    LabelAddressOperand vtableOperand = new LabelAddressOperand(
        VirtualTable.VIRTUAL_TABLE_PREFIX + className);
    classInitLabel
        .addToBody(SingleDataTransfer.LDR(RegisterOperand.R1, vtableOperand));
    classInitLabel.addToBody(SingleDataTransfer.STR(RegisterOperand.R1,
        new PreIndexedAddressOperand(RegisterOperand.R0)));

    classSymbolTable.incrementFunctionParametersSize(4);
    classSymbolTable.incrementOffset(4);

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
