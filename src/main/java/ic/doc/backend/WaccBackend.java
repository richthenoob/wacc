package ic.doc.backend;

import ic.doc.backend.instructions.Instruction;
import ic.doc.backend.instructions.Move;
import ic.doc.backend.instructions.SingleDataTransfer;
import ic.doc.frontend.WaccFrontend;
import ic.doc.frontend.nodes.ProgNode;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WaccBackend {

  private String output;
  private int instructCount;

  public WaccBackend(String output, int instructCount) {
    this.output = output;
    this.instructCount = instructCount;
  }

  public String getOutput() {
    return output;
  }

  public int getInstructCount() {
    return instructCount;
  }

  public static WaccBackend generateCode(ProgNode rootNode) {
    /* Recursively walk tree to generate code. */
    Context context = new Context();
    rootNode.translate(context);
    StringBuilder outputString = new StringBuilder();

    List<Label<Data>> dataLabels = context.getDataLabels();
    List<Label<Instruction>> instructionLabels = context.getInstructionLabels();
    int count = 0;
    /* Peephole Optimization */
    for (Label<Instruction> instructionLabel : instructionLabels) {
      if (!instructionLabel.getBody().isEmpty()) {
        List<Instruction> optimizedInstructions = new ArrayList<>();
        Instruction prevInstruction = instructionLabel.getBody().get(0);
        for (Instruction instruction : instructionLabel.getBody()) {
          boolean optimized = true;
          /* Remove redundant moves eg: MOV r1,r2 followed by MOV r2,r1 */
          if (prevInstruction instanceof Move
              && instruction instanceof Move
              && WaccFrontend.OPTIMIZE) {
            /* If not optimizable then add to new List */
            if (!((Move) instruction).optimizable((Move) prevInstruction)) {
              optimized = false;
              optimizedInstructions.add(instruction);
            }
          }
          /* Remove redundant Load after Store eg STR r1, [sp] followed by LDR r1, [sp] */
          else if (prevInstruction instanceof SingleDataTransfer
              && instruction instanceof SingleDataTransfer
              && WaccFrontend.OPTIMIZE) {
            /* If not optimizable then add to new List */
            if (!((SingleDataTransfer) instruction)
                .optimizable((SingleDataTransfer) prevInstruction)) {
              optimized = false;
              optimizedInstructions.add(instruction);
            }
          }
          /* Remove redundant move/Single Data Transfer when dst and src are the same */
          else if (instruction instanceof Move
              || instruction instanceof SingleDataTransfer && WaccFrontend.OPTIMIZE) {
            if (instruction instanceof Move && !((Move) instruction).redundant()) {
              optimized = false;
              optimizedInstructions.add(instruction);
            } else if (instruction instanceof SingleDataTransfer
                && !((SingleDataTransfer) instruction).redundant()) {
              optimized = false;
              optimizedInstructions.add(instruction);
            }
          } else {
            optimized = false;
            optimizedInstructions.add(instruction);
          }
          if (!optimized) {
            prevInstruction = instruction;
          }
        }
        instructionLabel.setBody(optimizedInstructions);
      }
      count += instructionLabel.getBody().size();
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

    for (VirtualTable table : context.getVirtualTables()) {
      outputString.append(table.toAssembly());
      outputString.append("\n");
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
