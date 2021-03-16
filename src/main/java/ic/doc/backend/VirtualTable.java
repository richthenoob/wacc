package ic.doc.backend;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VirtualTable {

  public static final String VIRTUAL_TABLE_PREFIX = "vtable_";

  /* Map of functions used in class to the class it was originally declared in
   * e.g. myFunc -> classA */
  private final Map<String, String> classFunctions;
  private final String className;

  public VirtualTable(String className) {
    this.className = className;
    this.classFunctions = new LinkedHashMap<>();
  }

  public int getFunctionOffset(String functionName) {
    List<String> keys = new ArrayList<>(classFunctions.keySet());
    return keys.indexOf(functionName);
  }

  public void addClassFunction(String functionName, String className) {
    classFunctions.put(functionName, className);
  }

  public String toAssembly() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(VIRTUAL_TABLE_PREFIX).append(className).append(":");
    stringBuilder.append("\n\t.word 0");

    for (Map.Entry<String, String> entry : classFunctions.entrySet()) {
      String funcName = entry.getKey();
      String funcClassName = entry.getValue();
      stringBuilder.append("\n\t.word ");
      stringBuilder.append(funcClassName).append("_f_").append(funcName);
    }
    return stringBuilder.toString();
  }
}
