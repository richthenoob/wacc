package ic.doc.backend;

import ic.doc.backend.instructions.DataProcessing;
import ic.doc.backend.instructions.Instruction;
import ic.doc.backend.instructions.Stack;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.frontend.semantics.SymbolTable;

import java.util.*;

public class Context {

  /* The register that corresponds to array index 0. */
  public static final int REGISTER_OFFSET = 4;
  /* Size of address in bytes. Used for stack pointer movements. */
  public static final int SIZE_OF_ADDRESS = 4;
  /* Maximum index of registers that are freely available for use
   * (i.e. registers 4-10, where 4 corresponds to index 0) */
  public static final int MAX_INDEX = 6;


  /* -------------------------------- Registers -------------------------------- */
  /* Array of booleans corresponding to registers 4-10, true if registers are in use
   * Initialized to false by default */
  private final boolean[] registers = new boolean[7];

  /* -------------------------------- Instruction Labels -------------------------------- */
  /* Currently active instruction label */
  private Label<Instruction> currentLabel;
  /* List of all instruction labels */
  private final List<Label<Instruction>> instructionLabels = new ArrayList<>();
  /* Stores next available anonymous label number */
  private int labelCounter = 0;

  /* -------------------------------- Symbol Tables -------------------------------- */
  /* Currently active symbol table. Corresponds to current scope */
  private SymbolTable currentSymbolTable;
  /* List of all symbol tables for functions, mapped to their function names */
  private Map<String, SymbolTable> functionTables = new HashMap<>();

  /* -------------------------------- End Functions -------------------------------- */
  private final Set<Label<Instruction>> endFunctions = new HashSet<>();
  private final List<Label<Data>> dataLabels = new ArrayList<>();
  private final Map<String, String> dataPlaceHolders = new HashMap<>();


  /* -------------------------------- Registers -------------------------------- */

  /* Returns value corresponding to the free register with the lowest value */
  public int getFreeRegister() {
    for (int i = 0; i < MAX_INDEX + 1; i++) {
      /* Check if any registers from 4 to 7 are free. Return them if they are. */
      if (!registers[i]) {
        registers[i] = true;
        return i + REGISTER_OFFSET;
      }
    }
    /* Push contents of register 10 to stack and return register 10 if no registers are free */
    currentLabel.addToBody(Stack.PUSH(new RegisterOperand(MAX_INDEX + REGISTER_OFFSET)));
    this.getCurrentSymbolTable().incrementOffset(SIZE_OF_ADDRESS);
    return MAX_INDEX + REGISTER_OFFSET;
  }

  /* Updates status of specified register to be free. Returns true if successful. */
  public boolean freeRegister(int register_num) {
    if (register_num < REGISTER_OFFSET || register_num > REGISTER_OFFSET + MAX_INDEX) {
      return false;
    }
    int index = register_num - REGISTER_OFFSET;
    if (!registers[index]) {
      return true;
    }
    registers[index] = false;
    return true;
  }

  /* -------------------------------- Instruction Labels -------------------------------- */

  /* Returns current instruction label which should be added to. */
  public Label<Instruction> getCurrentLabel() {
    return currentLabel;
  }

  /* Adds instruction to body of current instruction label. */
  public void addToCurrentLabel(Instruction instruction) {
    getCurrentLabel().addToBody(instruction);
  }

  /* Sets current instruction label. */
  public void setCurrentLabel(Label<Instruction> currentLabel) {
    this.currentLabel = currentLabel;
  }

  /* Returns list of all instruction labels. */
  public List<Label<Instruction>> getInstructionLabels() {
    return instructionLabels;
  }

  /* Returns next available anonymous label name. */
  public String getNextAnonymousLabel() {
    int counterToReturn = labelCounter;
    labelCounter++;
    return "L" + counterToReturn;
  }

  /* -------------------------------- End Functions -------------------------------- */

  /* Returns set of end functions. */
  public Set<Label<Instruction>> getEndFunctions() {
    return endFunctions;
  }

  public Label<Data> getSpecificLabel(String content) {
    for (Label<Data> data : dataLabels) {
      if (data.getFunctionLabel().equals(content)) {
        return data;
      }
    }
    return null;
  }

  public List<Label<Data>> getDataLabels() {
    /* Use getNextDataLabelString() and addToDataLabels instead of
     * manipulating the list directly! */
    return dataLabels;
  }

  public String getNextDataLabelString() {
    return "msg_" + dataLabels.size();
  }

  public void addToDataLabels(Label<Data> dataLabel) {
    dataLabels.add(dataLabel);
  }

  public Map<String, String> getDataPlaceHolders() {
    return dataPlaceHolders;
  }

  /* -------------------------------- Symbol Tables -------------------------------- */

  /* Returns symbol table corresponding to current scope. */
  public SymbolTable getCurrentSymbolTable() {
    return currentSymbolTable;
  }

  /* Returns map of function tables mapped to their function names. */
  public Map<String, SymbolTable> getFunctionTables() {
    return functionTables;
  }

  /* Sets scope by setting current symbol table. */
  public void setScope(SymbolTable currentSymbolTable) {
    this.currentSymbolTable = currentSymbolTable;
  }

  /* Restores the scope by changing the currentSymbolTable back to its parent
   * and adding instructions to move the stack pointer. */
  public void restoreScope() {
    /* Maximum amount of bytes that can be added at once */
    int maxConstant = 1024;
    /* Restore stack pointer according to size of current symbol table */
    int tableSize = currentSymbolTable.getTableSize();
    while (tableSize > 0) {
      /* Use multiple ADD instructions if table size > 1024. */
      DataProcessing restoreStackPtrInstr = DataProcessing
          .ADD(RegisterOperand.SP,
              RegisterOperand.SP,
              new ImmediateOperand<>(Integer.min(tableSize, maxConstant))
                  .withPrefixSymbol("#"));
      tableSize -= maxConstant;
      addToCurrentLabel(restoreStackPtrInstr);
    }

    /* Set currentSymbolTable as its parent. */
    SymbolTable parentSymbolTable = getCurrentSymbolTable()
        .getParentSymbolTable();
    if (parentSymbolTable != null) {
      currentSymbolTable = parentSymbolTable;
    }
  }

}
