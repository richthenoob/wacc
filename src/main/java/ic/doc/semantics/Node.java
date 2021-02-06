package ic.doc.semantics;

import ic.doc.semantics.IdentifierObjects.Type;

public abstract class Node {

    private String name;
    private Type type;

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
