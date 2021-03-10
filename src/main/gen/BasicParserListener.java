// Generated from /Users/zirun/wacc_22/src/main/antlr/BasicParser.g4 by ANTLR 4.9.1

    package ic.doc.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BasicParser}.
 */
public interface BasicParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BasicParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(BasicParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(BasicParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#class_}.
	 * @param ctx the parse tree
	 */
	void enterClass_(BasicParser.Class_Context ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#class_}.
	 * @param ctx the parse tree
	 */
	void exitClass_(BasicParser.Class_Context ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#classEntry}.
	 * @param ctx the parse tree
	 */
	void enterClassEntry(BasicParser.ClassEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#classEntry}.
	 * @param ctx the parse tree
	 */
	void exitClassEntry(BasicParser.ClassEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#func}.
	 * @param ctx the parse tree
	 */
	void enterFunc(BasicParser.FuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#func}.
	 * @param ctx the parse tree
	 */
	void exitFunc(BasicParser.FuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(BasicParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(BasicParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(BasicParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(BasicParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by the {@code declarativeAssignment}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterDeclarativeAssignment(BasicParser.DeclarativeAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code declarativeAssignment}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitDeclarativeAssignment(BasicParser.DeclarativeAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code read}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterRead(BasicParser.ReadContext ctx);
	/**
	 * Exit a parse tree produced by the {@code read}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitRead(BasicParser.ReadContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(BasicParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(BasicParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code skip}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterSkip(BasicParser.SkipContext ctx);
	/**
	 * Exit a parse tree produced by the {@code skip}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitSkip(BasicParser.SkipContext ctx);
	/**
	 * Enter a parse tree produced by the {@code while}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterWhile(BasicParser.WhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code while}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitWhile(BasicParser.WhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code declareNewClass}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterDeclareNewClass(BasicParser.DeclareNewClassContext ctx);
	/**
	 * Exit a parse tree produced by the {@code declareNewClass}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitDeclareNewClass(BasicParser.DeclareNewClassContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exit}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterExit(BasicParser.ExitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exit}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitExit(BasicParser.ExitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code print}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrint(BasicParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code print}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrint(BasicParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code println}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrintln(BasicParser.PrintlnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code println}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrintln(BasicParser.PrintlnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code semi}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterSemi(BasicParser.SemiContext ctx);
	/**
	 * Exit a parse tree produced by the {@code semi}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitSemi(BasicParser.SemiContext ctx);
	/**
	 * Enter a parse tree produced by the {@code free}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterFree(BasicParser.FreeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code free}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitFree(BasicParser.FreeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code if}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterIf(BasicParser.IfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code if}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitIf(BasicParser.IfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code begin}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterBegin(BasicParser.BeginContext ctx);
	/**
	 * Exit a parse tree produced by the {@code begin}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitBegin(BasicParser.BeginContext ctx);
	/**
	 * Enter a parse tree produced by the {@code return}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterReturn(BasicParser.ReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code return}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitReturn(BasicParser.ReturnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignNewClass}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssignNewClass(BasicParser.AssignNewClassContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignNewClass}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssignNewClass(BasicParser.AssignNewClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#assignLhs}.
	 * @param ctx the parse tree
	 */
	void enterAssignLhs(BasicParser.AssignLhsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#assignLhs}.
	 * @param ctx the parse tree
	 */
	void exitAssignLhs(BasicParser.AssignLhsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprDup}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 */
	void enterExprDup(BasicParser.ExprDupContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprDup}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 */
	void exitExprDup(BasicParser.ExprDupContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayLiterDup}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 */
	void enterArrayLiterDup(BasicParser.ArrayLiterDupContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayLiterDup}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 */
	void exitArrayLiterDup(BasicParser.ArrayLiterDupContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newPair}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 */
	void enterNewPair(BasicParser.NewPairContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newPair}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 */
	void exitNewPair(BasicParser.NewPairContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pairElemDup}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 */
	void enterPairElemDup(BasicParser.PairElemDupContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pairElemDup}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 */
	void exitPairElemDup(BasicParser.PairElemDupContext ctx);
	/**
	 * Enter a parse tree produced by the {@code call}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 */
	void enterCall(BasicParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code call}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 */
	void exitCall(BasicParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callClassFunction}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 */
	void enterCallClassFunction(BasicParser.CallClassFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callClassFunction}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 */
	void exitCallClassFunction(BasicParser.CallClassFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(BasicParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(BasicParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fstPairElem}
	 * labeled alternative in {@link BasicParser#pairElem}.
	 * @param ctx the parse tree
	 */
	void enterFstPairElem(BasicParser.FstPairElemContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fstPairElem}
	 * labeled alternative in {@link BasicParser#pairElem}.
	 * @param ctx the parse tree
	 */
	void exitFstPairElem(BasicParser.FstPairElemContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sndPairElem}
	 * labeled alternative in {@link BasicParser#pairElem}.
	 * @param ctx the parse tree
	 */
	void enterSndPairElem(BasicParser.SndPairElemContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sndPairElem}
	 * labeled alternative in {@link BasicParser#pairElem}.
	 * @param ctx the parse tree
	 */
	void exitSndPairElem(BasicParser.SndPairElemContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#classObject}.
	 * @param ctx the parse tree
	 */
	void enterClassObject(BasicParser.ClassObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#classObject}.
	 * @param ctx the parse tree
	 */
	void exitClassObject(BasicParser.ClassObjectContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pairTypeDup}
	 * labeled alternative in {@link BasicParser#type}.
	 * @param ctx the parse tree
	 */
	void enterPairTypeDup(BasicParser.PairTypeDupContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pairTypeDup}
	 * labeled alternative in {@link BasicParser#type}.
	 * @param ctx the parse tree
	 */
	void exitPairTypeDup(BasicParser.PairTypeDupContext ctx);
	/**
	 * Enter a parse tree produced by the {@code baseTypeDup}
	 * labeled alternative in {@link BasicParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBaseTypeDup(BasicParser.BaseTypeDupContext ctx);
	/**
	 * Exit a parse tree produced by the {@code baseTypeDup}
	 * labeled alternative in {@link BasicParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBaseTypeDup(BasicParser.BaseTypeDupContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayTypeDup}
	 * labeled alternative in {@link BasicParser#type}.
	 * @param ctx the parse tree
	 */
	void enterArrayTypeDup(BasicParser.ArrayTypeDupContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayTypeDup}
	 * labeled alternative in {@link BasicParser#type}.
	 * @param ctx the parse tree
	 */
	void exitArrayTypeDup(BasicParser.ArrayTypeDupContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intType}
	 * labeled alternative in {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 */
	void enterIntType(BasicParser.IntTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 */
	void exitIntType(BasicParser.IntTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 */
	void enterBoolType(BasicParser.BoolTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 */
	void exitBoolType(BasicParser.BoolTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code charType}
	 * labeled alternative in {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 */
	void enterCharType(BasicParser.CharTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code charType}
	 * labeled alternative in {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 */
	void exitCharType(BasicParser.CharTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code strType}
	 * labeled alternative in {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 */
	void enterStrType(BasicParser.StrTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code strType}
	 * labeled alternative in {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 */
	void exitStrType(BasicParser.StrTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#arrayType}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(BasicParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#arrayType}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(BasicParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#pairType}.
	 * @param ctx the parse tree
	 */
	void enterPairType(BasicParser.PairTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#pairType}.
	 * @param ctx the parse tree
	 */
	void exitPairType(BasicParser.PairTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#pairElemType}.
	 * @param ctx the parse tree
	 */
	void enterPairElemType(BasicParser.PairElemTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#pairElemType}.
	 * @param ctx the parse tree
	 */
	void exitPairElemType(BasicParser.PairElemTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binOp2Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinOp2Application(BasicParser.BinOp2ApplicationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binOp2Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinOp2Application(BasicParser.BinOp2ApplicationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifier}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(BasicParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifier}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(BasicParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binOp1Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinOp1Application(BasicParser.BinOp1ApplicationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binOp1Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinOp1Application(BasicParser.BinOp1ApplicationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayElemDup}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayElemDup(BasicParser.ArrayElemDupContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayElemDup}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayElemDup(BasicParser.ArrayElemDupContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binOp5Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinOp5Application(BasicParser.BinOp5ApplicationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binOp5Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinOp5Application(BasicParser.BinOp5ApplicationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code classVariable}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterClassVariable(BasicParser.ClassVariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code classVariable}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitClassVariable(BasicParser.ClassVariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binOp3Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinOp3Application(BasicParser.BinOp3ApplicationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binOp3Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinOp3Application(BasicParser.BinOp3ApplicationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binOp4Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinOp4Application(BasicParser.BinOp4ApplicationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binOp4Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinOp4Application(BasicParser.BinOp4ApplicationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literal}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(BasicParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literal}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(BasicParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unOpApplication}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnOpApplication(BasicParser.UnOpApplicationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unOpApplication}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnOpApplication(BasicParser.UnOpApplicationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code brackets}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBrackets(BasicParser.BracketsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code brackets}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBrackets(BasicParser.BracketsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binOp6Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinOp6Application(BasicParser.BinOp6ApplicationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binOp6Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinOp6Application(BasicParser.BinOp6ApplicationContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#intSign}.
	 * @param ctx the parse tree
	 */
	void enterIntSign(BasicParser.IntSignContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#intSign}.
	 * @param ctx the parse tree
	 */
	void exitIntSign(BasicParser.IntSignContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#intLiter}.
	 * @param ctx the parse tree
	 */
	void enterIntLiter(BasicParser.IntLiterContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#intLiter}.
	 * @param ctx the parse tree
	 */
	void exitIntLiter(BasicParser.IntLiterContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#arrayElem}.
	 * @param ctx the parse tree
	 */
	void enterArrayElem(BasicParser.ArrayElemContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#arrayElem}.
	 * @param ctx the parse tree
	 */
	void exitArrayElem(BasicParser.ArrayElemContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#arrayLiter}.
	 * @param ctx the parse tree
	 */
	void enterArrayLiter(BasicParser.ArrayLiterContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#arrayLiter}.
	 * @param ctx the parse tree
	 */
	void exitArrayLiter(BasicParser.ArrayLiterContext ctx);
}