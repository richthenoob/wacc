package ic.doc.backend;

import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.*;

import java.util.List;


public class PredefinedFunctions {
  private static void addCommonPrintInstructions(Label instrLabel){

  }
  public static void addPrintStringFunction(List<Label> instrLabels){
//    Label<Instruction> printStringLabel = new Label("p_print_string");
//    printStringLabel.addToBody(new Stack(true, Operand.LR));
//    // Operand.R0 needs to be [Operand.R0]
//    printStringLabel.addToBody(new SingleDataTransfer(true, Operand.R1, Operand.R0));
//    printStringLabel.addToBody(new DataProcessing(
//            new Operand(OperandType.REG, 2),
//            Operand.R0,
//            new Operand(OperandType.CONST, 4),
//            Operation.ADD)
//    );
//    printStringLabel.addToBody(new SingleDataTransfer);

//    Utility.addFunction(new LabelInstr("p_print_string"));
//    Utility.addFunction(new PUSH(Register.LR));
//    //TODO: InterferenceGraph.findRegister("print_string_ldr")
//    Utility.addFunction(new LOAD(Register.R1, new PreIndex(Register.R0)));
//    //TODO: InterferenceGraph.findRegister("print_string_mov")
//    Utility.addFunction(new ADD(new Register(2), Register.R0, new ImmValue(4)));
//    Utility.addFunction(new LOAD(Register.R0, new LabelExpr(Utility.getStringPlaceholder())));
//
//    printDefaults();
  }
}
