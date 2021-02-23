package ic.doc.backend.Instructions;

public class Move extends Instruction{

    private Operand dst;
    private Operand src;
    private Condition condition;

    public Move(Operand dst, Operand src, Condition condition) {
        this.dst = dst;
        this.src = src;
        this.condition = condition;
    }


    @Override
    public String toAssembly() {
        switch(condition){
            case BEQ:
                return "MOVEQ "+ dst.toString() + ", " + src.toString();
            case BNE:
                return "MOVNE "+ dst.toString() + ", " + src.toString();
            case  BGE:
                return "MOVGE "+ dst.toString() + ", " + src.toString();
            case BLT:
                return "MOVLT "+ dst.toString() + ", " + src.toString();
            case BGT:
                return "MOVGT "+ dst.toString() + ", " + src.toString();
            case BLE:
                return "MOVLE "+ dst.toString() + ", " + src.toString();
            default:
                return "MOV" + dst.toString() + ", " + src.toString();
        }
    }
}
