// Generated from /Users/zirun/Documents/OneDrive - Imperial College London/IMPERIAL Y2/IMPERIAL Y2S2/WACC/wacc_22/antlr_config/BasicParser.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BasicParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PLUS=1, MINUS=2, MUL=3, DIV=4, MOD=5, GT=6, GTE=7, LT=8, LTE=9, ASSIGN=10, 
		EQ=11, NEQ=12, AND=13, OR=14, NOT=15, LEN=16, ORD=17, CHR=18, SQUOTE=19, 
		DQUOTE=20, OPEN_PARENTHESES=21, CLOSE_PARENTHESES=22, OPEN_BRACKETS=23, 
		CLOSE_BRACKETS=24, COMMA=25, SEMI=26, PAIR=27, FST=28, SND=29, COMMENT=30, 
		WS=31, BEGIN=32, END=33, IS=34, READ=35, FREE=36, RETURN=37, EXIT=38, 
		PRINT=39, PRINTLN=40, IF=41, THEN=42, ELSE=43, FI=44, WHILE=45, DONE=46, 
		SKP=47, DO=48, NEWPAIR=49, CALL=50, INT=51, BOOL=52, CHAR=53, STR=54, 
		INT_LITER=55, BOOL_LITER=56, CHAR_LITER=57, STR_LITER=58, PAIR_LITER=59, 
		IDENT=60;
	public static final int
		RULE_prog = 0, RULE_func = 1, RULE_paramList = 2, RULE_param = 3, RULE_stat = 4, 
		RULE_assignLhs = 5, RULE_assignRhs = 6, RULE_argList = 7, RULE_pairElem = 8, 
		RULE_type = 9, RULE_baseType = 10, RULE_arrayType = 11, RULE_pairType = 12, 
		RULE_pairElemType = 13, RULE_unaryOper = 14, RULE_binaryOper = 15, RULE_expr = 16, 
		RULE_arrayElem = 17, RULE_arrayLiter = 18;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "func", "paramList", "param", "stat", "assignLhs", "assignRhs", 
			"argList", "pairElem", "type", "baseType", "arrayType", "pairType", "pairElemType", 
			"unaryOper", "binaryOper", "expr", "arrayElem", "arrayLiter"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", "'>='", "'<'", "'<='", 
			"'='", "'=='", "'!='", "'&&'", "'||'", "'!'", "'len'", "'ord'", "'chr'", 
			"'''", "'\"'", "'('", "')'", "'['", "']'", "','", "';'", "'pair'", "'fst'", 
			"'snd'", null, null, "'begin'", "'end'", "'is'", "'read'", "'free'", 
			"'return'", "'exit'", "'print'", "'println'", "'if'", "'then'", "'else'", 
			"'fi'", "'while'", "'done'", "'skip'", "'do'", "'newpair'", "'call'", 
			"'int'", "'bool'", "'char'", "'string'", null, null, null, null, "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PLUS", "MINUS", "MUL", "DIV", "MOD", "GT", "GTE", "LT", "LTE", 
			"ASSIGN", "EQ", "NEQ", "AND", "OR", "NOT", "LEN", "ORD", "CHR", "SQUOTE", 
			"DQUOTE", "OPEN_PARENTHESES", "CLOSE_PARENTHESES", "OPEN_BRACKETS", "CLOSE_BRACKETS", 
			"COMMA", "SEMI", "PAIR", "FST", "SND", "COMMENT", "WS", "BEGIN", "END", 
			"IS", "READ", "FREE", "RETURN", "EXIT", "PRINT", "PRINTLN", "IF", "THEN", 
			"ELSE", "FI", "WHILE", "DONE", "SKP", "DO", "NEWPAIR", "CALL", "INT", 
			"BOOL", "CHAR", "STR", "INT_LITER", "BOOL_LITER", "CHAR_LITER", "STR_LITER", 
			"PAIR_LITER", "IDENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "BasicParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public BasicParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgContext extends ParserRuleContext {
		public TerminalNode BEGIN() { return getToken(BasicParser.BEGIN, 0); }
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public TerminalNode END() { return getToken(BasicParser.END, 0); }
		public TerminalNode EOF() { return getToken(BasicParser.EOF, 0); }
		public List<FuncContext> func() {
			return getRuleContexts(FuncContext.class);
		}
		public FuncContext func(int i) {
			return getRuleContext(FuncContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(BEGIN);
			setState(42);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(39);
					func();
					}
					} 
				}
				setState(44);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(45);
			stat(0);
			setState(46);
			match(END);
			setState(47);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENT() { return getToken(BasicParser.IDENT, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(BasicParser.OPEN_PARENTHESES, 0); }
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(BasicParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode IS() { return getToken(BasicParser.IS, 0); }
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public TerminalNode END() { return getToken(BasicParser.END, 0); }
		public FuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitFunc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncContext func() throws RecognitionException {
		FuncContext _localctx = new FuncContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_func);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			type(0);
			setState(50);
			match(IDENT);
			setState(51);
			match(OPEN_PARENTHESES);
			setState(52);
			paramList();
			setState(53);
			match(CLOSE_PARENTHESES);
			setState(54);
			match(IS);
			setState(55);
			stat(0);
			setState(56);
			match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamListContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(BasicParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BasicParser.COMMA, i);
		}
		public ParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterParamList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitParamList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamListContext paramList() throws RecognitionException {
		ParamListContext _localctx = new ParamListContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_paramList);
		int _la;
		try {
			setState(67);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PAIR:
			case INT:
			case BOOL:
			case CHAR:
			case STR:
				enterOuterAlt(_localctx, 1);
				{
				setState(58);
				param();
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(59);
					match(COMMA);
					setState(60);
					param();
					}
					}
					setState(65);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case CLOSE_PARENTHESES:
				enterOuterAlt(_localctx, 2);
				{
				{
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENT() { return getToken(BasicParser.IDENT, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			type(0);
			setState(70);
			match(IDENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatContext extends ParserRuleContext {
		public TerminalNode SKP() { return getToken(BasicParser.SKP, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENT() { return getToken(BasicParser.IDENT, 0); }
		public TerminalNode ASSIGN() { return getToken(BasicParser.ASSIGN, 0); }
		public AssignRhsContext assignRhs() {
			return getRuleContext(AssignRhsContext.class,0);
		}
		public AssignLhsContext assignLhs() {
			return getRuleContext(AssignLhsContext.class,0);
		}
		public TerminalNode READ() { return getToken(BasicParser.READ, 0); }
		public TerminalNode FREE() { return getToken(BasicParser.FREE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RETURN() { return getToken(BasicParser.RETURN, 0); }
		public TerminalNode EXIT() { return getToken(BasicParser.EXIT, 0); }
		public TerminalNode PRINT() { return getToken(BasicParser.PRINT, 0); }
		public TerminalNode PRINTLN() { return getToken(BasicParser.PRINTLN, 0); }
		public TerminalNode IF() { return getToken(BasicParser.IF, 0); }
		public TerminalNode THEN() { return getToken(BasicParser.THEN, 0); }
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(BasicParser.ELSE, 0); }
		public TerminalNode FI() { return getToken(BasicParser.FI, 0); }
		public TerminalNode WHILE() { return getToken(BasicParser.WHILE, 0); }
		public TerminalNode DO() { return getToken(BasicParser.DO, 0); }
		public TerminalNode DONE() { return getToken(BasicParser.DONE, 0); }
		public TerminalNode BEGIN() { return getToken(BasicParser.BEGIN, 0); }
		public TerminalNode END() { return getToken(BasicParser.END, 0); }
		public TerminalNode SEMI() { return getToken(BasicParser.SEMI, 0); }
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		return stat(0);
	}

	private StatContext stat(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		StatContext _localctx = new StatContext(_ctx, _parentState);
		StatContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_stat, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SKP:
				{
				setState(73);
				match(SKP);
				}
				break;
			case PAIR:
			case INT:
			case BOOL:
			case CHAR:
			case STR:
				{
				setState(74);
				type(0);
				setState(75);
				match(IDENT);
				setState(76);
				match(ASSIGN);
				setState(77);
				assignRhs();
				}
				break;
			case FST:
			case SND:
			case IDENT:
				{
				setState(79);
				assignLhs();
				setState(80);
				match(ASSIGN);
				setState(81);
				assignRhs();
				}
				break;
			case READ:
				{
				setState(83);
				match(READ);
				setState(84);
				assignLhs();
				}
				break;
			case FREE:
				{
				setState(85);
				match(FREE);
				setState(86);
				expr(0);
				}
				break;
			case RETURN:
				{
				setState(87);
				match(RETURN);
				setState(88);
				expr(0);
				}
				break;
			case EXIT:
				{
				setState(89);
				match(EXIT);
				setState(90);
				expr(0);
				}
				break;
			case PRINT:
				{
				setState(91);
				match(PRINT);
				setState(92);
				expr(0);
				}
				break;
			case PRINTLN:
				{
				setState(93);
				match(PRINTLN);
				setState(94);
				expr(0);
				}
				break;
			case IF:
				{
				setState(95);
				match(IF);
				setState(96);
				expr(0);
				setState(97);
				match(THEN);
				setState(98);
				stat(0);
				setState(99);
				match(ELSE);
				setState(100);
				stat(0);
				setState(101);
				match(FI);
				}
				break;
			case WHILE:
				{
				setState(103);
				match(WHILE);
				setState(104);
				expr(0);
				setState(105);
				match(DO);
				setState(106);
				stat(0);
				setState(107);
				match(DONE);
				}
				break;
			case BEGIN:
				{
				setState(109);
				match(BEGIN);
				setState(110);
				stat(0);
				setState(111);
				match(END);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(120);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new StatContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_stat);
					setState(115);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(116);
					match(SEMI);
					setState(117);
					stat(2);
					}
					} 
				}
				setState(122);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AssignLhsContext extends ParserRuleContext {
		public TerminalNode IDENT() { return getToken(BasicParser.IDENT, 0); }
		public ArrayElemContext arrayElem() {
			return getRuleContext(ArrayElemContext.class,0);
		}
		public PairElemContext pairElem() {
			return getRuleContext(PairElemContext.class,0);
		}
		public AssignLhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignLhs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterAssignLhs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitAssignLhs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitAssignLhs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignLhsContext assignLhs() throws RecognitionException {
		AssignLhsContext _localctx = new AssignLhsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assignLhs);
		try {
			setState(126);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(123);
				match(IDENT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(124);
				arrayElem();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(125);
				pairElem();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignRhsContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ArrayLiterContext arrayLiter() {
			return getRuleContext(ArrayLiterContext.class,0);
		}
		public TerminalNode NEWPAIR() { return getToken(BasicParser.NEWPAIR, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(BasicParser.OPEN_PARENTHESES, 0); }
		public TerminalNode COMMA() { return getToken(BasicParser.COMMA, 0); }
		public TerminalNode CLOSE_PARENTHESES() { return getToken(BasicParser.CLOSE_PARENTHESES, 0); }
		public PairElemContext pairElem() {
			return getRuleContext(PairElemContext.class,0);
		}
		public TerminalNode CALL() { return getToken(BasicParser.CALL, 0); }
		public TerminalNode IDENT() { return getToken(BasicParser.IDENT, 0); }
		public ArgListContext argList() {
			return getRuleContext(ArgListContext.class,0);
		}
		public AssignRhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignRhs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterAssignRhs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitAssignRhs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitAssignRhs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignRhsContext assignRhs() throws RecognitionException {
		AssignRhsContext _localctx = new AssignRhsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_assignRhs);
		int _la;
		try {
			setState(145);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MINUS:
			case NOT:
			case LEN:
			case ORD:
			case CHR:
			case OPEN_PARENTHESES:
			case INT_LITER:
			case BOOL_LITER:
			case CHAR_LITER:
			case STR_LITER:
			case PAIR_LITER:
			case IDENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(128);
				expr(0);
				}
				break;
			case OPEN_BRACKETS:
				enterOuterAlt(_localctx, 2);
				{
				setState(129);
				arrayLiter();
				}
				break;
			case NEWPAIR:
				enterOuterAlt(_localctx, 3);
				{
				setState(130);
				match(NEWPAIR);
				setState(131);
				match(OPEN_PARENTHESES);
				setState(132);
				expr(0);
				setState(133);
				match(COMMA);
				setState(134);
				expr(0);
				setState(135);
				match(CLOSE_PARENTHESES);
				}
				break;
			case FST:
			case SND:
				enterOuterAlt(_localctx, 4);
				{
				setState(137);
				pairElem();
				}
				break;
			case CALL:
				enterOuterAlt(_localctx, 5);
				{
				setState(138);
				match(CALL);
				setState(139);
				match(IDENT);
				setState(140);
				match(OPEN_PARENTHESES);
				setState(142);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MINUS) | (1L << NOT) | (1L << LEN) | (1L << ORD) | (1L << CHR) | (1L << OPEN_PARENTHESES) | (1L << INT_LITER) | (1L << BOOL_LITER) | (1L << CHAR_LITER) | (1L << STR_LITER) | (1L << PAIR_LITER) | (1L << IDENT))) != 0)) {
					{
					setState(141);
					argList();
					}
				}

				setState(144);
				match(CLOSE_PARENTHESES);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgListContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(BasicParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BasicParser.COMMA, i);
		}
		public ArgListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterArgList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitArgList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitArgList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgListContext argList() throws RecognitionException {
		ArgListContext _localctx = new ArgListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_argList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			expr(0);
			setState(152);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(148);
				match(COMMA);
				setState(149);
				expr(0);
				}
				}
				setState(154);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairElemContext extends ParserRuleContext {
		public TerminalNode FST() { return getToken(BasicParser.FST, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SND() { return getToken(BasicParser.SND, 0); }
		public PairElemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairElem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterPairElem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitPairElem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitPairElem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairElemContext pairElem() throws RecognitionException {
		PairElemContext _localctx = new PairElemContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_pairElem);
		try {
			setState(159);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FST:
				enterOuterAlt(_localctx, 1);
				{
				setState(155);
				match(FST);
				setState(156);
				expr(0);
				}
				break;
			case SND:
				enterOuterAlt(_localctx, 2);
				{
				setState(157);
				match(SND);
				setState(158);
				expr(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public PairTypeContext pairType() {
			return getRuleContext(PairTypeContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode OPEN_BRACKETS() { return getToken(BasicParser.OPEN_BRACKETS, 0); }
		public TerminalNode CLOSE_BRACKETS() { return getToken(BasicParser.CLOSE_BRACKETS, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		return type(0);
	}

	private TypeContext type(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeContext _localctx = new TypeContext(_ctx, _parentState);
		TypeContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
			case BOOL:
			case CHAR:
			case STR:
				{
				setState(162);
				baseType();
				}
				break;
			case PAIR:
				{
				setState(163);
				pairType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(171);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TypeContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_type);
					setState(166);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(167);
					match(OPEN_BRACKETS);
					setState(168);
					match(CLOSE_BRACKETS);
					}
					} 
				}
				setState(173);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BaseTypeContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(BasicParser.INT, 0); }
		public TerminalNode BOOL() { return getToken(BasicParser.BOOL, 0); }
		public TerminalNode CHAR() { return getToken(BasicParser.CHAR, 0); }
		public TerminalNode STR() { return getToken(BasicParser.STR, 0); }
		public BaseTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterBaseType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitBaseType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitBaseType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseTypeContext baseType() throws RecognitionException {
		BaseTypeContext _localctx = new BaseTypeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_baseType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << BOOL) | (1L << CHAR) | (1L << STR))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayTypeContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode OPEN_BRACKETS() { return getToken(BasicParser.OPEN_BRACKETS, 0); }
		public TerminalNode CLOSE_BRACKETS() { return getToken(BasicParser.CLOSE_BRACKETS, 0); }
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterArrayType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitArrayType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_arrayType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176);
			type(0);
			setState(177);
			match(OPEN_BRACKETS);
			setState(178);
			match(CLOSE_BRACKETS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairTypeContext extends ParserRuleContext {
		public TerminalNode PAIR() { return getToken(BasicParser.PAIR, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(BasicParser.OPEN_PARENTHESES, 0); }
		public List<PairElemTypeContext> pairElemType() {
			return getRuleContexts(PairElemTypeContext.class);
		}
		public PairElemTypeContext pairElemType(int i) {
			return getRuleContext(PairElemTypeContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(BasicParser.COMMA, 0); }
		public TerminalNode CLOSE_PARENTHESES() { return getToken(BasicParser.CLOSE_PARENTHESES, 0); }
		public PairTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterPairType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitPairType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitPairType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairTypeContext pairType() throws RecognitionException {
		PairTypeContext _localctx = new PairTypeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_pairType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			match(PAIR);
			setState(181);
			match(OPEN_PARENTHESES);
			setState(182);
			pairElemType();
			setState(183);
			match(COMMA);
			setState(184);
			pairElemType();
			setState(185);
			match(CLOSE_PARENTHESES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairElemTypeContext extends ParserRuleContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public TerminalNode PAIR() { return getToken(BasicParser.PAIR, 0); }
		public PairElemTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairElemType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterPairElemType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitPairElemType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitPairElemType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairElemTypeContext pairElemType() throws RecognitionException {
		PairElemTypeContext _localctx = new PairElemTypeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_pairElemType);
		try {
			setState(190);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(187);
				baseType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(188);
				arrayType();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(189);
				match(PAIR);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryOperContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(BasicParser.NOT, 0); }
		public TerminalNode MINUS() { return getToken(BasicParser.MINUS, 0); }
		public TerminalNode LEN() { return getToken(BasicParser.LEN, 0); }
		public TerminalNode ORD() { return getToken(BasicParser.ORD, 0); }
		public TerminalNode CHR() { return getToken(BasicParser.CHR, 0); }
		public UnaryOperContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOper; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterUnaryOper(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitUnaryOper(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitUnaryOper(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryOperContext unaryOper() throws RecognitionException {
		UnaryOperContext _localctx = new UnaryOperContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_unaryOper);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MINUS) | (1L << NOT) | (1L << LEN) | (1L << ORD) | (1L << CHR))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinaryOperContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(BasicParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(BasicParser.MINUS, 0); }
		public TerminalNode MUL() { return getToken(BasicParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(BasicParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(BasicParser.MOD, 0); }
		public TerminalNode GT() { return getToken(BasicParser.GT, 0); }
		public TerminalNode GTE() { return getToken(BasicParser.GTE, 0); }
		public TerminalNode LT() { return getToken(BasicParser.LT, 0); }
		public TerminalNode LTE() { return getToken(BasicParser.LTE, 0); }
		public TerminalNode EQ() { return getToken(BasicParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(BasicParser.NEQ, 0); }
		public TerminalNode AND() { return getToken(BasicParser.AND, 0); }
		public TerminalNode OR() { return getToken(BasicParser.OR, 0); }
		public BinaryOperContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryOper; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterBinaryOper(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitBinaryOper(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitBinaryOper(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryOperContext binaryOper() throws RecognitionException {
		BinaryOperContext _localctx = new BinaryOperContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_binaryOper);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PLUS) | (1L << MINUS) | (1L << MUL) | (1L << DIV) | (1L << MOD) | (1L << GT) | (1L << GTE) | (1L << LT) | (1L << LTE) | (1L << EQ) | (1L << NEQ) | (1L << AND) | (1L << OR))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public TerminalNode INT_LITER() { return getToken(BasicParser.INT_LITER, 0); }
		public TerminalNode BOOL_LITER() { return getToken(BasicParser.BOOL_LITER, 0); }
		public TerminalNode CHAR_LITER() { return getToken(BasicParser.CHAR_LITER, 0); }
		public TerminalNode STR_LITER() { return getToken(BasicParser.STR_LITER, 0); }
		public TerminalNode PAIR_LITER() { return getToken(BasicParser.PAIR_LITER, 0); }
		public TerminalNode IDENT() { return getToken(BasicParser.IDENT, 0); }
		public ArrayElemContext arrayElem() {
			return getRuleContext(ArrayElemContext.class,0);
		}
		public UnaryOperContext unaryOper() {
			return getRuleContext(UnaryOperContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OPEN_PARENTHESES() { return getToken(BasicParser.OPEN_PARENTHESES, 0); }
		public TerminalNode CLOSE_PARENTHESES() { return getToken(BasicParser.CLOSE_PARENTHESES, 0); }
		public BinaryOperContext binaryOper() {
			return getRuleContext(BinaryOperContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(197);
				match(INT_LITER);
				}
				break;
			case 2:
				{
				setState(198);
				match(BOOL_LITER);
				}
				break;
			case 3:
				{
				setState(199);
				match(CHAR_LITER);
				}
				break;
			case 4:
				{
				setState(200);
				match(STR_LITER);
				}
				break;
			case 5:
				{
				setState(201);
				match(PAIR_LITER);
				}
				break;
			case 6:
				{
				setState(202);
				match(IDENT);
				}
				break;
			case 7:
				{
				setState(203);
				arrayElem();
				}
				break;
			case 8:
				{
				setState(204);
				unaryOper();
				setState(205);
				expr(2);
				}
				break;
			case 9:
				{
				setState(207);
				match(OPEN_PARENTHESES);
				setState(208);
				expr(0);
				setState(209);
				match(CLOSE_PARENTHESES);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(219);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ExprContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_expr);
					setState(213);
					if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
					setState(214);
					binaryOper();
					setState(215);
					expr(11);
					}
					} 
				}
				setState(221);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ArrayElemContext extends ParserRuleContext {
		public TerminalNode IDENT() { return getToken(BasicParser.IDENT, 0); }
		public List<TerminalNode> OPEN_BRACKETS() { return getTokens(BasicParser.OPEN_BRACKETS); }
		public TerminalNode OPEN_BRACKETS(int i) {
			return getToken(BasicParser.OPEN_BRACKETS, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> CLOSE_BRACKETS() { return getTokens(BasicParser.CLOSE_BRACKETS); }
		public TerminalNode CLOSE_BRACKETS(int i) {
			return getToken(BasicParser.CLOSE_BRACKETS, i);
		}
		public ArrayElemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayElem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterArrayElem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitArrayElem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitArrayElem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayElemContext arrayElem() throws RecognitionException {
		ArrayElemContext _localctx = new ArrayElemContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_arrayElem);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			match(IDENT);
			setState(227); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(223);
					match(OPEN_BRACKETS);
					setState(224);
					expr(0);
					setState(225);
					match(CLOSE_BRACKETS);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(229); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayLiterContext extends ParserRuleContext {
		public TerminalNode OPEN_BRACKETS() { return getToken(BasicParser.OPEN_BRACKETS, 0); }
		public TerminalNode CLOSE_BRACKETS() { return getToken(BasicParser.CLOSE_BRACKETS, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(BasicParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BasicParser.COMMA, i);
		}
		public ArrayLiterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayLiter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterArrayLiter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitArrayLiter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitArrayLiter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayLiterContext arrayLiter() throws RecognitionException {
		ArrayLiterContext _localctx = new ArrayLiterContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_arrayLiter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			match(OPEN_BRACKETS);
			setState(240);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MINUS) | (1L << NOT) | (1L << LEN) | (1L << ORD) | (1L << CHR) | (1L << OPEN_PARENTHESES) | (1L << INT_LITER) | (1L << BOOL_LITER) | (1L << CHAR_LITER) | (1L << STR_LITER) | (1L << PAIR_LITER) | (1L << IDENT))) != 0)) {
				{
				setState(232);
				expr(0);
				setState(237);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(233);
					match(COMMA);
					setState(234);
					expr(0);
					}
					}
					setState(239);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(242);
			match(CLOSE_BRACKETS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4:
			return stat_sempred((StatContext)_localctx, predIndex);
		case 9:
			return type_sempred((TypeContext)_localctx, predIndex);
		case 16:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean stat_sempred(StatContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean type_sempred(TypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 10);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3>\u00f7\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\3\2\7\2+\n\2\f\2\16\2.\13\2\3\2\3\2\3\2\3\2\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\7\4@\n\4\f\4\16\4C\13\4"+
		"\3\4\5\4F\n\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6t\n\6\3\6\3\6\3"+
		"\6\7\6y\n\6\f\6\16\6|\13\6\3\7\3\7\3\7\5\7\u0081\n\7\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u0091\n\b\3\b\5\b\u0094\n\b"+
		"\3\t\3\t\3\t\7\t\u0099\n\t\f\t\16\t\u009c\13\t\3\n\3\n\3\n\3\n\5\n\u00a2"+
		"\n\n\3\13\3\13\3\13\5\13\u00a7\n\13\3\13\3\13\3\13\7\13\u00ac\n\13\f\13"+
		"\16\13\u00af\13\13\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\17\3\17\3\17\5\17\u00c1\n\17\3\20\3\20\3\21\3\21\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22"+
		"\u00d6\n\22\3\22\3\22\3\22\3\22\7\22\u00dc\n\22\f\22\16\22\u00df\13\22"+
		"\3\23\3\23\3\23\3\23\3\23\6\23\u00e6\n\23\r\23\16\23\u00e7\3\24\3\24\3"+
		"\24\3\24\7\24\u00ee\n\24\f\24\16\24\u00f1\13\24\5\24\u00f3\n\24\3\24\3"+
		"\24\3\24\2\5\n\24\"\25\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&\2\5"+
		"\3\2\658\4\2\4\4\21\24\4\2\3\13\r\20\2\u010b\2(\3\2\2\2\4\63\3\2\2\2\6"+
		"E\3\2\2\2\bG\3\2\2\2\ns\3\2\2\2\f\u0080\3\2\2\2\16\u0093\3\2\2\2\20\u0095"+
		"\3\2\2\2\22\u00a1\3\2\2\2\24\u00a6\3\2\2\2\26\u00b0\3\2\2\2\30\u00b2\3"+
		"\2\2\2\32\u00b6\3\2\2\2\34\u00c0\3\2\2\2\36\u00c2\3\2\2\2 \u00c4\3\2\2"+
		"\2\"\u00d5\3\2\2\2$\u00e0\3\2\2\2&\u00e9\3\2\2\2(,\7\"\2\2)+\5\4\3\2*"+
		")\3\2\2\2+.\3\2\2\2,*\3\2\2\2,-\3\2\2\2-/\3\2\2\2.,\3\2\2\2/\60\5\n\6"+
		"\2\60\61\7#\2\2\61\62\7\2\2\3\62\3\3\2\2\2\63\64\5\24\13\2\64\65\7>\2"+
		"\2\65\66\7\27\2\2\66\67\5\6\4\2\678\7\30\2\289\7$\2\29:\5\n\6\2:;\7#\2"+
		"\2;\5\3\2\2\2<A\5\b\5\2=>\7\33\2\2>@\5\b\5\2?=\3\2\2\2@C\3\2\2\2A?\3\2"+
		"\2\2AB\3\2\2\2BF\3\2\2\2CA\3\2\2\2DF\3\2\2\2E<\3\2\2\2ED\3\2\2\2F\7\3"+
		"\2\2\2GH\5\24\13\2HI\7>\2\2I\t\3\2\2\2JK\b\6\1\2Kt\7\61\2\2LM\5\24\13"+
		"\2MN\7>\2\2NO\7\f\2\2OP\5\16\b\2Pt\3\2\2\2QR\5\f\7\2RS\7\f\2\2ST\5\16"+
		"\b\2Tt\3\2\2\2UV\7%\2\2Vt\5\f\7\2WX\7&\2\2Xt\5\"\22\2YZ\7\'\2\2Zt\5\""+
		"\22\2[\\\7(\2\2\\t\5\"\22\2]^\7)\2\2^t\5\"\22\2_`\7*\2\2`t\5\"\22\2ab"+
		"\7+\2\2bc\5\"\22\2cd\7,\2\2de\5\n\6\2ef\7-\2\2fg\5\n\6\2gh\7.\2\2ht\3"+
		"\2\2\2ij\7/\2\2jk\5\"\22\2kl\7\62\2\2lm\5\n\6\2mn\7\60\2\2nt\3\2\2\2o"+
		"p\7\"\2\2pq\5\n\6\2qr\7#\2\2rt\3\2\2\2sJ\3\2\2\2sL\3\2\2\2sQ\3\2\2\2s"+
		"U\3\2\2\2sW\3\2\2\2sY\3\2\2\2s[\3\2\2\2s]\3\2\2\2s_\3\2\2\2sa\3\2\2\2"+
		"si\3\2\2\2so\3\2\2\2tz\3\2\2\2uv\f\3\2\2vw\7\34\2\2wy\5\n\6\4xu\3\2\2"+
		"\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2\2{\13\3\2\2\2|z\3\2\2\2}\u0081\7>\2\2~"+
		"\u0081\5$\23\2\177\u0081\5\22\n\2\u0080}\3\2\2\2\u0080~\3\2\2\2\u0080"+
		"\177\3\2\2\2\u0081\r\3\2\2\2\u0082\u0094\5\"\22\2\u0083\u0094\5&\24\2"+
		"\u0084\u0085\7\63\2\2\u0085\u0086\7\27\2\2\u0086\u0087\5\"\22\2\u0087"+
		"\u0088\7\33\2\2\u0088\u0089\5\"\22\2\u0089\u008a\7\30\2\2\u008a\u0094"+
		"\3\2\2\2\u008b\u0094\5\22\n\2\u008c\u008d\7\64\2\2\u008d\u008e\7>\2\2"+
		"\u008e\u0090\7\27\2\2\u008f\u0091\5\20\t\2\u0090\u008f\3\2\2\2\u0090\u0091"+
		"\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u0094\7\30\2\2\u0093\u0082\3\2\2\2"+
		"\u0093\u0083\3\2\2\2\u0093\u0084\3\2\2\2\u0093\u008b\3\2\2\2\u0093\u008c"+
		"\3\2\2\2\u0094\17\3\2\2\2\u0095\u009a\5\"\22\2\u0096\u0097\7\33\2\2\u0097"+
		"\u0099\5\"\22\2\u0098\u0096\3\2\2\2\u0099\u009c\3\2\2\2\u009a\u0098\3"+
		"\2\2\2\u009a\u009b\3\2\2\2\u009b\21\3\2\2\2\u009c\u009a\3\2\2\2\u009d"+
		"\u009e\7\36\2\2\u009e\u00a2\5\"\22\2\u009f\u00a0\7\37\2\2\u00a0\u00a2"+
		"\5\"\22\2\u00a1\u009d\3\2\2\2\u00a1\u009f\3\2\2\2\u00a2\23\3\2\2\2\u00a3"+
		"\u00a4\b\13\1\2\u00a4\u00a7\5\26\f\2\u00a5\u00a7\5\32\16\2\u00a6\u00a3"+
		"\3\2\2\2\u00a6\u00a5\3\2\2\2\u00a7\u00ad\3\2\2\2\u00a8\u00a9\f\4\2\2\u00a9"+
		"\u00aa\7\31\2\2\u00aa\u00ac\7\32\2\2\u00ab\u00a8\3\2\2\2\u00ac\u00af\3"+
		"\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\25\3\2\2\2\u00af"+
		"\u00ad\3\2\2\2\u00b0\u00b1\t\2\2\2\u00b1\27\3\2\2\2\u00b2\u00b3\5\24\13"+
		"\2\u00b3\u00b4\7\31\2\2\u00b4\u00b5\7\32\2\2\u00b5\31\3\2\2\2\u00b6\u00b7"+
		"\7\35\2\2\u00b7\u00b8\7\27\2\2\u00b8\u00b9\5\34\17\2\u00b9\u00ba\7\33"+
		"\2\2\u00ba\u00bb\5\34\17\2\u00bb\u00bc\7\30\2\2\u00bc\33\3\2\2\2\u00bd"+
		"\u00c1\5\26\f\2\u00be\u00c1\5\30\r\2\u00bf\u00c1\7\35\2\2\u00c0\u00bd"+
		"\3\2\2\2\u00c0\u00be\3\2\2\2\u00c0\u00bf\3\2\2\2\u00c1\35\3\2\2\2\u00c2"+
		"\u00c3\t\3\2\2\u00c3\37\3\2\2\2\u00c4\u00c5\t\4\2\2\u00c5!\3\2\2\2\u00c6"+
		"\u00c7\b\22\1\2\u00c7\u00d6\79\2\2\u00c8\u00d6\7:\2\2\u00c9\u00d6\7;\2"+
		"\2\u00ca\u00d6\7<\2\2\u00cb\u00d6\7=\2\2\u00cc\u00d6\7>\2\2\u00cd\u00d6"+
		"\5$\23\2\u00ce\u00cf\5\36\20\2\u00cf\u00d0\5\"\22\4\u00d0\u00d6\3\2\2"+
		"\2\u00d1\u00d2\7\27\2\2\u00d2\u00d3\5\"\22\2\u00d3\u00d4\7\30\2\2\u00d4"+
		"\u00d6\3\2\2\2\u00d5\u00c6\3\2\2\2\u00d5\u00c8\3\2\2\2\u00d5\u00c9\3\2"+
		"\2\2\u00d5\u00ca\3\2\2\2\u00d5\u00cb\3\2\2\2\u00d5\u00cc\3\2\2\2\u00d5"+
		"\u00cd\3\2\2\2\u00d5\u00ce\3\2\2\2\u00d5\u00d1\3\2\2\2\u00d6\u00dd\3\2"+
		"\2\2\u00d7\u00d8\f\f\2\2\u00d8\u00d9\5 \21\2\u00d9\u00da\5\"\22\r\u00da"+
		"\u00dc\3\2\2\2\u00db\u00d7\3\2\2\2\u00dc\u00df\3\2\2\2\u00dd\u00db\3\2"+
		"\2\2\u00dd\u00de\3\2\2\2\u00de#\3\2\2\2\u00df\u00dd\3\2\2\2\u00e0\u00e5"+
		"\7>\2\2\u00e1\u00e2\7\31\2\2\u00e2\u00e3\5\"\22\2\u00e3\u00e4\7\32\2\2"+
		"\u00e4\u00e6\3\2\2\2\u00e5\u00e1\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e5"+
		"\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8%\3\2\2\2\u00e9\u00f2\7\31\2\2\u00ea"+
		"\u00ef\5\"\22\2\u00eb\u00ec\7\33\2\2\u00ec\u00ee\5\"\22\2\u00ed\u00eb"+
		"\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0"+
		"\u00f3\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f2\u00ea\3\2\2\2\u00f2\u00f3\3\2"+
		"\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f5\7\32\2\2\u00f5\'\3\2\2\2\24,AEsz"+
		"\u0080\u0090\u0093\u009a\u00a1\u00a6\u00ad\u00c0\u00d5\u00dd\u00e7\u00ef"+
		"\u00f2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}