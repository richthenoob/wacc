package ic.doc.backend.instructions;

public class LoadLiterals extends Instruction {

  @Override
  public String toAssembly() {
    return ".ltorg";
  }
}
