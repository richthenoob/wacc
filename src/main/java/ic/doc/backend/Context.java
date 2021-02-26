package ic.doc.backend;

import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.backend.Instructions.SingleDataTransfer;
import ic.doc.backend.Instructions.Stack;

import ic.doc.backend.Instructions.operands.RegisterOperand;
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
        return i + OFFSET; // since register 2 corresponds to array index 0
      }
    }
    instructionLabels
        .get(instructionLabels.size() - 1)
        .addToBody(
            Stack.PUSH(new RegisterOperand(MAXINDEX + OFFSET))); // Push to stack and return r10
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

  public Set<Label<Instruction>> getPfunctions() {
    return pfunctions;
  }

  public String getNextAnonymousLabel() {
    labelCounter += 1;
    return "L" + labelCounter;
  }
}
