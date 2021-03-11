package ic.doc.frontend.types;

public class ClassType extends Type {

  public static final String CLASS_NAME = "CLASS";
  private final String className;

  public ClassType(String className) {
    this.className = className;
  }

  public String getClassName() {
    return className;
  }

  @Override
  public int getVarSize() {
    /* Size of address to store on stack, not necessarily the class size!*/
    return 4;
  }

  @Override
  public String toString() {
    return CLASS_NAME + " " + className;
  }
}
