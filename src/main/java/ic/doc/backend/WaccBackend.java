package ic.doc.backend;

import ic.doc.backend.instructions.Instruction;
import ic.doc.backend.instructions.Move;
import ic.doc.backend.instructions.SingleDataTransfer;
import ic.doc.frontend.nodes.ProgNode;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WaccBackend {

  public static String generateCode(ProgNode rootNode) {
    /* Recursively walk tree to generate code. */
    Context context = new Context();
    rootNode.translate(context);
    StringBuilder outputString = new StringBuilder();

    List<Label<Data>> dataLabels = context.getDataLabels();
    List<Label<Instruction>> instructionLabels = context.getInstructionLabels();

    /* Peephole Optimization */
    for (Label<Instruction> instructionLabel : instructionLabels) {
      if (!instructionLabel.getBody().isEmpty()) {
        List<Instruction> optimizedInstructions = new ArrayList<>();
        Instruction prevInstruction = instructionLabel.getBody().get(0);
        for (Instruction instruction : instructionLabel.getBody()) {

          /* Remove redundant moves eg: MOV r1,r2 followed by MOV r2,r1 */
          if (prevInstruction instanceof Move && instruction instanceof Move) {
            /* If not optimizable then add to new List */
            if (!((Move) instruction).optimizable((Move) prevInstruction)) {
              optimizedInstructions.add(instruction);
            }
          }
          /* Remove redundant Load after Store eg STR r1, [sp] followed by LDR r1, [sp] */
          else if (prevInstruction instanceof SingleDataTransfer
              && instruction instanceof SingleDataTransfer) {
            /* If not optimizable then add to new List */
            if (!((SingleDataTransfer) instruction)
                .optimizable((SingleDataTransfer) prevInstruction)) {
              optimizedInstructions.add(instruction);
            }
          } else {
            optimizedInstructions.add(instruction);
          }
          prevInstruction = instruction;
        }
        instructionLabel.setBody(optimizedInstructions);
      }
    }
    /* Build .data section. */
    outputString.append(".data\n");

    for (Label<Data> dataLabel : dataLabels) {
      outputString.append(dataLabel.toString());
      outputString.append(":\n");
      for (Data data : dataLabel.getBody()) {
        outputString.append(data.toAssembly());
        outputString.append('\n');
      }
    }

    /* Build .text section. */
    outputString.append("\n.text\n");
    outputString.append(".global main\n");
    for (Label<Instruction> instructionLabel : instructionLabels) {
      outputString.append(instructionLabel.toString());
      outputString.append(":\n");
      for (Instruction instruction : instructionLabel.getBody()) {
        outputString.append('\t');
        outputString.append(instruction.toAssembly());
        outputString.append('\n');
      }
    }

    /* Build predefined functions at the very end. */
    for (Label<Instruction> endLabel : context.getEndFunctions()) {
      outputString.append(endLabel.toString());
      outputString.append(":\n");
      for (Instruction instruction : endLabel.getBody()) {
        outputString.append('\t');
        outputString.append(instruction.toAssembly());
        outputString.append('\n');
      }
    }

    return outputString.toString();
  }

  public static void writeToFile(String filepath, String output) {
    try {
      FileWriter writer = new FileWriter(filepath);
      writer.write(output);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
