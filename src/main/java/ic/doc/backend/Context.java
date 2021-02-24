package ic.doc.backend;

import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Context {

  public static final int ERROR = 16;
  public static final int OFFSET = 2;
  private Label<Instruction> currentLabel;
  private final boolean[] registers = new boolean[11]; // registers 2-12 initialised by default to false
  private final List<Label<Instruction>> instructionLabels = new ArrayList<>();
  private final List<Label<Data>> dataLabels = new ArrayList<>();
  private final Set<Label<Instruction>> pfunctions = new HashSet<>();

  public boolean freeRegister(int register_num) {
    if (register_num < 2 || register_num > 12) {
      return false;
    }
    int index = register_num - 2;
    if (!registers[index]) {
      return true;
    }
    registers[index] = false;
    return true;
  }

  public int getFreeRegister() {
    for (int i = 0; i < 11; i++) {
      if (!registers[i]) {
        return i + OFFSET;  //since register 2 corresponds to array index 0
      }
    }
    return ERROR; //Error
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

  public void setCurrentLabel(
      Label<Instruction> currentLabel) {
    this.currentLabel = currentLabel;
  }

  public Set<Label<Instruction>> getPfunctions() {
    return pfunctions;
  }
}
