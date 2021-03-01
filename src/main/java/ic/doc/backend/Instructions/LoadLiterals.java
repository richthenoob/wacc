package ic.doc.backend.Instructions;

public class LoadLiterals extends Instruction{
  @Override
  public String toAssembly() {
    return ".ltorg";
  }
}
