package ic.doc.backend;

import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.Instruction;
import ic.doc.frontend.nodes.ProgNode;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WaccBackend {

  public static String generateCode(ProgNode rootNode) {
    List<Label<Instruction>> instructionLabels = new ArrayList<>();
    List<Label<Data>> dataLabels = new ArrayList<>();

    /* Recursively walk tree to generate code. */
    rootNode.translate(instructionLabels, dataLabels);
    StringBuilder outputString = new StringBuilder();

    /* Build .data section. */
    outputString.append(".data\n");
    for (Label<Data> dataLabel : dataLabels) {
      outputString.append(dataLabel.toString());
      outputString.append(":\n");
      for (Data data : dataLabel.getBody()) {
        outputString.append('\t');
        outputString.append(data.toAssembly());
        outputString.append('\n');
      }
    }

    /* Build .text section. */
    outputString.append("\n.text\n\n");
    for (Label<Instruction> instructionLabel: instructionLabels) {
      outputString.append(instructionLabel.toString());
      outputString.append(":\n");
      for (Instruction instruction : instructionLabel.getBody()) {
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
