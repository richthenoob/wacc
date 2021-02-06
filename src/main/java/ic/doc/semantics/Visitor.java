package ic.doc.semantics;

import ic.doc.antlr.BasicParser;
import ic.doc.antlr.BasicParserBaseVisitor;

public class Visitor extends BasicParserBaseVisitor {
    @Override
    public Object visitProg(BasicParser.ProgContext ctx) {
        return super.visitProg(ctx);
    }

    @Override
    public Object visitFunc(BasicParser.FuncContext ctx) {
        return super.visitFunc(ctx);
    }

    @Override
    public Object visitParamList(BasicParser.ParamListContext ctx) {
        return super.visitParamList(ctx);
    }

    @Override
    public Object visitParam(BasicParser.ParamContext ctx) {
        return super.visitParam(ctx);
    }

    @Override
    public Object visitStat(BasicParser.StatContext ctx) {
        return super.visitStat(ctx);
    }

    @Override
    public Object visitAssignLhs(BasicParser.AssignLhsContext ctx) {
        return super.visitAssignLhs(ctx);
    }

    @Override
    public Object visitAssignRhs(BasicParser.AssignRhsContext ctx) {
        return super.visitAssignRhs(ctx);
    }

    @Override
    public Object visitArgList(BasicParser.ArgListContext ctx) {
        return super.visitArgList(ctx);
    }

    @Override
    public Object visitPairElem(BasicParser.PairElemContext ctx) {
        return super.visitPairElem(ctx);
    }

    @Override
    public Object visitType(BasicParser.TypeContext ctx) {
        return super.visitType(ctx);
    }

    @Override
    public Object visitBaseType(BasicParser.BaseTypeContext ctx) {
        return super.visitBaseType(ctx);
    }

    @Override
    public Object visitArrayType(BasicParser.ArrayTypeContext ctx) {
        return super.visitArrayType(ctx);
    }

    @Override
    public Object visitPairType(BasicParser.PairTypeContext ctx) {
        return super.visitPairType(ctx);
    }

    @Override
    public Object visitPairElemType(BasicParser.PairElemTypeContext ctx) {
        return super.visitPairElemType(ctx);
    }

    @Override
    public Object visitUnaryOper(BasicParser.UnaryOperContext ctx) {
        return super.visitUnaryOper(ctx);
    }

    @Override
    public Object visitBinaryOper(BasicParser.BinaryOperContext ctx) {
        return super.visitBinaryOper(ctx);
    }

    @Override
    public Object visitInt_sign(BasicParser.Int_signContext ctx) {
        return super.visitInt_sign(ctx);
    }

    @Override
    public Object visitInt_liter(BasicParser.Int_literContext ctx) {
        return super.visitInt_liter(ctx);
    }

    @Override
    public Object visitExpr(BasicParser.ExprContext ctx) {
        return super.visitExpr(ctx);
    }

    @Override
    public Object visitArrayElem(BasicParser.ArrayElemContext ctx) {
        return super.visitArrayElem(ctx);
    }

    @Override
    public Object visitArrayLiter(BasicParser.ArrayLiterContext ctx) {
        return super.visitArrayLiter(ctx);
    }
}
