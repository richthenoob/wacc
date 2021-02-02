// Generated from /Users/zirun/Documents/OneDrive - Imperial College London/IMPERIAL Y2/IMPERIAL Y2S2/WACC/wacc_22/antlr_config/BasicParser.g4 by ANTLR 4.9.1
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
	 * Visit a parse tree produced by {@link BasicParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(BasicParser.StatContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#assignLhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignLhs(BasicParser.AssignLhsContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#assignRhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignRhs(BasicParser.AssignRhsContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(BasicParser.ArgListContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#pairElem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairElem(BasicParser.PairElemContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(BasicParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#baseType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseType(BasicParser.BaseTypeContext ctx);
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
	 * Visit a parse tree produced by {@link BasicParser#unaryOper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOper(BasicParser.UnaryOperContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#binaryOper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryOper(BasicParser.BinaryOperContext ctx);
	/**
	 * Visit a parse tree produced by {@link BasicParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(BasicParser.ExprContext ctx);
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