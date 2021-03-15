package ic.doc.backend;

import java.util.HashMap;
import java.util.Map;

public class VirtualTable {
  /* Map of functions used in class to the class it was originally declared in
   * e.g. myFunc -> classA */
 private final Map<String, String> classFunctions;

  public VirtualTable() {
    this.classFunctions = new HashMap<>();
  }

  public Map<String, String> getClassFunctions() {
    return classFunctions;
  }

  public void addClassFunction(String functionName, String className) {
    classFunctions.put(functionName, className);
  }

}
