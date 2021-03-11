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
import ic.doc.frontend.nodes.exprnodes.BinaryOperatorNode.BinaryOperators;
import ic.doc.frontend.nodes.exprnodes.UnaryOperatorNode.UnaryOperators;
import ic.doc.frontend.identifiers.*;
import ic.doc.frontend.nodes.*;
import ic.doc.frontend.nodes.statnodes.*;
import ic.doc.frontend.types.*;

import ic.doc.frontend.nodes.TypeNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import static ic.doc.frontend.nodes.ImportVisitorNode.magicallyParse;

public class Visitor extends BasicParserBaseVisitor<Node> {

  private SymbolTable currentSymbolTable;

  private SemanticErrorList semanticErrorList;

  public SymbolTable getCurrentSymbolTable() {
    return currentSymbolTable;
  }

  public SemanticErrorList getSemanticErrorList() {
    return semanticErrorList;
  }

  String baseDirectory;

  public Visitor(String baseDirectory){
    this.baseDirectory = baseDirectory;
  }

  /* Called once at the start of any program eg. begin FUNCTIONS STATEMENT end
  Initialises symbol table an semantic list */
  @Override
  public Node visitProg(BasicParser.ProgContext ctx) {
    currentSymbolTable = new SymbolTable(null);
    semanticErrorList =
        new SemanticErrorList(ctx.getStart().getInputStream().toString().split("\n"));
    List<IncludeContext> includeCtxs = ctx.include();
    List<FuncContext> functionCtxs = ctx.func();
    List<FunctionNode> functionNodes = new ArrayList<>();

    List<String> imports = new ArrayList<>();

    for(IncludeContext i : includeCtxs){
      ImportNode node = (ImportNode) visit(i);
      String fileDirectory = baseDirectory + node.getFileName();
      System.out.println(fileDirectory);
      imports.add(fileDirectory);
    }

    List<FuncContext> importFunctions = new ArrayList<>();
    for(String file : imports){
      try {
        List<BasicParser.FuncContext> funcCtxs = magicallyParse(file);
        importFunctions.addAll(funcCtxs);
      } catch(IllegalArgumentException e){
        semanticErrorList.addException(ctx, e.getMessage());
      } catch(IOException e){
        // idk what to do here bro
      }
    }

    for (FuncContext f : importFunctions){
      declareFunction(f);
    }

    for (FuncContext f : functionCtxs) {
      declareFunction(f);
    }


    for (FuncContext f : importFunctions){
      functionNodes.add((FunctionNode) visit(f));
    }

    for (FuncContext f : functionCtxs) {
      functionNodes.add((FunctionNode) visit(f));
    }

    StatNode statNode = (StatNode) visit(ctx.stat());

    return new ProgNode(currentSymbolTable, functionNodes, statNode);
  }

  @Override
  public Node visitInclude(BasicParser.IncludeContext ctx) {
    String fileName = ctx.FILE_NAME().getText();
    /* Remove start and end quotes of file name */
    fileName = fileName.substring(1, fileName.length() - 1);
    return new ImportNode(fileName);
  }
  /* Helper function to called to declare functions, adds to symbol table if function name is not already defined */
  private void declareFunction(BasicParser.FuncContext ctx) {
    if (!ctx.IDENT().getText().equals("main")) {
      String funcName = ctx.IDENT().toString();
      Type returnType = ((TypeNode) visit(ctx.type())).getType();

      ParamListNode paramList = (ParamListNode) visit(ctx.paramList());

      SymbolKey key = new SymbolKey(funcName, true);
      if (currentSymbolTable.lookup(key) == null) {
        FunctionIdentifier id = new FunctionIdentifier(returnType, paramList.getType());
        currentSymbolTable.add(key, id);
      } else {
        semanticErrorList.addScopeException(ctx, true, "Function", "'" + funcName + "'");
      }
    }
  }

  /* Called during function declaration eg. int myFunction(int a, int b) is STAT end
  Iterates through params to add them to symbol table and creates a function node */
  @Override
  public Node visitFunc(BasicParser.FuncContext ctx) {

    if (!ctx.IDENT().getText().equals("main")) {
      String funcName = ctx.IDENT().toString();
      Type returnType = ((TypeNode) visit(ctx.type())).getType();

      /* create new symbol table here*/
      currentSymbolTable = new SymbolTable(currentSymbolTable);

      ParamListNode paramList = (ParamListNode) visit(ctx.paramList());
      List<ParamNode> params = paramList.getParams();
      for (ParamNode p : params) {
        String name = p.getInput();
        Type type = p.getType();
        SymbolKey key = new SymbolKey(name, false);
        currentSymbolTable.add(key, new VariableIdentifier(type));
      }
      StatNode stat = (StatNode) visit(ctx.stat());
      /* Needs to check current and outer scope*/
      FunctionNode node =
          new FunctionNode(currentSymbolTable, funcName, returnType, paramList, stat);
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
  Adds variable to symbol table if not already defined in current scope and creates an assignment node */
  @Override
  public Node visitDeclarativeAssignment(BasicParser.DeclarativeAssignmentContext ctx) {
    Type type = ((TypeNode) visit(ctx.type())).getType();
    String name = ctx.IDENT().toString();
    VariableNode var = new VariableNode(name);
    var.setType(type);
    ExprNode expr = (ExprNode) visit(ctx.assignRhs());
    AssignmentNode node = new AssignmentNode(var, expr, true, currentSymbolTable);
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
  Looks up variable in symbol table and creates an assignment node */
  @Override
  public Node visitAssignment(BasicParser.AssignmentContext ctx) {
    ExprNode lhs = (ExprNode) visit(ctx.assignLhs());
    ExprNode rhs = (ExprNode) visit(ctx.assignRhs());
    AssignmentNode node = new AssignmentNode(lhs, rhs, false, currentSymbolTable);
    node.check(this, ctx);
    return node;
  }

  /* Called when a statement that does nothing is required by syntax eg. skip
  Creates a skip node */
  @Override
  public Node visitSkip(BasicParser.SkipContext ctx) {
    return new SkipNode();
  }

  /* Called when a while loop is reached eg. while true do skip done
  Creates a while loop node */
  @Override
  public Node visitWhile(BasicParser.WhileContext ctx) {
    ExprNode expr = (ExprNode) visit(ctx.expr());

    SymbolTable whileBodySymbolTable = new SymbolTable(currentSymbolTable);
    currentSymbolTable = whileBodySymbolTable;
    StatNode body = (StatNode) visit(ctx.stat());
    currentSymbolTable = whileBodySymbolTable.getParentSymbolTable();

    WhileLoopNode node = new WhileLoopNode(expr, body, whileBodySymbolTable);
    node.check(this, ctx);
    return node;
  }

  /* Called to exit with an exit code eg. exit 5
  Creates an exit node */
  @Override
  public Node visitExit(BasicParser.ExitContext ctx) {
    ExitNode node = new ExitNode((ExprNode) visit(ctx.expr()));
    node.check(this, ctx);
    return node;
  }

  /* Called when printing an expression eg. print 5
  Creates a print node */
  @Override
  public Node visitPrint(BasicParser.PrintContext ctx) {
    return new PrintNode((ExprNode) visit(ctx.expr()), false);
  }

  /* Called when a printing an expression with a new line after  eg. println 5
  Creates a print node */
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

  /* Called when a freeing a pair or array from memory eg. free x
  Creates a memory free node*/
  @Override
  public Node visitFree(BasicParser.FreeContext ctx) {
    MemoryFreeNode node = new MemoryFreeNode((ExprNode) visit(ctx.expr()));
    node.check(this, ctx);
    return node;
  }

  /* Called when a reaching an if loop eg. if b then print b else print a fi
  Creates a new symbol table for both if and else bodies and creates a conditional branch node*/
  @Override
  public Node visitIf(BasicParser.IfContext ctx) {

    /* Each if and else body has their own scope/symbol table */
    ExprNode expr = (ExprNode) visit(ctx.expr());
    SymbolTable trueBodySymbolTable = new SymbolTable(currentSymbolTable);
    currentSymbolTable = trueBodySymbolTable;
    StatNode trueBody = (StatNode) visit(ctx.stat(0));
    SymbolTable falseBodySymbolTable = new SymbolTable(trueBodySymbolTable.getParentSymbolTable());
    currentSymbolTable = falseBodySymbolTable;
    StatNode falseBody = (StatNode) visit(ctx.stat(1));
    currentSymbolTable = falseBodySymbolTable.getParentSymbolTable();
    ConditionalBranchNode node =
        new ConditionalBranchNode(
            expr, trueBody, falseBody, trueBodySymbolTable, falseBodySymbolTable);
    node.check(this, ctx);
    return node;
  }

  /* Called when declaring a new scope  eg. begin STATEMENT end
  Creates a new symbol table for the new scope and creates a scoping node*/
  @Override
  public Node visitBegin(BasicParser.BeginContext ctx) {
    currentSymbolTable = new SymbolTable(currentSymbolTable);
    StatNode stat = (StatNode) visit(ctx.stat());
    ScopingNode node = new ScopingNode(currentSymbolTable, stat);
    currentSymbolTable = currentSymbolTable.getParentSymbolTable();
    return node;
  }

  /* Called when returning a value from a non-main function eg. return true
  Checks if function is non-main and creates a function return node*/
  @Override
  public Node visitReturn(BasicParser.ReturnContext ctx) {
    String functionName = "";
    ParserRuleContext curr = ctx;
    while (!(curr.getParent() instanceof BasicParser.FuncContext)) {
      curr = curr.getParent();
      if (curr instanceof BasicParser.ProgContext) {
        functionName = "main";
        break;
      }
    }

    Type type = null;
    if (functionName.equals("")) {
      functionName = ((FuncContext) curr.getParent()).IDENT().getText();
      Node functionType = visit(((BasicParser.FuncContext) curr.getParent()).type());
      type = ((TypeNode) functionType).getType();
    }

    FunctionReturnNode node = new FunctionReturnNode((ExprNode) visit(ctx.expr()), functionName, type);
    node.check(this, ctx);
    return node;
  }

  /* Called when assigning the target in an assignment  eg. x = EXPRESSION
  Determines variable type and creates a variable node*/
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

  /* Duplicate visit due to labeling in parsing, calls visit on arrayLiter */
  @Override
  public Node visitArrayLiterDup(BasicParser.ArrayLiterDupContext ctx) {
    return this.visit(ctx.arrayLiter());
  }

  /* Called when assigning value of a pair constructor eg. newpair(x, y)
  Creates a pair node*/
  @Override
  public Node visitNewPair(BasicParser.NewPairContext ctx) {
    ExprNode lhs = (ExprNode) visit(ctx.expr(0));
    ExprNode rhs = (ExprNode) visit(ctx.expr(1));
    return new PairNode(lhs, rhs);
  }

  /* Duplicate visit due to labeling in parsing, calls visit on the pairElem */
  @Override
  public Node visitPairElemDup(BasicParser.PairElemDupContext ctx) {
    return this.visit(ctx.pairElem());
  }

  /* Called when assigning value of function return eg. call increment(int x)
  Creates a call node*/
  @Override
  public Node visitCall(BasicParser.CallContext ctx) {
    ArgListNode args = (ArgListNode) visit(ctx.argList());
    CallNode node = new CallNode(ctx.IDENT().toString(), args);
    node.check(this, ctx);
    return node;
  }

  /* Called when argument list is reached eg. FUNCTION(int i, bool b)
  Iterates through the arguments and creates an arg list node*/
  @Override
  public Node visitArgList(BasicParser.ArgListContext ctx) {
    List<ExprNode> exprNodes = new ArrayList<>();
    for (ExprContext exprCtx : ctx.expr()) {
      ExprNode expr = (ExprNode) visit(exprCtx);
      exprNodes.add(expr);
    }
    return new ArgListNode(exprNodes);
  }

  /* Called when accessing first element of a pair eg. fst pair
  Creates a pair elem node*/
  @Override
  public Node visitFstPairElem(BasicParser.FstPairElemContext ctx) {
    ExprNode exprNode = (ExprNode) visit(ctx.expr());
    PairElementNode node = new PairElementNode(PairElementNode.PairPosition.FST, exprNode);
    node.check(this, ctx);
    return node;
  }

  /* Called when accessing first element of a pair eg. snd pair
  Creates a pair elem node*/
  @Override
  public Node visitSndPairElem(BasicParser.SndPairElemContext ctx) {
    ExprNode exprNode = (ExprNode) visit(ctx.expr());
    PairElementNode node = new PairElementNode(PairElementNode.PairPosition.SND, exprNode);
    node.check(this, ctx);
    return node;
  }

  /* Duplicate visit due to labeling in parsing, calls visit on the pairType */
  @Override
  public Node visitPairTypeDup(BasicParser.PairTypeDupContext ctx) {
    return visit(ctx.pairType());
  }

  /* Duplicate visit due to labeling in parsing, calls visit on the baseType */
  @Override
  public Node visitBaseTypeDup(BasicParser.BaseTypeDupContext ctx) {
    return visit(ctx.baseType());
  }

  /* Duplicate visit due to labeling in parsing, calls visit on the ArrayType */
  @Override
  public Node visitArrayTypeDup(BasicParser.ArrayTypeDupContext ctx) {
    TypeNode elemType = (TypeNode) visit(ctx.type());
    return new TypeNode(new ArrayType(elemType.getType()));
  }

  /* Called when the int type is reached eg. int
  Creates a type node*/
  @Override
  public Node visitIntType(BasicParser.IntTypeContext ctx) {
    return new TypeNode(new IntType());
  }

  /* Called when the bool type is reached eg. bool
  Creates a type node*/
  @Override
  public Node visitBoolType(BasicParser.BoolTypeContext ctx) {
    return new TypeNode(new BoolType());
  }

  /* Called when the char type is reached eg. char
  Creates a type node*/
  @Override
  public Node visitCharType(BasicParser.CharTypeContext ctx) {
    return new TypeNode(new CharType());
  }

  /* Called when the str type is reached eg. str
  Creates a type node*/
  @Override
  public Node visitStrType(BasicParser.StrTypeContext ctx) {
    return new TypeNode(new StringType());
  }

  /* Called when the array type is reached eg. int []
  Creates a type node*/
  @Override
  public Node visitArrayType(BasicParser.ArrayTypeContext ctx) {
    TypeNode elemType = (TypeNode) visit(ctx.type());
    return new TypeNode(new ArrayType(elemType.getType()));
  }

  /* Called when the pair type is reached eg. pair (int, bool)
  Creates a type node*/
  @Override
  public Node visitPairType(BasicParser.PairTypeContext ctx) {
    TypeNode fst = (TypeNode) visit(ctx.pairElemType(0));
    TypeNode snd = (TypeNode) visit(ctx.pairElemType(1));
    return new TypeNode(new PairType(fst.getType(), snd.getType()));
  }

  /* Called when the type of the individual pair element is reached eg. pair (int, TYPE)
  Determines the subtype and creates a type node*/
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

  /* Called when an identifier is reached eg. increment
  Creates a variable node*/
  @Override
  public Node visitIdentifier(BasicParser.IdentifierContext ctx) {
    String name = ctx.IDENT().getText();
    VariableNode node = new VariableNode(name);

    /* Has side-effect of setting
     * its type when calling check(). */
    node.check(this, ctx);

    return node;
  }

  /* Duplicate visit due to labeling in parsing, calls visit on the ArrayElemType */
  @Override
  public Node visitArrayElemDup(BasicParser.ArrayElemDupContext ctx) {
    return visitArrayElem(ctx.arrayElem());
  }

  /* Called when a type literal is reached eg. true
  Determines the type of the literal and creates the corresponding node*/
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
        exprNode = new BooleanLiteralNode(stringRepresentation.equals("true"));
        break;
      case BasicLexer.CHAR_LITER:
        if (stringRepresentation.charAt(1) == '\\') {
          exprNode = new CharacterLiteralNode(stringRepresentation.charAt(2));
        } else {
          exprNode = new CharacterLiteralNode(stringRepresentation.charAt(1));
        }
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

  /* Called when applying an unary operator to an expression eg. not b
  Determines the subtype and creates a unary operator node*/
  @Override
  public Node visitUnOpApplication(BasicParser.UnOpApplicationContext ctx) {

    /* Determine operator. */
    UnaryOperators operator;
    TerminalNode typeNode = (TerminalNode) ctx.getChild(0);
    switch (typeNode.getSymbol().getType()) {
      case BasicLexer.NOT:
        operator = UnaryOperators.LOGICAL_NOT;
        break;
      case BasicLexer.MINUS:
        operator = UnaryOperators.MATH_NEGATION;
        break;
      case BasicLexer.LEN:
        operator = UnaryOperators.LEN;
        break;
      case BasicLexer.ORD:
        operator = UnaryOperators.ORD;
        break;
      case BasicLexer.CHR:
        operator = UnaryOperators.CHR;
        break;
      default:
        throw new IllegalStateException("Invalid operator passed to" + "Unary Operator context!");
    }

    ExprNode exprNode = (ExprNode) visit(ctx.expr());
    UnaryOperatorNode unaryOperatorNode = new UnaryOperatorNode(operator, exprNode);

    unaryOperatorNode.check(this, ctx);

    return unaryOperatorNode;
  }

  /* Called when brackets are reached eg. (EXPRESSION)
  Visits the inner expression */
  @Override
  public Node visitBrackets(BasicParser.BracketsContext ctx) {
    return visit(ctx.expr());
  }

  /* Called when accessing specific element of an array eg. cars[5]
  Iterates through expressions , checks the identifier and creates a array element node*/
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

    ArrayElementNode arrayElementNode = new ArrayElementNode(exprNodes, variableNode);

    arrayElementNode.check(this, ctx);

    return arrayElementNode;
  }

  /* Called when an array literal is reached eg. [1,2,3]
  Iterates through the expressions and creates a array literal node*/
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

  /* Called when applying a binary operation of precedence 1 to 2 expressions eg. 5 * 6
  Creates a binary operator node*/
  @Override
  public Node visitBinOp1Application(BinOp1ApplicationContext ctx) {
    /* Determine operator. */
    BinaryOperators operator;
    TerminalNode typeNode = (TerminalNode) ctx.getChild(1);
    switch (typeNode.getSymbol().getType()) {
      case BasicLexer.MUL:
        operator = BinaryOperators.MUL;
        break;
      case BasicLexer.DIV:
        operator = BinaryOperators.DIV;
        break;
      case BasicLexer.MOD:
        operator = BinaryOperators.MOD;
        break;
      default:
        throw new IllegalStateException("Invalid operator passed to" + "binOp1 context!");
    }

    ExprNode leftExpr = (ExprNode) visit(ctx.expr(0));
    ExprNode rightExpr = (ExprNode) visit(ctx.expr(1));

    BinaryOperatorNode binaryOperatorNode = new BinaryOperatorNode(operator, leftExpr, rightExpr);

    binaryOperatorNode.check(this, ctx);

    return binaryOperatorNode;
  }

  /* Called when applying a binary operation of precedence 2 to 2 expressions eg. 5 + 6
  Creates a binary operator node*/
  @Override
  public Node visitBinOp2Application(BinOp2ApplicationContext ctx) {
    /* Determine operator. */
    BinaryOperators operator;
    TerminalNode typeNode = (TerminalNode) ctx.getChild(1);
    switch (typeNode.getSymbol().getType()) {
      case BasicLexer.PLUS:
        operator = BinaryOperators.PLUS;
        break;
      case BasicLexer.MINUS:
        operator = BinaryOperators.MINUS;
        break;
      case BasicLexer.MOD:
        operator = BinaryOperators.MOD;
        break;
      default:
        throw new IllegalStateException("Invalid operator passed to" + "binOp1 context!");
    }

    ExprNode leftExpr = (ExprNode) visit(ctx.expr(0));
    ExprNode rightExpr = (ExprNode) visit(ctx.expr(1));

    BinaryOperatorNode binaryOperatorNode = new BinaryOperatorNode(operator, leftExpr, rightExpr);

    binaryOperatorNode.check(this, ctx);

    return binaryOperatorNode;
  }

  /* Called when applying a binary operation of precedence 3 to 2 expressions eg. 5 < 6
  Creates a binary operator node*/
  @Override
  public Node visitBinOp3Application(BinOp3ApplicationContext ctx) {
    /* Determine operator. */
    BinaryOperators operator;
    TerminalNode typeNode = (TerminalNode) ctx.getChild(1);
    switch (typeNode.getSymbol().getType()) {
      case BasicLexer.GT:
        operator = BinaryOperators.GT;
        break;
      case BasicLexer.GTE:
        operator = BinaryOperators.GTE;
        break;
      case BasicLexer.LT:
        operator = BinaryOperators.LT;
        break;
      case BasicLexer.LTE:
        operator = BinaryOperators.LTE;
        break;
      default:
        throw new IllegalStateException("Invalid operator passed to" + "binOp1 context!");
    }

    ExprNode leftExpr = (ExprNode) visit(ctx.expr(0));
    ExprNode rightExpr = (ExprNode) visit(ctx.expr(1));

    BinaryOperatorNode binaryOperatorNode = new BinaryOperatorNode(operator, leftExpr, rightExpr);

    binaryOperatorNode.check(this, ctx);

    return binaryOperatorNode;
  }

  /* Called when applying a binary operation of precedence 4 to 2 expressions eg. 5 != 6
  Creates a binary operator node*/
  @Override
  public Node visitBinOp4Application(BinOp4ApplicationContext ctx) {
    /* Determine operator. */
    BinaryOperators operator;
    TerminalNode typeNode = (TerminalNode) ctx.getChild(1);
    switch (typeNode.getSymbol().getType()) {
      case BasicLexer.EQ:
        operator = BinaryOperators.EQ;
        break;
      case BasicLexer.NEQ:
        operator = BinaryOperators.NEQ;
        break;
      default:
        throw new IllegalStateException("Invalid operator passed to" + "binOp1 context!");
    }

    ExprNode leftExpr = (ExprNode) visit(ctx.expr(0));
    ExprNode rightExpr = (ExprNode) visit(ctx.expr(1));

    BinaryOperatorNode binaryOperatorNode = new BinaryOperatorNode(operator, leftExpr, rightExpr);

    binaryOperatorNode.check(this, ctx);

    return binaryOperatorNode;
  }

  /* Called when applying a binary operation of precedence 5 to 2 expressions eg. true && true
  Creates a binary operator node*/
  @Override
  public Node visitBinOp5Application(BinOp5ApplicationContext ctx) {
    /* Only one operator with precedence 5 : AND. */
    TerminalNode typeNode = (TerminalNode) ctx.getChild(1);
    if (typeNode.getSymbol().getType() != BasicLexer.AND) {
      throw new IllegalStateException("Invalid operator passed to" + "binOp1 context!");
    }

    ExprNode leftExpr = (ExprNode) visit(ctx.expr(0));
    ExprNode rightExpr = (ExprNode) visit(ctx.expr(1));

    BinaryOperatorNode binaryOperatorNode =
        new BinaryOperatorNode(BinaryOperators.AND, leftExpr, rightExpr);

    binaryOperatorNode.check(this, ctx);

    return binaryOperatorNode;
  }

  /* Called when applying a binary operation of precedence 6 to 2 expressions eg. true || true
  Creates a binary operator node*/
  @Override
  public Node visitBinOp6Application(BinOp6ApplicationContext ctx) {
    /* Only one operator with precedence 5 : AND. */
    TerminalNode typeNode = (TerminalNode) ctx.getChild(1);
    if (typeNode.getSymbol().getType() != BasicLexer.OR) {
      throw new IllegalStateException("Invalid operator passed to" + "binOp1 context!");
    }

    ExprNode leftExpr = (ExprNode) visit(ctx.expr(0));
    ExprNode rightExpr = (ExprNode) visit(ctx.expr(1));

    BinaryOperatorNode binaryOperatorNode =
        new BinaryOperatorNode(BinaryOperators.OR, leftExpr, rightExpr);

    binaryOperatorNode.check(this, ctx);

    return binaryOperatorNode;
  }

  /* Called when an int literal is reached eg. 5
  Checks if the integer is within the permissible range and creates a int literal node*/
  @Override
  public Node visitIntLiter(IntLiterContext ctx) {
    long value;

    try {
      value = Long.parseLong(ctx.INTEGER().getText());
    } catch (NumberFormatException e) {
      throw new SyntaxException(
          "Integer is of value: "
              + ctx.INTEGER().getText()
              + ", but must be between "
              + IntType.INT_MIN
              + " and "
              + IntType.INT_MAX,
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
