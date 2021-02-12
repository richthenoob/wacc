package ic.doc.frontend.types;

import java.util.List;

public abstract class Type {

  public abstract String toString();

  public static boolean checkTypeCompatibility(Type t1, Type t2){
    if(t1 instanceof ErrorType || t2 instanceof ErrorType){
      return false;
    }
    if(t1 instanceof AnyType || t2 instanceof AnyType){
      return true;
    }

    if(t1 instanceof ArrayType && t2 instanceof ArrayType){
      ArrayType a1 = (ArrayType) t1;
      ArrayType a2 = (ArrayType) t2;
      return checkTypeCompatibility(a1.getInternalType(), a2.getInternalType());
    }

    if(t1 instanceof PairType && t2 instanceof PairType){
      PairType p1 = (PairType) t1;
      PairType p2 = (PairType) t2;
      return checkTypeCompatibility(p1.getType1(), p2.getType1()) && checkTypeCompatibility(p1.getType2(), p2.getType2());
    }

    return t1.getClass().equals(t2.getClass());
  }

  public static boolean checkTypeListCompatibility(List<Type> t1s, List<Type> t2s){
    if(t1s.size() != t2s.size()){
      return false;
    }

    for(int i = 0; i < t1s.size(); i++){
      Type t1 = t1s.get(i);
      Type t2 = t2s.get(i);
      if(!checkTypeCompatibility(t1,t2)){
        return false;
      }
    }

    return true;
  }
}
