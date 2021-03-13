package ic.doc.frontend.semantics;

import ic.doc.antlr.BasicParser;
import ic.doc.antlr.BasicParserBaseVisitor;
import ic.doc.frontend.errors.SemanticErrorList;
import ic.doc.frontend.nodes.*;
import ic.doc.frontend.nodes.statnodes.StatNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ic.doc.frontend.utils.fsUtils.parseImportedFile;

public class ImportVisitor extends BasicParserBaseVisitor<Node> {
  private String baseDirectory;
  private Set<String> allImports;

  public ImportVisitor(String baseDirectory, Set<String> allImports){
    this.baseDirectory = baseDirectory;
    this.allImports = allImports;
  }

  @Override
  public Node visitProg(BasicParser.ProgContext ctx) throws IllegalArgumentException {
    List<BasicParser.IncludeContext> includeCtxs = ctx.include();
    List<BasicParser.FuncContext> functionCtxs = ctx.func();

    List<String> imports = new ArrayList<>();

    for(BasicParser.IncludeContext i : includeCtxs){
      ImportNode node = (ImportNode) visit(i);
      /* Resolves the file against the current directory and normalize it to remove . and .. */
      String includedFilePath = Paths.get(baseDirectory).resolve(node.getFileName()).normalize().toString();
      imports.add(includedFilePath);
    }

    ImportVisitorNode node = new ImportVisitorNode();

    for(String file : imports){
      /* Ignore any file that has already been imported */
      if(allImports.contains(file)){
        continue;
      }
      allImports.add(file);
      try {
        List<BasicParser.FuncContext> funcCtxs = parseImportedFile(file, allImports);
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
