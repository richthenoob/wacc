package ic.doc.backend.Instructions;

import java.util.ArrayList;
import java.util.List;

public class DataProcessing extends Instruction {
  // 2 operands for CMP, 4 operands for SMULL, 3 otherwise
  private List<Operand> operands;
  private Operation operation;

  public DataProcessing(Operand dst, Operand lhs,
      Operand rhs, Operation operation) {
    operands = new ArrayList<>();
    operands.add(dst);
    operands.add(lhs);
    operands.add(rhs);
    this.operation = operation;
  }

  public DataProcessing(Operand operand1, Operand operand2) {
    operands = new ArrayList<>();
    operands.add(operand1);
    operands.add(operand2);
    this.operation = Operation.CMP;
  }

  public DataProcessing(Operand rdLo, Operand rdHi,
      Operand rm, Operand rs) {
    operands = new ArrayList<>();
    operands.add(rdLo);
    operands.add(rdHi);
    operands.add(rm);
    operands.add(rs);
    this.operation = Operation.SMULL;
  }

  @Override
  public String toAssembly() {
    //TODO: tostring for operation enum?
    StringBuilder assembly = new StringBuilder();
    assembly.append(operation.toString());
    assembly.append(" ");
    for (int i = 0; i < operands.size(); i++) {
      assembly.append(operands.get(i));
      assembly.append(", ");
    }
    assembly.delete(assembly.length()-2, assembly.length()-1);
    return assembly.toString();
  }
}
