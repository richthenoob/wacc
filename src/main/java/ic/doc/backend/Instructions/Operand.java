package ic.doc.backend.Instructions;

public class Operand {
    public static Operand R0 = new Operand(OperandType.REG, 0);
    public static Operand R1 = new Operand(OperandType.REG,1);
    public static Operand SP = new Operand(OperandType.REG,13);
    public static Operand LR = new Operand(OperandType.REG,14);
    public static Operand PC = new Operand(OperandType.REG,15);

    private OperandType operandType;
    private int value; //for register number or constant value
    private String varName;

    private Operand(OperandType operandType, int value) {
        this.operandType = operandType;
        this.value = value;
    }

    public static Operand REG(int value) {
        return new Operand(OperandType.REG, value);
    }

    public static Operand MEM(int value){
        return new Operand(OperandType.MEM, value);
    }

    public static Operand IMM(int value){
        return new Operand(OperandType.IMM, value);
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public int getValue() {
        return value;
    }


    @Override
    public String toString() {
        switch (operandType){
            case REG:
                if(value == 13){
                    return "sp";
                }
                else if(value == 14){
                    return "lr";
                }
                else if(value == 15){
                    return "pc";
                }
                else {
                    return "r" + value;
                }

            case MEM:
                return "=" + varName; //Not sure what to do here or if this situation occurs
            default:
                return "#" + value;
        }
    }
}


