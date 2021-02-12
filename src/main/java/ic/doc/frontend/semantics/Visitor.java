package ic.doc.frontend.semantics;

import ic.doc.frontend.errors.SemanticErrorList;
import ic.doc.frontend.errors.SyntaxException;
import ic.doc.antlr.BasicLexer;
import ic.doc.antlr.BasicParser;
import ic.doc.antlr.BasicParser.*;
import ic.doc.antlr.BasicParserBaseVisitor;

import ic.doc.frontend.nodes.ArgListNode;
import ic.doc.frontend.nodes.exprnodes.*;
import ic.doc.frontend.nodes.exprnodes.Literals.*;
import ic.doc.frontend.nodes.exprnodes.BinaryOperatorNode.binaryOperators;
import ic.doc.frontend.nodes.exprnodes.UnaryOperatorNode.unaryOperators;
import ic.doc.frontend.identifiers.*;
import ic.doc.frontend.nodes.FunctionNode;
import ic.doc.frontend.nodes.Node;
import ic.doc.frontend.nodes.ParamListNode;
import ic.doc.frontend.nodes.ParamNode;
import ic.doc.frontend.nodes.ProgNode;
import ic.doc.frontend.nodes.statnodes.*;
import ic.doc.frontend.types.*;

import ic.doc.frontend.nodes.TypeNode;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

public class Visitor extends BasicParserBaseVisitor<Node> {

  private SymbolTable currentSymbolTable;

  private SemanticErrorList semanticErrorList;

  public SymbolTable getCurrentSymbolTable() {
    return currentSymbolTable;
  }

  public SemanticErrorList getSemanticErrorList() {
    return semanticErrorList;
  }

  /* Called once at the start of any program eg. begin FUNCTIONS STATEMENT end
     initialises symbol table an semantic list */
  @Override
  public Node visitProg(BasicParser.ProgContext ctx) {
    currentSymbolTable = new SymbolTable(null);
    semanticErrorList = new SemanticErrorList();

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

  /* Helper function to called to declare functions, adds to symbol table if function name is not already defined */
  private void declareFunction(BasicParser.FuncContext ctx) {
    if (!ctx.IDENT().getText().equals("main")) {
      String funcName = ctx.IDENT().toString();
      Type returnType = ((TypeNode) visit(ctx.type())).getType();

      ParamListNode paramList = (ParamListNode) visit(ctx.paramList());

      SymbolKey key = new SymbolKey(funcName, true);
      if (currentSymbolTable.lookup(key) == null) {
        FunctionIdentifier id = new FunctionIdentifier(returnType,
            paramList.getType());
        currentSymbolTable.add(key, id);
      } else {
        semanticErrorList.addException(ctx, "Function '" + funcName
            + "' already declared in current scope.");
      }
    }
  }

  /* Called during function declaration eg. int myFunction(int a, int b) is STAT end
     Adds params to symbol table  and creates a function node */
  @Override
  public Node visitFunc(BasicParser.FuncContext ctx) {

    if (!ctx.IDENT().getText().equals("main")) {
      String funcName = ctx.IDENT().toString();
      Type returnType = ((TypeNode) visit(ctx.type())).getType();

      /* create new symbol table here*/
      currentSymbolTable = new SymbolTable(currentSymbolTable);

      /* Visiting stat should be visiting with the enclosing symbol table, should contain function already*/
      /* In other words, we need to add this function to the symbol table before visiting the STAT */
      /* This means that we need to add it to the symbol table here, NOT in the function node */
      /* This is because functionNode needs a stat in its constructor */

      ParamListNode paramList = (ParamListNode) visit(ctx.paramList());
      List<ParamNode> params = paramList.getParams();
      for (ParamNode p : params) {
        String name = p.getInput();
        Type type = p.getType();
        SymbolKey key = new SymbolKey(name, false);
        currentSymbolTable.add(key, new ParamIdentifier(type));
      }
      StatNode stat = (StatNode) visit(ctx.stat());
      /* Needs to check current and outer scope*/
      FunctionNode node = new FunctionNode(currentSymbolTable, funcName,
          returnType, paramList, stat);
      node.check(this, ctx);
      currentSymbolTable = node.getParentSymbolTable();
      return node;
    }
    return null;
  }

  /* Called when a parameter list is reached. eg. FUNCTION_NAME(int a, int b)
     Iterates through the params and creates a paramList node */
  @Override
  public Node visitParamList(BasicParser.ParamListContext ctx) {
    List<ParamNode> params = new ArrayList<>();
    for (BasicParser.ParamContext p : ctx.param()) {
      params.add((ParamNode) visit(p));
    }
    return new ParamListNode(params);
  }

  /* Called when a parameter is reached. eg. FUNCTION_NAME(int a, PARAM)
     Gets the type of parameter and creates a param node */
  @Override
  public Node visitParam(BasicParser.ParamContext ctx) {
    Type type = ((TypeNode) visit(ctx.type())).getType();
    return new ParamNode(type, ctx.IDENT().getText());
  }

  /* Called when declaring a variable while assigning it to a value. eg. int i = 5
     Adds variable to symbol table if not already defined in current scope and creates an assignment node*/
  @Override
  public Node visitDeclarativeAssignment(
      BasicParser.DeclarativeAssignmentContext ctx) {
    Type type = ((TypeNode) visit(ctx.type())).getType();
    String name = ctx.IDENT().toString();
    VariableNode var = new VariableNode(name);
    var.setType(type);
    ExprNode expr = (ExprNode) visit(ctx.assignRhs());
    AssignmentNode node = new AssignmentNode(var, expr, true);

    node.check(this, ctx);

    return node;
  }

  /* Called when assigning a value from standard input into an expression eg. read i
     Creates read node with the expression */
  @Override
  public Node visitRead(BasicParser.ReadContext ctx) {
    ExprNode exprNode = (ExprNode) visit(ctx.assignLhs());
    ReadNode node = new ReadNode(exprNode);
    node.check(this, ctx);

    return node;
  }

  /* Called when assigning a variable to a value. eg. i = 5
     Looks up variable in symbol table and creates an assignment node*/
  @Override
  public Node visitAssignment(BasicParser.AssignmentContext ctx) {
    ExprNode lhs = (ExprNode) visit(ctx.assignLhs());
    ExprNode rhs = (ExprNode) visit(ctx.assignRhs());
    AssignmentNode node = new AssignmentNode(lhs, rhs, false);
    node.check(this, ctx);
    return node;
  }

  /* Called when a statement that does nothing is required by syntax eg. skip
     Creates a skip node*/
  @Override
  public Node visitSkip(BasicParser.SkipContext ctx) {
    return new SkipNode();
  }

  /* Called when a while loop is reached eg. while true do skip done
     Creates a while loop node*/
  @Override
  public Node visitWhile(BasicParser.WhileContext ctx) {
    StatNode body = (StatNode) visit(ctx.stat());
    ExprNode expr = (ExprNode) visit(ctx.expr());
    WhileLoopNode node = new WhileLoopNode(expr, body);
    node.check(this, ctx);
    return node;
  }

  /* Called to exit with an exit code eg. exit 5
     Creates an exit node*/
  @Override
  public Node visitExit(BasicParser.ExitContext ctx) {
    ExitNode node = new ExitNode((ExprNode) visit(ctx.expr()));
    node.check(this, ctx);
    return node;
  }

  /* Called when printing an expression eg. print 5
     Creates a print node*/
  @Override
  public Node visitPrint(BasicParser.PrintContext ctx) {
    return new PrintNode((ExprNode) visit(ctx.expr()), false);
  }

  /* Called when a printing an expression with a new line after  eg. println 5
     Creates a print node*/
  @Override
  public Node visitPrintln(BasicParser.PrintlnContext ctx) {
    return new PrintNode((ExprNode) visit(ctx.expr()), true);
  }

  /* Called when statements are to be executed in sequence eg. print 5; print 6
     Iterates through the statements and creates a sequential composition node node*/
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
    boolean main = false;
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
      Node functionType = visit(
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
      VariableNode node = new VariableNode(ctx.IDENT().getText());
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
    ArgListNode args = (ArgListNode) visit(ctx.argList());
    CallNode node = new CallNode(ctx.IDENT().toString(), args);
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
    return new ArgListNode(exprNodes);
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
  public Node visitPairTypeDup(BasicParser.PairTypeDupContext ctx) {
    return visit(ctx.pairType());
  }

  @Override
  public Node visitBaseTypeDup(BasicParser.BaseTypeDupContext ctx) {
    return visit(ctx.baseType());
  }

  @Override
  public Node visitArrayTypeDup(BasicParser.ArrayTypeDupContext ctx) {
    TypeNode elemType = (TypeNode) visit(ctx.type());
    return new TypeNode(new ArrayType(elemType.getType()));
  }

  @Override
  public Node visitIntType(BasicParser.IntTypeContext ctx) {
    return new TypeNode(new IntType());
  }

  @Override
  public Node visitBoolType(BasicParser.BoolTypeContext ctx) {
    return new TypeNode(new BoolType());
  }

  @Override
  public Node visitCharType(BasicParser.CharTypeContext ctx) {
    return new TypeNode(new CharType());
  }

  @Override
  public Node visitStrType(BasicParser.StrTypeContext ctx) {
    return new TypeNode(new StringType());
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
    return new TypeNode(new PairType(fst.getType(), snd.getType()));
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
    VariableNode node = new VariableNode(name);

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
    variableNode = new VariableNode(varName);
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
