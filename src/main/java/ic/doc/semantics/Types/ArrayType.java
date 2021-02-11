package ic.doc.semantics.Types;

import java.util.Objects;

public class ArrayType implements Type {

    private final Type internalType;

    public ArrayType(Type type) {
        this.internalType = type;
    }

    public Type getInternalType() {
        return internalType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }

        if (o.getClass().equals(AnyType.class)) {
            return true;
        }

        if (getClass() != o.getClass()) {
            return false;
        }

        ArrayType array = (ArrayType) o;

        return getInternalType().equals(array.internalType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalType);
    }

    @Override
    public java.lang.String toString() {
        return getInternalType() + "[]";
    }
}