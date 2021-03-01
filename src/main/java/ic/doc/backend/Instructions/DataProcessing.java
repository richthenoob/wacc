package ic.doc.backend.Instructions;

import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.Operand;
import ic.doc.backend.Instructions.operands.PreIndexedAddressOperand;

import java.util.ArrayList;
import java.util.List;

public class DataProcessing extends Instruction {
  // 2 operands for CMP, 4 operands for SMULL, 3 otherwise
  private List<Operand> operands;
  private Operation operation;
  private PreIndexedAddressOperand.ShiftTypes shift = null;
  private ImmediateOperand<Integer> multiplier = null;

  // CMP
  private DataProcessing(Operand operand1, Operand operand2) {
    operands = new ArrayList<>();
    operands.add(operand1);
    operands.add(operand2);
    this.operation = Operation.CMP;
  }

  // SMULL
  private DataProcessing(Operand rdLo, Operand rdHi, Operand rm, Operand rs) {
    operands = new ArrayList<>();
    operands.add(rdLo); // least significant bits
    operands.add(rdHi); // most significant bits
    operands.add(rm); // operand 1 to be multiplied
    operands.add(rs); // operand 2 to be multiplied
  }

  // All other operations
  private DataProcessing(Operand dst, Operand lhs, Operand rhs, Operation operation) {
    operands = new ArrayList<>();
    operands.add(dst);
    operands.add(lhs);
    operands.add(rhs);
    this.operation = operation;
  }

  public static DataProcessing CMP(Operand operand1, Operand operand2) {
    return new DataProcessing(operand1, operand2);
  }

  public static DataProcessing SMULL(Operand rdLo, Operand rdHi, Operand rm, Operand rs) {
    return new DataProcessing(rdLo, rdHi, rm, rs);
  }

  public static DataProcessing ADD(Operand dst, Operand lhs, Operand rhs) {
    return new DataProcessing(dst, lhs, rhs, Operation.ADD);
  }

  public static DataProcessing SHIFTADD(
      Operand dst,
      Operand lhs,
      Operand rhs,
      PreIndexedAddressOperand.ShiftTypes shift,
      ImmediateOperand<Integer> multiplier) {
    DataProcessing ret = new DataProcessing(dst, lhs, rhs, Operation.ADD);
    ret.shift = shift;
    ret.multiplier = multiplier;
    return ret;
  }

  public static DataProcessing SUB(Operand dst, Operand lhs, Operand rhs) {
    return new DataProcessing(dst, lhs, rhs, Operation.SUB);
  }

  public static DataProcessing AND(Operand dst, Operand lhs, Operand rhs) {
    return new DataProcessing(dst, lhs, rhs, Operation.AND);
  }

  public static DataProcessing ORR(Operand dst, Operand lhs, Operand rhs) {
    return new DataProcessing(dst, lhs, rhs, Operation.ORR);
  }

  public static DataProcessing RSBS(Operand dst, Operand lhs, Operand rhs) {
    return new DataProcessing(dst, lhs, rhs, Operation.RSBS);
  }

  public static DataProcessing EOR(Operand dst, Operand lhs, Operand rhs) {
    return new DataProcessing(dst, lhs, rhs, Operation.EOR);
  }

  @Override
  public String toAssembly() {
    // TODO: tostring for operation enum?
    StringBuilder assembly = new StringBuilder();
    assembly.append(operation.toString());
    assembly.append(" ");
    for (int i = 0; i < operands.size(); i++) {
      assembly.append(operands.get(i));
      assembly.append(", ");
    }
    if (shift != null) {
      assembly.append(shift.name());
      assembly.append(" ");
      assembly.append(multiplier.toString());
      return assembly.toString();
    }
    assembly.delete(assembly.length() - 2, assembly.length() - 1);
    return assembly.toString();
  }
}
