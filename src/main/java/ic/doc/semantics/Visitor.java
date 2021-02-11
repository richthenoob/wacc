package ic.doc.semantics;

import ic.doc.SyntaxException;
import ic.doc.antlr.BasicLexer;
import ic.doc.antlr.BasicParser;
import ic.doc.antlr.BasicParser.*;
import ic.doc.antlr.BasicParserBaseVisitor;

import ic.doc.semantics.ExprNodes.*;
import ic.doc.semantics.ExprNodes.Literals.*;
import ic.doc.semantics.ExprNodes.BinaryOperatorNode.binaryOperators;
import ic.doc.semantics.ExprNodes.UnaryOperatorNode.unaryOperators;
import ic.doc.semantics.IdentifierObjects.*;
import ic.doc.semantics.StatNodes.*;
import ic.doc.semantics.Types.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

public class Visitor extends BasicParserBaseVisitor<Node> {

  private SymbolTable currentSymbolTable;

  private boolean visitingFunctionDeclarations;

  public SymbolTable getCurrentSymbolTable() {
    return currentSymbolTable;
  }

  private List<String> semanticErrors;

  public List<String> getSemanticErrors() {
    return semanticErrors;
  }

  public void addException(ParserRuleContext ctx, String errorMessage) {
    semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
        + ":" + ctx.getStart().getCharPositionInLine() + " - " + errorMessage);
  }

  public void addTypeException(ParserRuleContext ctx, String input,
      String expectedType, String actualType) {
    if (actualType.equals("STRING")) {
      semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
          + ":" + ctx.getStart().getCharPositionInLine()
          + " - Incompatible type at \"" + input
          + "\". Expected type: " + expectedType
          + ". Actual type: " + actualType + ".");
    } else {
      semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
          + ":" + ctx.getStart().getCharPositionInLine()
          + " - Incompatible type at '" + input
          + "'. Expected type: " + expectedType
          + ". Actual type: " + actualType + ".");
    }
  }

  public void addSuggestion(String suggestion) {
    semanticErrors.add(suggestion);
  }

  public void addTokenException(ParserRuleContext ctx, String token,
      String input) {
    semanticErrors.add("Semantic error at line " + ctx.getStart().getLine()
        + ":" + ctx.getStart().getCharPositionInLine()
        + " - Invalid character token '" + token + "' in string: " + input
        + ".");
  }

  @Override
  public Node visitProg(BasicParser.ProgContext ctx) {
    SymbolTable globalSymbolTable = new SymbolTable(null);
    currentSymbolTable = globalSymbolTable;
    semanticErrors = new LinkedList<>();

    List<FuncContext> functionCtxs = ctx.func();
    List<FunctionNode> functionNodes = new ArrayList<>();
    for (FuncContext f : functionCtxs) {
      declareFunction(f);
    }
    for (FuncContext f : functionCtxs) {
      functionNodes.add((FunctionNode) visit(f));
    }

    StatNode statNode = (StatNode) visit(ctx.stat());

    return new ProgNode(functionNodes, statNode);
  }

  private void declareFunction(BasicParser.FuncContext ctx) {
    if (!ctx.IDENT().getText().equals("main")) {
      String funcName = ctx.IDENT().toString();
      Type returnType = ((TypeNode) visit(ctx.type())).getType();
      /* create new symbol table here*/

      /* Visiting stat should be visiting with the enclosing symbol table, should contain function already*/
      /* In other words, we need to add this function to the symbol table before visiting the STAT */
      /* This means that we need to add it to the symbol table here, NOT in the function node */
      /* This is because functionNode needs a stat in its constructor */
      currentSymbolTable = new SymbolTable(currentSymbolTable);
      ParamListNode paramList = (ParamListNode) visit(ctx.paramList());

      /* Function symbol table needs to be added to the parent symbol table*/
      SymbolKey key = new SymbolKey(funcName, true);
      if (currentSymbolTable.getParentSymbolTable().lookup(key) == null) {
        FunctionIdentifier id = new FunctionIdentifier(returnType,
            paramList.getType());
        currentSymbolTable.getParentSymbolTable().add(key, id);
      } else {
        addException(ctx, "Function '" + funcName
            + "' already declared in current scope.");
      }
      currentSymbolTable = currentSymbolTable.getParentSymbolTable();
    }
  }

  @Override
  public Node visitFunc(BasicParser.FuncContext ctx) {
    // need to check if we have a return statement
    if (!ctx.IDENT().getText().equals("main")) {
      String funcName = ctx.IDENT().toString();
      Type returnType = ((TypeNode) visit(ctx.type())).getType();
      /* create new symbol table here*/

      /* Visiting stat should be visiting with the enclosing symbol table, should contain function already*/
      /* In other words, we need to add this function to the symbol table before visiting the STAT */
      /* This means that we need to add it to the symbol table here, NOT in the function node */
      /* This is because functionNode needs a stat in its constructor */
      currentSymbolTable = new SymbolTable(currentSymbolTable);
      ParamListNode paramList = (ParamListNode) visit(ctx.paramList());

      StatNode stat = (StatNode) visit(ctx.stat());
      /* Needs to check current and outer scope*/
      FunctionNode node = new FunctionNode(currentSymbolTable, funcName,
          returnType, paramList, stat);
//            currentSymbolTable = currentSymbolTable.getParentSymbolTable();
      node.check(this, ctx);
      currentSymbolTable = node.getParentSymbolTable();
      return node;
    }
    return null;
  }

  @Override
  public Node visitParamList(BasicParser.ParamListContext ctx) {
    List<ParamNode> params = new ArrayList<>();
    for (BasicParser.ParamContext p : ctx.param()) {
      params.add((ParamNode) visit(p));
    }
    ParamListNode node = new ParamListNode(params);
    return node;
  }

  @Override
  public Node visitParam(BasicParser.ParamContext ctx) {
    Type type = ((TypeNode) visitType(ctx.type())).getType();
    String name = ctx.IDENT().getText();
    ParamNode node = new ParamNode(type, ctx.IDENT().getText());
    SymbolKey key = new SymbolKey(name, false);
    currentSymbolTable.add(key, new ParamIdentifier(type));
    return node;
  }

  @Override
  public Node visitDeclarativeAssignment(
      BasicParser.DeclarativeAssignmentContext ctx) {
    Type type = ((TypeNode) visit(ctx.type())).getType();
    String name = ctx.IDENT().toString();
    SymbolKey key = new SymbolKey(name, false);
    if (currentSymbolTable.lookup(key) != null) {
      addException(ctx,
          "Variable " + name + " was already defined in this scope.");
    }

    ExprNode expr = (ExprNode) visit(ctx.assignRhs());
    VariableNode var = new VariableNode(new IdentifierNode(name));
    var.setType(type);
    AssignmentNode node = new AssignmentNode(var, expr);

    node.check(this, ctx);
    currentSymbolTable.add(key, new VariableIdentifier(type));

    return node;
  }

  @Override
  public Node visitRead(BasicParser.ReadContext ctx) {
//        String name = ctx.assignLhs().getText();
    ExprNode exprNode = (ExprNode) visit(ctx.assignLhs());
    ReadNode node = new ReadNode(exprNode);
    node.check(this, ctx);

    return node;
  }

  @Override
  public Node visitAssignment(BasicParser.AssignmentContext ctx) {
    ExprNode lhs;
    if (ctx.assignLhs().IDENT() != null) {
      lhs = new VariableNode(
          new IdentifierNode(ctx.assignLhs().IDENT().getText()));
      lhs.check(this, ctx);
    } else {
      lhs = (ExprNode) visit(ctx.assignLhs());
    }

    ExprNode rhs = (ExprNode) visit(ctx.assignRhs());
    AssignmentNode node = new AssignmentNode(lhs, rhs);
    node.check(this, ctx);
    return node;
  }

  @Override
  public Node visitSkip(BasicParser.SkipContext ctx) {
    return new SkipNode();
  }


  @Override
  public Node visitWhile(BasicParser.WhileContext ctx) {
    StatNode body = (StatNode) visit(ctx.stat());
    ExprNode expr = (ExprNode) visit(ctx.expr());
    WhileLoopNode node = new WhileLoopNode(expr, body);
    node.check(this, ctx);
    return node;
  }

  @Override
  public Node visitExit(BasicParser.ExitContext ctx) {
    ExitNode node = new ExitNode((ExprNode) visit(ctx.expr()));
    node.check(this, ctx);
    return node;
  }

  @Override
  public Node visitPrint(BasicParser.PrintContext ctx) {
    PrintNode node = new PrintNode((ExprNode) visit(ctx.expr()), false);
    return node;
  }

  @Override
  public Node visitPrintln(BasicParser.PrintlnContext ctx) {
    PrintNode node = new PrintNode((ExprNode) visit(ctx.expr()), true);
    return node;
  }

  @Override
  public Node visitSemi(BasicParser.SemiContext ctx) {
    SequentialCompositionNode node = new SequentialCompositionNode();
    for (BasicParser.StatContext statCtx : ctx.stat()) {
      StatNode statNode = (StatNode) visit(statCtx);
      node.add(statNode);
    }
    return node;
  }

  @Override
  public Node visitFree(BasicParser.FreeContext ctx) {
    MemoryFreeNode node = new MemoryFreeNode((ExprNode) visit(ctx.expr()));
    node.check(this, ctx);
    return node;
  }

  @Override
  public Node visitIf(BasicParser.IfContext ctx) {
    /* Each if and else body has their own scope/symbol table */
    ExprNode expr = (ExprNode) visit(ctx.expr());
    SymbolTable trueBodySymbolTable = new SymbolTable(currentSymbolTable);
    currentSymbolTable = trueBodySymbolTable;
    StatNode trueBody = (StatNode) visit(ctx.stat(0));
    SymbolTable falseBodySymbolTable = new SymbolTable(
        trueBodySymbolTable.getParentSymbolTable());
    currentSymbolTable = falseBodySymbolTable;
    StatNode falseBody = (StatNode) visit(ctx.stat(1));
    currentSymbolTable = falseBodySymbolTable.getParentSymbolTable();
    ConditionalBranchNode node = new ConditionalBranchNode(expr, trueBody,
        falseBody, trueBodySymbolTable, falseBodySymbolTable);
    node.check(this, ctx);
    return node;
  }

  @Override
  public Node visitBegin(BasicParser.BeginContext ctx) {
    currentSymbolTable = new SymbolTable(currentSymbolTable);
    StatNode stat = (StatNode) visit(ctx.stat());
    ScopingNode node = new ScopingNode(currentSymbolTable, stat);
    currentSymbolTable = currentSymbolTable.getParentSymbolTable();
    return node;

  }

  @Override
  public Node visitReturn(BasicParser.ReturnContext ctx) {
    Boolean main = false;
    ParserRuleContext curr = ctx;
    while (!(curr.getParent() instanceof BasicParser.FuncContext)) {
      curr = curr.getParent();
      if (curr instanceof BasicParser.ProgContext) {
        main = true;
        break;
      }
    }

    Type type = null;
    if (!main) {
      Node functionType = visitType(
          ((BasicParser.FuncContext) curr.getParent()).type());
      type = ((TypeNode) functionType).getType();
    }

    FunctionReturnNode node = new FunctionReturnNode(
        (ExprNode) visit(ctx.expr()), main, type);
    node.check(this, ctx);
    return node;
  }

  @Override
  public Node visitAssignLhs(BasicParser.AssignLhsContext ctx) {
    // decide which lhs
    if (ctx.IDENT() != null) {
      VariableNode node = new VariableNode(
          new IdentifierNode(ctx.IDENT().getText()));
      node.check(this, ctx);
      return node;
    } else if (ctx.arrayElem() != null) {
      return this.visit(ctx.arrayElem());
    } else {
      return this.visit(ctx.pairElem());
    }
  }

  @Override
  public Node visitArrayLiterDup(BasicParser.ArrayLiterDupContext ctx) {
    return this.visit(ctx.arrayLiter());
  }

  @Override
  public Node visitNewPair(BasicParser.NewPairContext ctx) {
    ExprNode lhs = (ExprNode) visit(ctx.expr(0));
    ExprNode rhs = (ExprNode) visit(ctx.expr(1));
    return new PairNode(lhs, rhs);
  }

  @Override
  public Node visitPairElemDup(BasicParser.PairElemDupContext ctx) {
    return this.visit(ctx.pairElem());
  }

  @Override
  public Node visitCall(BasicParser.CallContext ctx) {
    IdentifierNode id = new IdentifierNode(ctx.IDENT().toString());
    ArgListNode args = (ArgListNode) visit(ctx.argList());
    CallNode node = new CallNode(id, args);
    node.check(this, ctx);
    return node;
  }


  @Override
  public Node visitArgList(BasicParser.ArgListContext ctx) {
    List<ExprNode> exprNodes = new ArrayList<>();
    for (ExprContext exprCtx : ctx.expr()) {
      ExprNode expr = (ExprNode) visit(exprCtx);
      exprNodes.add(expr);
    }
    ArgListNode argList = new ArgListNode(exprNodes);
    return argList;
  }

  @Override
  public Node visitFstPairElem(BasicParser.FstPairElemContext ctx) {
    ExprNode exprNode = (ExprNode) visit(ctx.expr());
    PairElementNode node = new PairElementNode(PairElementNode.PairPosition.FST,
        exprNode);
    node.check(this, ctx);
    return node;
  }

  @Override
  public Node visitSndPairElem(BasicParser.SndPairElemContext ctx) {
    ExprNode exprNode = (ExprNode) visit(ctx.expr());
    PairElementNode node = new PairElementNode(PairElementNode.PairPosition.SND,
        exprNode);
    node.check(this, ctx);
    return node;
  }

  @Override
  public Node visitType(BasicParser.TypeContext ctx) {

    if (ctx.pairType() != null) {
      return visit(ctx.pairType());
    }

    if (ctx.baseType() != null) {
      return visit(ctx.baseType());
    }

    if (ctx.type() != null) {
      TypeNode elemType = (TypeNode) visit(ctx.type());
      return new TypeNode(new ArrayType(elemType.getType()));
    }

    return new TypeNode(new ErrorType());
  }

  @Override
  public Node visitBaseType(BasicParser.BaseTypeContext ctx) {
    if (ctx.INT() != null) {
      return new TypeNode(new IntType());
    } else if (ctx.BOOL() != null) {
      return new TypeNode(new BoolType());
    } else if (ctx.CHAR() != null) {
      return new TypeNode(new CharType());
    } else if (ctx.STR() != null) {
      return new TypeNode(new StringType());
    }
    return new TypeNode(new ErrorType());
  }

  @Override
  public Node visitArrayType(BasicParser.ArrayTypeContext ctx) {
    TypeNode elemType = (TypeNode) visit(ctx.type());
    return new TypeNode(new ArrayType(elemType.getType()));
  }

  @Override
  public Node visitPairType(BasicParser.PairTypeContext ctx) {
    TypeNode fst = (TypeNode) visit(ctx.pairElemType(0));
    TypeNode snd = (TypeNode) visit(ctx.pairElemType(1));
    TypeNode ret = new TypeNode(new PairType(fst.getType(), snd.getType()));
    return ret;
  }

  @Override
  public Node visitPairElemType(BasicParser.PairElemTypeContext ctx) {
    if (ctx.baseType() != null) {
      return visit(ctx.baseType());
    } else if (ctx.PAIR() != null) {
      return new TypeNode(new PairType(new AnyType(), new AnyType()));
    } else if (ctx.arrayType() != null) {
      TypeNode elemType = (TypeNode) visit(ctx.arrayType());
      return new TypeNode(elemType.getType());
    }
    return new TypeNode(new ErrorType());
  }

  @Override
  public Node visitIdentifier(BasicParser.IdentifierContext ctx) {
    String name = ctx.IDENT().getText();
    VariableNode node = new VariableNode(new IdentifierNode(name));

    /* Check symbol table to see if it exists already. Throws
     * a semantic exception if it does not. Has side-effect of setting
     * its type when calling check(). */
    node.check(this, ctx);

    return node;
  }

  @Override
  public Node visitArrayElemDup(BasicParser.ArrayElemDupContext ctx) {
    return visitArrayElem(ctx.arrayElem());
  }

  @Override
  public Node visitLiteral(BasicParser.LiteralContext ctx) {

    /* Special case, since intLiter is not a terminal node. */
    if (ctx.intLiter() != null) {
      return visitIntLiter(ctx.intLiter());
    }

    ExprNode exprNode;
    TerminalNode typeNode = (TerminalNode) ctx.getChild(0);
    String stringRepresentation = typeNode.getSymbol().getText();

    switch (typeNode.getSymbol().getType()) {
      case BasicLexer.BOOL_LITER:
        exprNode = new BooleanLiteralNode(
            stringRepresentation.equals("true"));
        break;
      case BasicLexer.CHAR_LITER:
        exprNode = new CharacterLiteralNode(stringRepresentation.charAt(1));
        break;
      case BasicLexer.STR_LITER:
        exprNode = new StringLiteralNode(stringRepresentation);
        break;
      case BasicLexer.PAIR_LITER:
        exprNode = new PairLiteralNode();
        break;
      default:
        throw new IllegalStateException("Literal node not defined!");
    }

    return exprNode;
  }

  @Override
  public Node visitUnOpApplication(BasicParser.UnOpApplicationContext ctx) {
    /* Determine operator. */
    unaryOperators operator;
    TerminalNode typeNode = (TerminalNode) ctx.getChild(0);
    switch (typeNode.getSymbol().getType()) {
      case BasicLexer.NOT:
        operator = unaryOperators.LOGICAL_NOT;
        break;
      case BasicLexer.MINUS:
        operator = unaryOperators.MATH_NEGATION;
        break;
      case BasicLexer.LEN:
        operator = unaryOperators.LEN;
        break;
      case BasicLexer.ORD:
        operator = unaryOperators.ORD;
        break;
      case BasicLexer.CHR:
        operator = unaryOperators.CHR;
        break;
      default:
        throw new IllegalStateException("Invalid operator passed to"
            + "Unary Operator context!");
    }

    ExprNode exprNode = (ExprNode) visit(ctx.expr());
    UnaryOperatorNode unaryOperatorNode =
        new UnaryOperatorNode(operator, exprNode);

    unaryOperatorNode.check(this, ctx);

    return unaryOperatorNode;
  }

  @Override
  public Node visitBrackets(BasicParser.BracketsContext ctx) {
    return visit(ctx.expr());
  }

  @Override
  public Node visitArrayElem(BasicParser.ArrayElemContext ctx) {

    /* Visit all expression nodes contained within this arrayElem. */
    List<ExprNode> exprNodes = new ArrayList<>();
    for (ExprContext context : ctx.expr()) {
      exprNodes.add((ExprNode) visit(context));
    }

    /* Visit variable node. */
    VariableNode variableNode;
    String varName = ctx.IDENT().getText();
    variableNode = new VariableNode(new IdentifierNode(varName));
    variableNode.check(this, ctx);

    ArrayElementNode arrayElementNode = new ArrayElementNode(exprNodes,
        variableNode);

    arrayElementNode.check(this, ctx);

    return arrayElementNode;
  }

  @Override
  public Node visitArrayLiter(BasicParser.ArrayLiterContext ctx) {
    List<ExprNode> exprNodes = new ArrayList<>();
    for (ExprContext context : ctx.expr()) {
      exprNodes.add((ExprNode) visit(context));
    }
    ArrayLiteralNode arrayLiteralNode = new ArrayLiteralNode(exprNodes);
    arrayLiteralNode.check(this, ctx);
    return arrayLiteralNode;
  }

  @Override
  public Node visitBinOp1Application(BinOp1ApplicationContext ctx) {
    /* Determine operator. */
    BinaryOperatorNode.binaryOperators operator;
    TerminalNode typeNode = (TerminalNode) ctx.getChild(1);
    switch (typeNode.getSymbol().getType()) {
      case BasicLexer.MUL:
        operator = binaryOperators.MUL;
        break;
      case BasicLexer.DIV:
        operator = binaryOperators.DIV;
        break;
      case BasicLexer.MOD:
        operator = binaryOperators.MOD;
        break;
      default:
        throw new IllegalStateException("Invalid operator passed to"
            + "binOp1 context!");
    }

    ExprNode leftExpr = (ExprNode) visit(ctx.expr(0));
    ExprNode rightExpr = (ExprNode) visit(ctx.expr(1));

    BinaryOperatorNode binaryOperatorNode = new BinaryOperatorNode(operator,
        leftExpr, rightExpr);

    binaryOperatorNode.check(this, ctx);

    return binaryOperatorNode;
  }

  @Override
  public Node visitBinOp2Application(BinOp2ApplicationContext ctx) {
    /* Determine operator. */
    BinaryOperatorNode.binaryOperators operator;
    TerminalNode typeNode = (TerminalNode) ctx.getChild(1);
    switch (typeNode.getSymbol().getType()) {
      case BasicLexer.PLUS:
        operator = binaryOperators.PLUS;
        break;
      case BasicLexer.MINUS:
        operator = binaryOperators.MINUS;
        break;
      case BasicLexer.MOD:
        operator = binaryOperators.MOD;
        break;
      default:
        throw new IllegalStateException("Invalid operator passed to"
            + "binOp1 context!");
    }

    ExprNode leftExpr = (ExprNode) visit(ctx.expr(0));
    ExprNode rightExpr = (ExprNode) visit(ctx.expr(1));

    BinaryOperatorNode binaryOperatorNode = new BinaryOperatorNode(operator,
        leftExpr, rightExpr);

    binaryOperatorNode.check(this, ctx);

    return binaryOperatorNode;
  }

  @Override
  public Node visitBinOp3Application(BinOp3ApplicationContext ctx) {
    /* Determine operator. */
    BinaryOperatorNode.binaryOperators operator;
    TerminalNode typeNode = (TerminalNode) ctx.getChild(1);
    switch (typeNode.getSymbol().getType()) {
      case BasicLexer.GT:
        operator = binaryOperators.GT;
        break;
      case BasicLexer.GTE:
        operator = binaryOperators.GTE;
        break;
      case BasicLexer.LT:
        operator = binaryOperators.LT;
        break;
      case BasicLexer.LTE:
        operator = binaryOperators.LTE;
        break;
      default:
        throw new IllegalStateException("Invalid operator passed to"
            + "binOp1 context!");
    }

    ExprNode leftExpr = (ExprNode) visit(ctx.expr(0));
    ExprNode rightExpr = (ExprNode) visit(ctx.expr(1));

    BinaryOperatorNode binaryOperatorNode = new BinaryOperatorNode(operator,
        leftExpr, rightExpr);

    binaryOperatorNode.check(this, ctx);

    return binaryOperatorNode;
  }

  @Override
  public Node visitBinOp4Application(BinOp4ApplicationContext ctx) {
    /* Determine operator. */
    BinaryOperatorNode.binaryOperators operator;
    TerminalNode typeNode = (TerminalNode) ctx.getChild(1);
    switch (typeNode.getSymbol().getType()) {
      case BasicLexer.EQ:
        operator = binaryOperators.EQ;
        break;
      case BasicLexer.NEQ:
        operator = binaryOperators.NEQ;
        break;
      default:
        throw new IllegalStateException("Invalid operator passed to"
            + "binOp1 context!");
    }

    ExprNode leftExpr = (ExprNode) visit(ctx.expr(0));
    ExprNode rightExpr = (ExprNode) visit(ctx.expr(1));

    BinaryOperatorNode binaryOperatorNode = new BinaryOperatorNode(operator,
        leftExpr, rightExpr);

    binaryOperatorNode.check(this, ctx);

    return binaryOperatorNode;
  }

  @Override
  public Node visitBinOp5Application(BinOp5ApplicationContext ctx) {
    /* Only one operator with precedence 5 : AND. */
    TerminalNode typeNode = (TerminalNode) ctx.getChild(1);
    if (typeNode.getSymbol().getType() != BasicLexer.AND) {
      throw new IllegalStateException("Invalid operator passed to"
          + "binOp1 context!");
    }

    ExprNode leftExpr = (ExprNode) visit(ctx.expr(0));
    ExprNode rightExpr = (ExprNode) visit(ctx.expr(1));

    BinaryOperatorNode binaryOperatorNode = new BinaryOperatorNode(
        binaryOperators.AND,
        leftExpr, rightExpr);

    binaryOperatorNode.check(this, ctx);

    return binaryOperatorNode;
  }

  @Override
  public Node visitBinOp6Application(BinOp6ApplicationContext ctx) {
    /* Only one operator with precedence 5 : AND. */
    TerminalNode typeNode = (TerminalNode) ctx.getChild(1);
    if (typeNode.getSymbol().getType() != BasicLexer.OR) {
      throw new IllegalStateException("Invalid operator passed to"
          + "binOp1 context!");
    }

    ExprNode leftExpr = (ExprNode) visit(ctx.expr(0));
    ExprNode rightExpr = (ExprNode) visit(ctx.expr(1));

    BinaryOperatorNode binaryOperatorNode = new BinaryOperatorNode(
        binaryOperators.OR,
        leftExpr, rightExpr);

    binaryOperatorNode.check(this, ctx);

    return binaryOperatorNode;
  }

  @Override
  public Node visitIntLiter(IntLiterContext ctx) {
    long value;

    try {
      value = Long.parseLong(ctx.INTEGER().getText());
    } catch (NumberFormatException e) {
      throw new SyntaxException("Integer is of value: " +
          ctx.INTEGER().getText() +
          ", but must be between " + IntType.INT_MIN +
          " and " + IntType.INT_MAX,
          ctx.getStart().getLine(),
          ctx.getStart().getCharPositionInLine());
    }

    /* Parse sign. Int literals without any sign are implicitly positive. */
    if (ctx.intSign() != null) {
      if (ctx.intSign().getText().equals("-")) {
        value = -value;
      }
    }

    IntLiteralNode node = new IntLiteralNode(value);
    node.check(this, ctx);

    return node;
  }
}
