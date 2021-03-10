// Generated from /Users/zirun/wacc_22/src/main/antlr/BasicParser.g4 by ANTLR 4.9.1

    package ic.doc.antlr;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BasicParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BasicParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BasicParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(BasicParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#class_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_(BasicParser.Class_Context ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#classEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassEntry(BasicParser.ClassEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc(BasicParser.FuncContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(BasicParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(BasicParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by the {@code declarativeAssignment}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarativeAssignment(BasicParser.DeclarativeAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code read}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead(BasicParser.ReadContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(BasicParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code skip}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSkip(BasicParser.SkipContext ctx);
	/**
	 * Visit a parse tree produced by the {@code while}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile(BasicParser.WhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code declareNewClass}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclareNewClass(BasicParser.DeclareNewClassContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exit}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExit(BasicParser.ExitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code print}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(BasicParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code println}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintln(BasicParser.PrintlnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code semi}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSemi(BasicParser.SemiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code free}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFree(BasicParser.FreeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code if}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(BasicParser.IfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code begin}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBegin(BasicParser.BeginContext ctx);
	/**
	 * Visit a parse tree produced by the {@code return}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn(BasicParser.ReturnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignNewClass}
	 * labeled alternative in {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignNewClass(BasicParser.AssignNewClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#assignLhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignLhs(BasicParser.AssignLhsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprDup}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprDup(BasicParser.ExprDupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayLiterDup}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayLiterDup(BasicParser.ArrayLiterDupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newPair}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewPair(BasicParser.NewPairContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pairElemDup}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairElemDup(BasicParser.PairElemDupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code call}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(BasicParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callClassFunction}
	 * labeled alternative in {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallClassFunction(BasicParser.CallClassFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(BasicParser.ArgListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fstPairElem}
	 * labeled alternative in {@link BasicParser#pairElem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFstPairElem(BasicParser.FstPairElemContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sndPairElem}
	 * labeled alternative in {@link BasicParser#pairElem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSndPairElem(BasicParser.SndPairElemContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#classObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassObject(BasicParser.ClassObjectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pairTypeDup}
	 * labeled alternative in {@link BasicParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairTypeDup(BasicParser.PairTypeDupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code baseTypeDup}
	 * labeled alternative in {@link BasicParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseTypeDup(BasicParser.BaseTypeDupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayTypeDup}
	 * labeled alternative in {@link BasicParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayTypeDup(BasicParser.ArrayTypeDupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(BasicParser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(BasicParser.BoolTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code charType}
	 * labeled alternative in {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharType(BasicParser.CharTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code strType}
	 * labeled alternative in {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStrType(BasicParser.StrTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(BasicParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#pairType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairType(BasicParser.PairTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#pairElemType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairElemType(BasicParser.PairElemTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binOp2Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinOp2Application(BasicParser.BinOp2ApplicationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identifier}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(BasicParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binOp1Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinOp1Application(BasicParser.BinOp1ApplicationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayElemDup}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayElemDup(BasicParser.ArrayElemDupContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binOp5Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinOp5Application(BasicParser.BinOp5ApplicationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code classVariable}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassVariable(BasicParser.ClassVariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binOp3Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinOp3Application(BasicParser.BinOp3ApplicationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binOp4Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinOp4Application(BasicParser.BinOp4ApplicationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literal}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(BasicParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unOpApplication}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnOpApplication(BasicParser.UnOpApplicationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code brackets}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBrackets(BasicParser.BracketsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binOp6Application}
	 * labeled alternative in {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinOp6Application(BasicParser.BinOp6ApplicationContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#intSign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntSign(BasicParser.IntSignContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#intLiter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiter(BasicParser.IntLiterContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#arrayElem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayElem(BasicParser.ArrayElemContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#arrayLiter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayLiter(BasicParser.ArrayLiterContext ctx);
}