package ic.doc.backend.Instructions;

public class Move extends Instruction{

    private Operand operand1;
    private Operand operand2;
    private Condition condition;

    @Override
    public String toAssembly() {
        switch(condition){
            case BEQ:
                return "MOVEQ "+ operand1.toString() + ", " + operand2.toString();
            case BNE:
                return "MOVNE "+ operand1.toString() + ", " + operand2.toString();
            case  BGE:
                return "MOVGE "+ operand1.toString() + ", " + operand2.toString();
            case BLT:
                return "MOVLT "+ operand1.toString() + ", " + operand2.toString();
            case BGT:
                return "MOVGT "+ operand1.toString() + ", " + operand2.toString();
            case BLE:
                return "MOVLE "+ operand1.toString() + ", " + operand2.toString();
            default:
                return "MOV" + operand1.toString() + ", " + operand2.toString();
        }
    }
}
