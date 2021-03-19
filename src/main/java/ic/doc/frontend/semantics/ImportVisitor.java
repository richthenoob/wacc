package ic.doc.frontend.semantics;

import ic.doc.antlr.BasicParser;
import ic.doc.antlr.BasicParserBaseVisitor;
import ic.doc.frontend.errors.SemanticErrorList;
import ic.doc.frontend.nodes.*;
import ic.doc.frontend.nodes.statnodes.StatNode;
import ic.doc.frontend.utils.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ic.doc.frontend.utils.fsUtils.parseImportedFile;

public class ImportVisitor extends BasicParserBaseVisitor<Node> {
  private String filePath;
  private Set<String> allImports;

  public ImportVisitor(String filePath, Set<String> allImports){
    this.filePath = filePath;
    this.allImports = allImports;
  }

  @Override
  public Node visitProg(BasicParser.ProgContext ctx) throws IllegalArgumentException {
    List<BasicParser.IncludeContext> includeCtxs = ctx.include();
    List<BasicParser.FuncContext> functionCtxs = ctx.func();
    List<BasicParser.Class_Context> classCtxs = ctx.class_();

    List<String> imports = new ArrayList<>();

    for(BasicParser.IncludeContext i : includeCtxs){
      ImportNode node = (ImportNode) visit(i);
      String fileName = node.getFileName();
      String normalizedFilePath;
      if(fileName.equals(Visitor.STDLIB_NAME)){
        normalizedFilePath = Paths.get("").toAbsolutePath().resolve(Visitor.STDLIB_DIR).toString();
      } else {
        normalizedFilePath = Paths.get(filePath).resolve(fileName).normalize().toString();
      }
      imports.add(normalizedFilePath);
    }

    ImportVisitorNode node = new ImportVisitorNode();

    for(String file : imports){
      /* Ignore any file that has already been imported */
      if(allImports.contains(file)){
        continue;
      }
      allImports.add(file);
      try {
        ImportVisitorNode importedFile = parseImportedFile(file, allImports);
        for(Pair<BasicParser.FuncContext, String> funcCtx : importedFile.getFuncCtxs()){
          node.addFuncCtx(funcCtx.getFst(), funcCtx.getSnd());
        }
        for(Pair<BasicParser.Class_Context, String> classCtx : importedFile.getClassCtxs()){
          node.addClassCtx(classCtx.getFst(), classCtx.getSnd());
        }
      } catch(IOException e){
        throw new IllegalArgumentException(e.getMessage());
      }
    }

    for(BasicParser.FuncContext funcCtx : functionCtxs){
      node.addFuncCtx(funcCtx, filePath);
    }

    for(BasicParser.Class_Context classCtx : classCtxs){
      node.addClassCtx(classCtx, filePath);
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
