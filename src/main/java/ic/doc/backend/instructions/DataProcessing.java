package ic.doc.backend.instructions;

import ic.doc.backend.instructions.operands.AddressOperand.ShiftTypes;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.Operand;
import ic.doc.backend.instructions.operands.PreIndexedAddressOperand;

import ic.doc.backend.instructions.operands.RegisterOperand;
import java.util.ArrayList;
import java.util.List;

public class DataProcessing extends Instruction {
  /* 2 operands for CMP, 4 operands for SMULL, 3 otherwise */
  private List<Operand> operands;
  private Operation operation;
  private PreIndexedAddressOperand.ShiftTypes shift = null;
  private ImmediateOperand<Integer> multiplier = null;

  private DataProcessing(Operand dst, Operand lhs, Operand rhs, Operation operation) {
    operands = new ArrayList<>();
    operands.add(dst);
    operands.add(lhs);
    operands.add(rhs);
    this.operation = operation;
  }

  private DataProcessing(Operation operation) {
    operands = new ArrayList<>();
    this.operation = operation;
  }

  public static DataProcessing CMP(Operand operand1, Operand operand2) {
    DataProcessing cmp = new DataProcessing(Operation.CMP);
    cmp.operands.add(operand1);
    cmp.operands.add(operand2);
    return cmp;
  }

  public static DataProcessing SMULL(Operand rdLo, Operand rdHi, Operand rm, Operand rs) {
    DataProcessing smull = new DataProcessing(Operation.SMULL);
    smull.operands.add(rdLo); /* least significant bits */
    smull.operands.add(rdHi); /* most significant bits */
    smull.operands.add(rm); /* operand 1 to be multiplied */
    smull.operands.add(rs); /* operand 2 to be multiplied */
    return smull;
  }

  public static DataProcessing ADD(Operand dst, Operand lhs, Operand rhs) {
    Operation op = Operation.ADD;
    if (rhs instanceof RegisterOperand) {
      op = Operation.ADDS;    /* Signed add. */
    }
    return new DataProcessing(dst, lhs, rhs, op);
  }

  public static DataProcessing SHIFTADD(
      Operand dst,
      Operand lhs,
      Operand rhs,
      ShiftTypes shift,
      ImmediateOperand<Integer> multiplier) {
    DataProcessing ret = new DataProcessing(dst, lhs, rhs, Operation.ADD);
    ret.shift = shift;
    ret.multiplier = multiplier;
    return ret;
  }

  public static DataProcessing SUB(Operand dst, Operand lhs, Operand rhs) {
    Operation op = Operation.SUB;
    if (rhs instanceof RegisterOperand) {
      op = Operation.SUBS; /* Signed subtract. */
    }
    return new DataProcessing(dst, lhs, rhs, op);
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
