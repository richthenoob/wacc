// Generated from /Users/zirun/Documents/OneDrive - Imperial College London/IMPERIAL Y2/IMPERIAL Y2S2/WACC/wacc_22/antlr_config/BasicParser.g4 by ANTLR 4.9.1
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
	 * Enter a parse tree produced by {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(BasicParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(BasicParser.StatContext ctx);
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
	 * Enter a parse tree produced by {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 */
	void enterAssignRhs(BasicParser.AssignRhsContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 */
	void exitAssignRhs(BasicParser.AssignRhsContext ctx);
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
	 * Enter a parse tree produced by {@link BasicParser#pairElem}.
	 * @param ctx the parse tree
	 */
	void enterPairElem(BasicParser.PairElemContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#pairElem}.
	 * @param ctx the parse tree
	 */
	void exitPairElem(BasicParser.PairElemContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(BasicParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(BasicParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 */
	void enterBaseType(BasicParser.BaseTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 */
	void exitBaseType(BasicParser.BaseTypeContext ctx);
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
	 * Enter a parse tree produced by {@link BasicParser#unaryOper}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOper(BasicParser.UnaryOperContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#unaryOper}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOper(BasicParser.UnaryOperContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#binaryOper}.
	 * @param ctx the parse tree
	 */
	void enterBinaryOper(BasicParser.BinaryOperContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#binaryOper}.
	 * @param ctx the parse tree
	 */
	void exitBinaryOper(BasicParser.BinaryOperContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(BasicParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(BasicParser.ExprContext ctx);
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