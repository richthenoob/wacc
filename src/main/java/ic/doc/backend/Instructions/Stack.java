package ic.doc.backend.Instructions;

import ic.doc.backend.Instructions.operands.Operand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.frontend.semantics.SymbolTable;

public class Stack extends Instruction{
  private final boolean pushFlag;
  private final RegisterOperand value;

  private Stack(boolean pushFlag, RegisterOperand value) {
    this.pushFlag = pushFlag;
    this.value = value;
  }

  public static Stack PUSH(RegisterOperand value, SymbolTable symbolTable){
    return new Stack(true, value);
  }

  public static Stack POP(RegisterOperand value, SymbolTable symbolTable){

    return new Stack(false, value);
  }

  @Override
  public String toAssembly() {
    String action = pushFlag ? "PUSH " : "POP ";
    return action + "{" + value + "}";
  }
}
