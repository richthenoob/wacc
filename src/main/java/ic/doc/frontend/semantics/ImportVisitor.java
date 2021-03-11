package ic.doc.frontend.semantics;

import ic.doc.antlr.BasicParser;
import ic.doc.antlr.BasicParserBaseVisitor;
import ic.doc.frontend.errors.SemanticErrorList;
import ic.doc.frontend.nodes.*;
import ic.doc.frontend.nodes.statnodes.StatNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ic.doc.frontend.nodes.ImportVisitorNode.magicallyParse;

public class ImportVisitor extends BasicParserBaseVisitor<Node> {
  private String baseDirectory;

  public ImportVisitor(String baseDirectory){
    this.baseDirectory = baseDirectory;
  }

  @Override
  public Node visitProg(BasicParser.ProgContext ctx) throws IllegalArgumentException {
    List<BasicParser.IncludeContext> includeCtxs = ctx.include();
    List<BasicParser.FuncContext> functionCtxs = ctx.func();

    List<String> imports = new ArrayList<>();

    for(BasicParser.IncludeContext i : includeCtxs){
      ImportNode node = (ImportNode) visit(i);
      String fileDirectory = baseDirectory + node.getFileName();
      System.out.println(fileDirectory);
      imports.add(fileDirectory);
    }

    ImportVisitorNode node = new ImportVisitorNode();

    for(String file : imports){
      try {
        List<BasicParser.FuncContext> funcCtxs = magicallyParse(file);
        for(BasicParser.FuncContext funcCtx : funcCtxs){
          node.addFuncCtx(funcCtx);
        }
      } catch(IOException e){
        throw new IllegalArgumentException("File not found");
      }
    }


    for(BasicParser.FuncContext funcCtx : functionCtxs){
      node.addFuncCtx(funcCtx);
    }

    return node;
  }

  @Override
  public Node visitInclude(BasicParser.IncludeContext ctx) {
    String fileName = ctx.FILE_NAME().getText();
    /* Remove start and end quotes of file name */
    fileName = fileName.substring(1, fileName.length() - 1);
    return new ImportNode(fileName);
  }
}
