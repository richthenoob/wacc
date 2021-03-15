package ic.doc.frontend.types;

import ic.doc.frontend.identifiers.ClassIdentifier;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.SymbolTable;

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

  /* Check that t1 is a subclass of t2 or t1 name == t2 name. */
  public static boolean checkClassCompatibility(ClassType t1, ClassType t2,
      SymbolTable currentSymbolTable) {
    String t1ClassName = t1.getClassName();
    String t2ClassName = t2.getClassName();

    if (t1ClassName.equals(t2ClassName)) {
      return true;
    }

    return isSubclassOf(t1ClassName, t2ClassName, currentSymbolTable);
  }

  public static boolean isSubclassOf(String currentClassName,
      String parentClassName,
      SymbolTable symbolTable) {

    /* Iteratively lookup immediate super class names, only returning
     * true if it matches the parent class name we are looking for. */
    while (!currentClassName.isEmpty()) {
      SymbolKey currentKey = new SymbolKey(currentClassName, KeyTypes.CLASS);
      Identifier currentIdentifier = symbolTable.lookupAll(currentKey);

      if (!(currentIdentifier instanceof ClassIdentifier)) {
        throw new IllegalStateException(currentClassName +
            " not in the symbol table or does not exist as a class identifier!");
      }

      String immediateSuperClass = ((ClassIdentifier) currentIdentifier)
          .getImmediateSuperClass();

      if (immediateSuperClass.equals(parentClassName)) {
        return true;
      }

      currentClassName = immediateSuperClass;
    }

    return false;
  }
}
