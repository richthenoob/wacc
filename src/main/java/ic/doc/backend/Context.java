package ic.doc.backend;

import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.DataProcessing;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.Stack;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.frontend.semantics.SymbolTable;

import java.util.*;

public class Context {

  public static final int OFFSET = 4;
  public static final int MAXINDEX = 6;

  private final boolean[] registers =
      new boolean[7]; // registers 4-10 initialised by default to false
  private int labelCounter = 0; // Used for anonymous label names

  private Label<Instruction> currentLabel;
  private final List<Label<Instruction>> instructionLabels = new ArrayList<>();

  private final List<Label<Data>> dataLabels = new ArrayList<>();
  private final Set<Label<Instruction>> endFunctions = new HashSet<>();

  private final Map<String, String> dataPlaceHolders = new HashMap<>();
  private SymbolTable currentSymbolTable;

  public void addToCurrentLabel(Instruction instruction) {
    getCurrentLabel().addToBody(instruction);
  }

  public boolean freeRegister(int register_num) {
    if (register_num < OFFSET || register_num > OFFSET + MAXINDEX) {
      return false;
    }
    int index = register_num - OFFSET;
    if (!registers[index]) {
      return true;
    }
    registers[index] = false;
    return true;
  }

  public int getFreeRegister() {
    for (int i = 0; i < MAXINDEX + 1; i++) {
      if (!registers[i]) {
        registers[i] = true;
        return i + OFFSET; // since register 2 corresponds to array index 0
      }
    }
    // TODO: maybe pass in an argument to determine how much to push?
    currentLabel.addToBody(
        Stack.PUSH_FOUR(
            new RegisterOperand(MAXINDEX + OFFSET),
            currentSymbolTable)); // Push to stack and return r10
    return MAXINDEX + OFFSET;
  }

  public List<Label<Instruction>> getInstructionLabels() {
    return instructionLabels;
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

  public Label<Instruction> getCurrentLabel() {
    return currentLabel;
  }

  public void setCurrentLabel(Label<Instruction> currentLabel) {
    this.currentLabel = currentLabel;
  }

  public Set<Label<Instruction>> getEndFunctions() {
    return endFunctions;
  }

  public SymbolTable getCurrentSymbolTable() {
    return currentSymbolTable;
  }

  public Map<String, String> getDataPlaceHolders() { return dataPlaceHolders; }

  public String getNextAnonymousLabel() {
    int counterToReturn = labelCounter;
    labelCounter++;
    return "L" + counterToReturn;
  }

  public String getNextDataLabelString() {
    return "msg_" + dataLabels.size();
  }

  public void addToDataLabels(Label<Data> dataLabel) {
    dataLabels.add(dataLabel);
  }

  public void setScope(SymbolTable currentSymbolTable) {
    this.currentSymbolTable = currentSymbolTable;
  }

  /* Restores the scope by changing the currentSymbolTable back to its parent
   * and adding an instruction move the stack pointer. */
  public void restoreScope() {
    int tableSize = currentSymbolTable.getTableSize();

    if (tableSize != 0) {
      DataProcessing restoreStackPtrInstr = DataProcessing
          .ADD(RegisterOperand.SP,
              RegisterOperand.SP,
              new ImmediateOperand<>(true, tableSize));

      addToCurrentLabel(restoreStackPtrInstr);
    }

    SymbolTable parentSymbolTable = getCurrentSymbolTable()
        .getParentSymbolTable();
    if (parentSymbolTable != null) {
      currentSymbolTable = parentSymbolTable;
    }
  }

}
