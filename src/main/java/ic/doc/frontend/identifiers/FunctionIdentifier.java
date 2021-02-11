package ic.doc.frontend.identifiers;

import ic.doc.frontend.types.Type;
import java.util.List;

public class FunctionIdentifier extends Identifier {

  private final List<Type> paramTypeList;

  public FunctionIdentifier(Type type, List<Type> paramTypeList) {
    super(type);
    this.paramTypeList = paramTypeList;
  }

  public List<Type> getParamTypeList() {
    return paramTypeList;
  }

  public String printTypes() {
    StringBuilder types = new StringBuilder();
    types.append("(");
    for (int i = 0; i < paramTypeList.size(); i++) {
      types.append(paramTypeList.get(i).toString());
      types.append(", ");
    }
    if (paramTypeList.size() > 0) {
      types.delete(types.length() - 2, types.length());
    }
    types.append(")");
    return types.toString();
  }

  @Override
  public String toString() {
    // int(int, str, int)
    return getType() + "(" + printTypes() + ")";
  }
}
