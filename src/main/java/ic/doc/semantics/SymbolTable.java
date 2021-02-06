package ic.doc.semantics;

import java.util.LinkedHashMap;
import java.util.Map;

public class SymbolTable {

    private SymbolTable encSymTable;
    private Map<String, Node> dictionary;

    public SymbolTable(SymbolTable encSymTable) {
        this.encSymTable = encSymTable;
        dictionary = new LinkedHashMap<>();
    }

    public void add(String name, Node obj){
        dictionary.put(name,obj);
    }

    public Node lookup(String name){
        return dictionary.get(name);
    }

    public Node lookupAll(String name){
        SymbolTable curr = this;
        while(curr != null){
            Node obj = curr.lookup(name);
            if(obj!= null){
                return obj;
            }
            curr = curr.encSymTable;
        }
        return null;
    }
}
