package ic.doc.backend.Instructions;

import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.frontend.semantics.SymbolTable;

public class Stack extends Instruction {
  private final boolean pushFlag;
  private final RegisterOperand value;

  private Stack(boolean pushFlag, RegisterOperand value) {
    this.pushFlag = pushFlag;
    this.value = value;
  }

  private static Stack PUSH(RegisterOperand value, SymbolTable symbolTable, int offset) {
    symbolTable.incrementOffset(offset);
    return new Stack(true, value);
  }

  private static Stack POP(RegisterOperand value, SymbolTable symbolTable, int offset) {
    symbolTable.decrementOffset(offset);
    return new Stack(false, value);
  }

  public static Stack PUSH_FOUR(RegisterOperand value, SymbolTable symbolTable) {
    return PUSH(value, symbolTable, 4);
  }

  public static Stack PUSH_ONE(RegisterOperand value, SymbolTable symbolTable) {
    return PUSH(value, symbolTable, 1);
  }

  public static Stack POP_FOUR(RegisterOperand value, SymbolTable symbolTable) {
    return POP(value, symbolTable, 4);
  }

  public static Stack POP_ONE(RegisterOperand value, SymbolTable symbolTable) {
    return POP(value, symbolTable, 1);
  }

  @Override
  public String toAssembly() {
    String action = pushFlag ? "PUSH " : "POP ";
    return action + "{" + value + "}";
  }
}
