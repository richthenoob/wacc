package ic.doc.backend.Instructions;

public class Operand {
    private OperandType operandType;
    private int value; //for register number or constant value
    private String varName;

    public Operand(OperandType operandType, int value) {
        this.operandType = operandType;
        this.value = value;
    }

    public void setVarName(String varName) {
        this.varName = varName;
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


