package ic.doc.backend;

import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.DataProcessing;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.Stack;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.frontend.semantics.SymbolTable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Context {

  public static final int OFFSET = 4;
  public static final int MAXINDEX = 6;

  private final boolean[] registers =
      new boolean[7]; // registers 4-10 initialised by default to false
  private int labelCounter = 0; // Used for anonymous label names

  private Label<Instruction> currentLabel;
  private final List<Label<Instruction>> instructionLabels = new ArrayList<>();
  private final List<Label<Data>> dataLabels = new ArrayList<>();
  private final Set<Label<Instruction>> pfunctions = new HashSet<>();
  private SymbolTable currentSymbolTable;

  public void addToLastInstructionLabel(Instruction instruction) {
    instructionLabels.get(instructionLabels.size() - 1).addToBody(instruction);
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
    currentLabel.addToBody(
        Stack.PUSH(
            new RegisterOperand(MAXINDEX + OFFSET),
            currentSymbolTable)); // Push to stack and return r10
    return MAXINDEX + OFFSET;
  }

  public List<Label<Instruction>> getInstructionLabels() {
    return instructionLabels;
  }

  public List<Label<Data>> getDataLabels() {
    return dataLabels;
  }

  public Label<Instruction> getCurrentLabel() {
    return currentLabel;
  }

  public void setCurrentLabel(Label<Instruction> currentLabel) {
    this.currentLabel = currentLabel;
  }

  public SymbolTable getCurrentSymbolTable() {
    return currentSymbolTable;
  }

  public void setCurrentSymbolTable(SymbolTable currentSymbolTable) {
    this.currentSymbolTable = currentSymbolTable;
  }

  public Set<Label<Instruction>> getPfunctions() {
    return pfunctions;
  }

  public String getNextAnonymousLabel() {
    int counterToReturn = labelCounter;
    labelCounter++;
    return "L" + counterToReturn;
  }

  /* Restores the scope by changing the currentSymbolTable back to its parent
   * and adding an instruction move the stack pointer. */
  public void restoreScope() {
    int tableSize = currentSymbolTable.getTableSize();

    if (tableSize != 0) {
      DataProcessing restoreStackPtrInstr = DataProcessing
          .ADD(RegisterOperand.SP,
              RegisterOperand.SP,
              new ImmediateOperand(true, tableSize));

      addToLastInstructionLabel(restoreStackPtrInstr);
    }

    currentSymbolTable = getCurrentSymbolTable().getParentSymbolTable();
  }

}
