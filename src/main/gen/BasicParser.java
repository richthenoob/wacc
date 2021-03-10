// Generated from /Users/zirun/wacc_22/src/main/antlr/BasicParser.g4 by ANTLR 4.9.1

    package ic.doc.antlr;

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
		EQ=11, NEQ=12, AND=13, OR=14, NOT=15, LEN=16, ORD=17, CHR=18, DOT=19, 
		SQUOTE=20, DQUOTE=21, OPEN_PARENTHESES=22, CLOSE_PARENTHESES=23, OPEN_BRACKETS=24, 
		CLOSE_BRACKETS=25, OPEN_CURLY_BRACES=26, CLOSE_CURLY_BRACES=27, COMMA=28, 
		SEMI=29, PAIR=30, FST=31, SND=32, COMMENT=33, WS=34, INTEGER=35, CLASS=36, 
		NEW=37, BEGIN=38, END=39, IS=40, READ=41, FREE=42, RETURN=43, EXIT=44, 
		PRINT=45, PRINTLN=46, IF=47, THEN=48, ELSE=49, FI=50, WHILE=51, DONE=52, 
		SKP=53, DO=54, NEWPAIR=55, CALL=56, INT=57, BOOL=58, CHAR=59, STR=60, 
		BOOL_LITER=61, CHAR_LITER=62, STR_LITER=63, PAIR_LITER=64, IDENT=65;
	public static final int
		RULE_prog = 0, RULE_class_ = 1, RULE_classEntry = 2, RULE_func = 3, RULE_paramList = 4, 
		RULE_param = 5, RULE_stat = 6, RULE_assignLhs = 7, RULE_assignRhs = 8, 
		RULE_argList = 9, RULE_pairElem = 10, RULE_classObject = 11, RULE_type = 12, 
		RULE_baseType = 13, RULE_arrayType = 14, RULE_pairType = 15, RULE_pairElemType = 16, 
		RULE_expr = 17, RULE_intSign = 18, RULE_intLiter = 19, RULE_arrayElem = 20, 
		RULE_arrayLiter = 21;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "class_", "classEntry", "func", "paramList", "param", "stat", 
			"assignLhs", "assignRhs", "argList", "pairElem", "classObject", "type", 
			"baseType", "arrayType", "pairType", "pairElemType", "expr", "intSign", 
			"intLiter", "arrayElem", "arrayLiter"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", "'>='", "'<'", "'<='", 
			"'='", "'=='", "'!='", "'&&'", "'||'", "'!'", "'len'", "'ord'", "'chr'", 
			"'.'", "'''", "'\"'", "'('", "')'", "'['", "']'", "'{'", "'}'", "','", 
			"';'", "'pair'", "'fst'", "'snd'", null, null, null, "'class'", "'new'", 
			"'begin'", "'end'", "'is'", "'read'", "'free'", "'return'", "'exit'", 
			"'print'", "'println'", "'if'", "'then'", "'else'", "'fi'", "'while'", 
			"'done'", "'skip'", "'do'", "'newpair'", "'call'", "'int'", "'bool'", 
			"'char'", "'string'", null, null, null, "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PLUS", "MINUS", "MUL", "DIV", "MOD", "GT", "GTE", "LT", "LTE", 
			"ASSIGN", "EQ", "NEQ", "AND", "OR", "NOT", "LEN", "ORD", "CHR", "DOT", 
			"SQUOTE", "DQUOTE", "OPEN_PARENTHESES", "CLOSE_PARENTHESES", "OPEN_BRACKETS", 
			"CLOSE_BRACKETS", "OPEN_CURLY_BRACES", "CLOSE_CURLY_BRACES", "COMMA", 
			"SEMI", "PAIR", "FST", "SND", "COMMENT", "WS", "INTEGER", "CLASS", "NEW", 
			"BEGIN", "END", "IS", "READ", "FREE", "RETURN", "EXIT", "PRINT", "PRINTLN", 
			"IF", "THEN", "ELSE", "FI", "WHILE", "DONE", "SKP", "DO", "NEWPAIR", 
			"CALL", "INT", "BOOL", "CHAR", "STR", "BOOL_LITER", "CHAR_LITER", "STR_LITER", 
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
		public List<Class_Context> class_() {
			return getRuleContexts(Class_Context.class);
		}
		public Class_Context class_(int i) {
			return getRuleContext(Class_Context.class,i);
		}
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
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(BEGIN);
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CLASS) {
				{
				{
				setState(45);
				class_();
				}
				}
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(54);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(51);
					func();
					}
					} 
				}
				setState(56);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			setState(57);
			stat(0);
			setState(58);
			match(END);
			setState(59);
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

	public static class Class_Context extends ParserRuleContext {
		public TerminalNode CLASS() { return getToken(BasicParser.CLASS, 0); }
		public TerminalNode IDENT() { return getToken(BasicParser.IDENT, 0); }
		public TerminalNode OPEN_CURLY_BRACES() { return getToken(BasicParser.OPEN_CURLY_BRACES, 0); }
		public TerminalNode CLOSE_CURLY_BRACES() { return getToken(BasicParser.CLOSE_CURLY_BRACES, 0); }
		public List<ClassEntryContext> classEntry() {
			return getRuleContexts(ClassEntryContext.class);
		}
		public ClassEntryContext classEntry(int i) {
			return getRuleContext(ClassEntryContext.class,i);
		}
		public Class_Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_class_; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterClass_(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitClass_(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitClass_(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Class_Context class_() throws RecognitionException {
		Class_Context _localctx = new Class_Context(_ctx, getState());
		enterRule(_localctx, 2, RULE_class_);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			match(CLASS);
			setState(62);
			match(IDENT);
			setState(63);
			match(OPEN_CURLY_BRACES);
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PAIR) | (1L << INT) | (1L << BOOL) | (1L << CHAR) | (1L << STR))) != 0)) {
				{
				{
				setState(64);
				classEntry();
				}
				}
				setState(69);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(70);
			match(CLOSE_CURLY_BRACES);
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

	public static class ClassEntryContext extends ParserRuleContext {
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
		public FuncContext func() {
			return getRuleContext(FuncContext.class,0);
		}
		public ClassEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterClassEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitClassEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitClassEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassEntryContext classEntry() throws RecognitionException {
		ClassEntryContext _localctx = new ClassEntryContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classEntry);
		int _la;
		try {
			setState(81);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(72);
				param();
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(73);
					match(COMMA);
					setState(74);
					param();
					}
					}
					setState(79);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(80);
				func();
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
		enterRule(_localctx, 6, RULE_func);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			type(0);
			setState(84);
			match(IDENT);
			setState(85);
			match(OPEN_PARENTHESES);
			setState(86);
			paramList();
			setState(87);
			match(CLOSE_PARENTHESES);
			setState(88);
			match(IS);
			setState(89);
			stat(0);
			setState(90);
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
		enterRule(_localctx, 8, RULE_paramList);
		int _la;
		try {
			setState(101);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PAIR:
			case INT:
			case BOOL:
			case CHAR:
			case STR:
				enterOuterAlt(_localctx, 1);
				{
				setState(92);
				param();
				setState(97);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(93);
					match(COMMA);
					setState(94);
					param();
					}
					}
					setState(99);
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
		enterRule(_localctx, 10, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			type(0);
			setState(104);
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
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
	 
		public StatContext() { }
		public void copyFrom(StatContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DeclarativeAssignmentContext extends StatContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENT() { return getToken(BasicParser.IDENT, 0); }
		public TerminalNode ASSIGN() { return getToken(BasicParser.ASSIGN, 0); }
		public AssignRhsContext assignRhs() {
			return getRuleContext(AssignRhsContext.class,0);
		}
		public DeclarativeAssignmentContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterDeclarativeAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitDeclarativeAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitDeclarativeAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReadContext extends StatContext {
		public TerminalNode READ() { return getToken(BasicParser.READ, 0); }
		public AssignLhsContext assignLhs() {
			return getRuleContext(AssignLhsContext.class,0);
		}
		public ReadContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterRead(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitRead(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitRead(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignmentContext extends StatContext {
		public AssignLhsContext assignLhs() {
			return getRuleContext(AssignLhsContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(BasicParser.ASSIGN, 0); }
		public AssignRhsContext assignRhs() {
			return getRuleContext(AssignRhsContext.class,0);
		}
		public AssignmentContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SkipContext extends StatContext {
		public TerminalNode SKP() { return getToken(BasicParser.SKP, 0); }
		public SkipContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterSkip(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitSkip(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitSkip(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhileContext extends StatContext {
		public TerminalNode WHILE() { return getToken(BasicParser.WHILE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode DO() { return getToken(BasicParser.DO, 0); }
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public TerminalNode DONE() { return getToken(BasicParser.DONE, 0); }
		public WhileContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterWhile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitWhile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitWhile(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DeclareNewClassContext extends StatContext {
		public List<TerminalNode> IDENT() { return getTokens(BasicParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(BasicParser.IDENT, i);
		}
		public TerminalNode ASSIGN() { return getToken(BasicParser.ASSIGN, 0); }
		public TerminalNode NEW() { return getToken(BasicParser.NEW, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(BasicParser.OPEN_PARENTHESES, 0); }
		public TerminalNode CLOSE_PARENTHESES() { return getToken(BasicParser.CLOSE_PARENTHESES, 0); }
		public DeclareNewClassContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterDeclareNewClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitDeclareNewClass(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitDeclareNewClass(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExitContext extends StatContext {
		public TerminalNode EXIT() { return getToken(BasicParser.EXIT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExitContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterExit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitExit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitExit(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintContext extends StatContext {
		public TerminalNode PRINT() { return getToken(BasicParser.PRINT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PrintContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterPrint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitPrint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitPrint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintlnContext extends StatContext {
		public TerminalNode PRINTLN() { return getToken(BasicParser.PRINTLN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PrintlnContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterPrintln(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitPrintln(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitPrintln(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SemiContext extends StatContext {
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public TerminalNode SEMI() { return getToken(BasicParser.SEMI, 0); }
		public SemiContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterSemi(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitSemi(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitSemi(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FreeContext extends StatContext {
		public TerminalNode FREE() { return getToken(BasicParser.FREE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public FreeContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterFree(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitFree(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitFree(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfContext extends StatContext {
		public TerminalNode IF() { return getToken(BasicParser.IF, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode THEN() { return getToken(BasicParser.THEN, 0); }
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(BasicParser.ELSE, 0); }
		public TerminalNode FI() { return getToken(BasicParser.FI, 0); }
		public IfContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitIf(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BeginContext extends StatContext {
		public TerminalNode BEGIN() { return getToken(BasicParser.BEGIN, 0); }
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public TerminalNode END() { return getToken(BasicParser.END, 0); }
		public BeginContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterBegin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitBegin(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitBegin(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReturnContext extends StatContext {
		public TerminalNode RETURN() { return getToken(BasicParser.RETURN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ReturnContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterReturn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitReturn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitReturn(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignNewClassContext extends StatContext {
		public List<TerminalNode> IDENT() { return getTokens(BasicParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(BasicParser.IDENT, i);
		}
		public TerminalNode ASSIGN() { return getToken(BasicParser.ASSIGN, 0); }
		public AssignNewClassContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterAssignNewClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitAssignNewClass(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitAssignNewClass(this);
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
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_stat, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				_localctx = new SkipContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(107);
				match(SKP);
				}
				break;
			case 2:
				{
				_localctx = new DeclareNewClassContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(108);
				match(IDENT);
				setState(109);
				match(IDENT);
				setState(110);
				match(ASSIGN);
				setState(111);
				match(NEW);
				setState(112);
				match(IDENT);
				setState(113);
				match(OPEN_PARENTHESES);
				setState(114);
				match(CLOSE_PARENTHESES);
				}
				break;
			case 3:
				{
				_localctx = new AssignNewClassContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(115);
				match(IDENT);
				setState(116);
				match(IDENT);
				setState(117);
				match(ASSIGN);
				setState(118);
				match(IDENT);
				}
				break;
			case 4:
				{
				_localctx = new DeclarativeAssignmentContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(119);
				type(0);
				setState(120);
				match(IDENT);
				setState(121);
				match(ASSIGN);
				setState(122);
				assignRhs();
				}
				break;
			case 5:
				{
				_localctx = new AssignmentContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(124);
				assignLhs();
				setState(125);
				match(ASSIGN);
				setState(126);
				assignRhs();
				}
				break;
			case 6:
				{
				_localctx = new ReadContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(128);
				match(READ);
				setState(129);
				assignLhs();
				}
				break;
			case 7:
				{
				_localctx = new FreeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(130);
				match(FREE);
				setState(131);
				expr(0);
				}
				break;
			case 8:
				{
				_localctx = new ReturnContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(132);
				match(RETURN);
				setState(133);
				expr(0);
				}
				break;
			case 9:
				{
				_localctx = new ExitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(134);
				match(EXIT);
				setState(135);
				expr(0);
				}
				break;
			case 10:
				{
				_localctx = new PrintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(136);
				match(PRINT);
				setState(137);
				expr(0);
				}
				break;
			case 11:
				{
				_localctx = new PrintlnContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(138);
				match(PRINTLN);
				setState(139);
				expr(0);
				}
				break;
			case 12:
				{
				_localctx = new IfContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(140);
				match(IF);
				setState(141);
				expr(0);
				setState(142);
				match(THEN);
				setState(143);
				stat(0);
				setState(144);
				match(ELSE);
				setState(145);
				stat(0);
				setState(146);
				match(FI);
				}
				break;
			case 13:
				{
				_localctx = new WhileContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(148);
				match(WHILE);
				setState(149);
				expr(0);
				setState(150);
				match(DO);
				setState(151);
				stat(0);
				setState(152);
				match(DONE);
				}
				break;
			case 14:
				{
				_localctx = new BeginContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(154);
				match(BEGIN);
				setState(155);
				stat(0);
				setState(156);
				match(END);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(165);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new SemiContext(new StatContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_stat);
					setState(160);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(161);
					match(SEMI);
					setState(162);
					stat(2);
					}
					} 
				}
				setState(167);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
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
		public ClassObjectContext classObject() {
			return getRuleContext(ClassObjectContext.class,0);
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
		enterRule(_localctx, 14, RULE_assignLhs);
		try {
			setState(172);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(168);
				match(IDENT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(169);
				arrayElem();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(170);
				pairElem();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(171);
				classObject();
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
		public AssignRhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignRhs; }
	 
		public AssignRhsContext() { }
		public void copyFrom(AssignRhsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExprDupContext extends AssignRhsContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExprDupContext(AssignRhsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterExprDup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitExprDup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitExprDup(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CallContext extends AssignRhsContext {
		public TerminalNode CALL() { return getToken(BasicParser.CALL, 0); }
		public TerminalNode IDENT() { return getToken(BasicParser.IDENT, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(BasicParser.OPEN_PARENTHESES, 0); }
		public ArgListContext argList() {
			return getRuleContext(ArgListContext.class,0);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(BasicParser.CLOSE_PARENTHESES, 0); }
		public CallContext(AssignRhsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PairElemDupContext extends AssignRhsContext {
		public PairElemContext pairElem() {
			return getRuleContext(PairElemContext.class,0);
		}
		public PairElemDupContext(AssignRhsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterPairElemDup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitPairElemDup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitPairElemDup(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NewPairContext extends AssignRhsContext {
		public TerminalNode NEWPAIR() { return getToken(BasicParser.NEWPAIR, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(BasicParser.OPEN_PARENTHESES, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(BasicParser.COMMA, 0); }
		public TerminalNode CLOSE_PARENTHESES() { return getToken(BasicParser.CLOSE_PARENTHESES, 0); }
		public NewPairContext(AssignRhsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterNewPair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitNewPair(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitNewPair(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CallClassFunctionContext extends AssignRhsContext {
		public TerminalNode CALL() { return getToken(BasicParser.CALL, 0); }
		public ClassObjectContext classObject() {
			return getRuleContext(ClassObjectContext.class,0);
		}
		public TerminalNode OPEN_PARENTHESES() { return getToken(BasicParser.OPEN_PARENTHESES, 0); }
		public TerminalNode CLOSE_PARENTHESES() { return getToken(BasicParser.CLOSE_PARENTHESES, 0); }
		public CallClassFunctionContext(AssignRhsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterCallClassFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitCallClassFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitCallClassFunction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayLiterDupContext extends AssignRhsContext {
		public ArrayLiterContext arrayLiter() {
			return getRuleContext(ArrayLiterContext.class,0);
		}
		public ArrayLiterDupContext(AssignRhsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterArrayLiterDup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitArrayLiterDup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitArrayLiterDup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignRhsContext assignRhs() throws RecognitionException {
		AssignRhsContext _localctx = new AssignRhsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_assignRhs);
		try {
			setState(195);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				_localctx = new ExprDupContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(174);
				expr(0);
				}
				break;
			case 2:
				_localctx = new ArrayLiterDupContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(175);
				arrayLiter();
				}
				break;
			case 3:
				_localctx = new NewPairContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(176);
				match(NEWPAIR);
				setState(177);
				match(OPEN_PARENTHESES);
				setState(178);
				expr(0);
				setState(179);
				match(COMMA);
				setState(180);
				expr(0);
				setState(181);
				match(CLOSE_PARENTHESES);
				}
				break;
			case 4:
				_localctx = new PairElemDupContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(183);
				pairElem();
				}
				break;
			case 5:
				_localctx = new CallContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(184);
				match(CALL);
				setState(185);
				match(IDENT);
				setState(186);
				match(OPEN_PARENTHESES);
				setState(187);
				argList();
				setState(188);
				match(CLOSE_PARENTHESES);
				}
				break;
			case 6:
				_localctx = new CallClassFunctionContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(190);
				match(CALL);
				setState(191);
				classObject();
				setState(192);
				match(OPEN_PARENTHESES);
				setState(193);
				match(CLOSE_PARENTHESES);
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
		enterRule(_localctx, 18, RULE_argList);
		int _la;
		try {
			setState(206);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
			case MINUS:
			case NOT:
			case LEN:
			case ORD:
			case CHR:
			case OPEN_PARENTHESES:
			case INTEGER:
			case BOOL_LITER:
			case CHAR_LITER:
			case STR_LITER:
			case PAIR_LITER:
			case IDENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(197);
				expr(0);
				setState(202);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(198);
					match(COMMA);
					setState(199);
					expr(0);
					}
					}
					setState(204);
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

	public static class PairElemContext extends ParserRuleContext {
		public PairElemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pairElem; }
	 
		public PairElemContext() { }
		public void copyFrom(PairElemContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SndPairElemContext extends PairElemContext {
		public TerminalNode SND() { return getToken(BasicParser.SND, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public SndPairElemContext(PairElemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterSndPairElem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitSndPairElem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitSndPairElem(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FstPairElemContext extends PairElemContext {
		public TerminalNode FST() { return getToken(BasicParser.FST, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public FstPairElemContext(PairElemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterFstPairElem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitFstPairElem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitFstPairElem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairElemContext pairElem() throws RecognitionException {
		PairElemContext _localctx = new PairElemContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_pairElem);
		try {
			setState(212);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FST:
				_localctx = new FstPairElemContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(208);
				match(FST);
				setState(209);
				expr(0);
				}
				break;
			case SND:
				_localctx = new SndPairElemContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(210);
				match(SND);
				setState(211);
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

	public static class ClassObjectContext extends ParserRuleContext {
		public List<TerminalNode> IDENT() { return getTokens(BasicParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(BasicParser.IDENT, i);
		}
		public TerminalNode DOT() { return getToken(BasicParser.DOT, 0); }
		public ClassObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classObject; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterClassObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitClassObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitClassObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassObjectContext classObject() throws RecognitionException {
		ClassObjectContext _localctx = new ClassObjectContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_classObject);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			match(IDENT);
			setState(215);
			match(DOT);
			setState(216);
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

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PairTypeDupContext extends TypeContext {
		public PairTypeContext pairType() {
			return getRuleContext(PairTypeContext.class,0);
		}
		public PairTypeDupContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterPairTypeDup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitPairTypeDup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitPairTypeDup(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BaseTypeDupContext extends TypeContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public BaseTypeDupContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterBaseTypeDup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitBaseTypeDup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitBaseTypeDup(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayTypeDupContext extends TypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode OPEN_BRACKETS() { return getToken(BasicParser.OPEN_BRACKETS, 0); }
		public TerminalNode CLOSE_BRACKETS() { return getToken(BasicParser.CLOSE_BRACKETS, 0); }
		public ArrayTypeDupContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterArrayTypeDup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitArrayTypeDup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitArrayTypeDup(this);
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
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
			case BOOL:
			case CHAR:
			case STR:
				{
				_localctx = new BaseTypeDupContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(219);
				baseType();
				}
				break;
			case PAIR:
				{
				_localctx = new PairTypeDupContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(220);
				pairType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(228);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ArrayTypeDupContext(new TypeContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_type);
					setState(223);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(224);
					match(OPEN_BRACKETS);
					setState(225);
					match(CLOSE_BRACKETS);
					}
					} 
				}
				setState(230);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
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
		public BaseTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseType; }
	 
		public BaseTypeContext() { }
		public void copyFrom(BaseTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CharTypeContext extends BaseTypeContext {
		public TerminalNode CHAR() { return getToken(BasicParser.CHAR, 0); }
		public CharTypeContext(BaseTypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterCharType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitCharType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitCharType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StrTypeContext extends BaseTypeContext {
		public TerminalNode STR() { return getToken(BasicParser.STR, 0); }
		public StrTypeContext(BaseTypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterStrType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitStrType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitStrType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntTypeContext extends BaseTypeContext {
		public TerminalNode INT() { return getToken(BasicParser.INT, 0); }
		public IntTypeContext(BaseTypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterIntType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitIntType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitIntType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolTypeContext extends BaseTypeContext {
		public TerminalNode BOOL() { return getToken(BasicParser.BOOL, 0); }
		public BoolTypeContext(BaseTypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterBoolType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitBoolType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitBoolType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseTypeContext baseType() throws RecognitionException {
		BaseTypeContext _localctx = new BaseTypeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_baseType);
		try {
			setState(235);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				_localctx = new IntTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(231);
				match(INT);
				}
				break;
			case BOOL:
				_localctx = new BoolTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(232);
				match(BOOL);
				}
				break;
			case CHAR:
				_localctx = new CharTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(233);
				match(CHAR);
				}
				break;
			case STR:
				_localctx = new StrTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(234);
				match(STR);
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
		enterRule(_localctx, 28, RULE_arrayType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			type(0);
			setState(238);
			match(OPEN_BRACKETS);
			setState(239);
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
		enterRule(_localctx, 30, RULE_pairType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			match(PAIR);
			setState(242);
			match(OPEN_PARENTHESES);
			setState(243);
			pairElemType();
			setState(244);
			match(COMMA);
			setState(245);
			pairElemType();
			setState(246);
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
		enterRule(_localctx, 32, RULE_pairElemType);
		try {
			setState(251);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(248);
				baseType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(249);
				arrayType();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(250);
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

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BinOp2ApplicationContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(BasicParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(BasicParser.MINUS, 0); }
		public BinOp2ApplicationContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterBinOp2Application(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitBinOp2Application(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitBinOp2Application(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdentifierContext extends ExprContext {
		public TerminalNode IDENT() { return getToken(BasicParser.IDENT, 0); }
		public IdentifierContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinOp1ApplicationContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode MUL() { return getToken(BasicParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(BasicParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(BasicParser.MOD, 0); }
		public BinOp1ApplicationContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterBinOp1Application(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitBinOp1Application(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitBinOp1Application(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayElemDupContext extends ExprContext {
		public ArrayElemContext arrayElem() {
			return getRuleContext(ArrayElemContext.class,0);
		}
		public ArrayElemDupContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterArrayElemDup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitArrayElemDup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitArrayElemDup(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinOp5ApplicationContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode AND() { return getToken(BasicParser.AND, 0); }
		public BinOp5ApplicationContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterBinOp5Application(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitBinOp5Application(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitBinOp5Application(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ClassVariableContext extends ExprContext {
		public ClassObjectContext classObject() {
			return getRuleContext(ClassObjectContext.class,0);
		}
		public ClassVariableContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterClassVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitClassVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitClassVariable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinOp3ApplicationContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode GT() { return getToken(BasicParser.GT, 0); }
		public TerminalNode GTE() { return getToken(BasicParser.GTE, 0); }
		public TerminalNode LT() { return getToken(BasicParser.LT, 0); }
		public TerminalNode LTE() { return getToken(BasicParser.LTE, 0); }
		public BinOp3ApplicationContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterBinOp3Application(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitBinOp3Application(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitBinOp3Application(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinOp4ApplicationContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode EQ() { return getToken(BasicParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(BasicParser.NEQ, 0); }
		public BinOp4ApplicationContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterBinOp4Application(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitBinOp4Application(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitBinOp4Application(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralContext extends ExprContext {
		public IntLiterContext intLiter() {
			return getRuleContext(IntLiterContext.class,0);
		}
		public TerminalNode BOOL_LITER() { return getToken(BasicParser.BOOL_LITER, 0); }
		public TerminalNode CHAR_LITER() { return getToken(BasicParser.CHAR_LITER, 0); }
		public TerminalNode STR_LITER() { return getToken(BasicParser.STR_LITER, 0); }
		public TerminalNode PAIR_LITER() { return getToken(BasicParser.PAIR_LITER, 0); }
		public LiteralContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnOpApplicationContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode NOT() { return getToken(BasicParser.NOT, 0); }
		public TerminalNode MINUS() { return getToken(BasicParser.MINUS, 0); }
		public TerminalNode LEN() { return getToken(BasicParser.LEN, 0); }
		public TerminalNode ORD() { return getToken(BasicParser.ORD, 0); }
		public TerminalNode CHR() { return getToken(BasicParser.CHR, 0); }
		public UnOpApplicationContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterUnOpApplication(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitUnOpApplication(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitUnOpApplication(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BracketsContext extends ExprContext {
		public TerminalNode OPEN_PARENTHESES() { return getToken(BasicParser.OPEN_PARENTHESES, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(BasicParser.CLOSE_PARENTHESES, 0); }
		public BracketsContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterBrackets(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitBrackets(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitBrackets(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinOp6ApplicationContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OR() { return getToken(BasicParser.OR, 0); }
		public BinOp6ApplicationContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterBinOp6Application(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitBinOp6Application(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitBinOp6Application(this);
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
		int _startState = 34;
		enterRecursionRule(_localctx, 34, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				_localctx = new LiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(259);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case PLUS:
				case MINUS:
				case INTEGER:
					{
					setState(254);
					intLiter();
					}
					break;
				case BOOL_LITER:
					{
					setState(255);
					match(BOOL_LITER);
					}
					break;
				case CHAR_LITER:
					{
					setState(256);
					match(CHAR_LITER);
					}
					break;
				case STR_LITER:
					{
					setState(257);
					match(STR_LITER);
					}
					break;
				case PAIR_LITER:
					{
					setState(258);
					match(PAIR_LITER);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 2:
				{
				_localctx = new IdentifierContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(261);
				match(IDENT);
				}
				break;
			case 3:
				{
				_localctx = new ArrayElemDupContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(262);
				arrayElem();
				}
				break;
			case 4:
				{
				_localctx = new UnOpApplicationContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(263);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MINUS) | (1L << NOT) | (1L << LEN) | (1L << ORD) | (1L << CHR))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(264);
				expr(3);
				}
				break;
			case 5:
				{
				_localctx = new BracketsContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(265);
				match(OPEN_PARENTHESES);
				setState(266);
				expr(0);
				setState(267);
				match(CLOSE_PARENTHESES);
				}
				break;
			case 6:
				{
				_localctx = new ClassVariableContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(269);
				classObject();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(292);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(290);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
					case 1:
						{
						_localctx = new BinOp1ApplicationContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(272);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(273);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(274);
						expr(13);
						}
						break;
					case 2:
						{
						_localctx = new BinOp2ApplicationContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(275);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(276);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(277);
						expr(12);
						}
						break;
					case 3:
						{
						_localctx = new BinOp3ApplicationContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(278);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(279);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << GTE) | (1L << LT) | (1L << LTE))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(280);
						expr(11);
						}
						break;
					case 4:
						{
						_localctx = new BinOp4ApplicationContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(281);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(282);
						_la = _input.LA(1);
						if ( !(_la==EQ || _la==NEQ) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(283);
						expr(10);
						}
						break;
					case 5:
						{
						_localctx = new BinOp5ApplicationContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(284);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						{
						setState(285);
						match(AND);
						}
						setState(286);
						expr(9);
						}
						break;
					case 6:
						{
						_localctx = new BinOp6ApplicationContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(287);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						{
						setState(288);
						match(OR);
						}
						setState(289);
						expr(8);
						}
						break;
					}
					} 
				}
				setState(294);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
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

	public static class IntSignContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(BasicParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(BasicParser.MINUS, 0); }
		public IntSignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intSign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterIntSign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitIntSign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitIntSign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntSignContext intSign() throws RecognitionException {
		IntSignContext _localctx = new IntSignContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_intSign);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(295);
			_la = _input.LA(1);
			if ( !(_la==PLUS || _la==MINUS) ) {
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

	public static class IntLiterContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(BasicParser.INTEGER, 0); }
		public IntSignContext intSign() {
			return getRuleContext(IntSignContext.class,0);
		}
		public IntLiterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intLiter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).enterIntLiter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicParserListener ) ((BasicParserListener)listener).exitIntLiter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BasicParserVisitor ) return ((BasicParserVisitor<? extends T>)visitor).visitIntLiter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntLiterContext intLiter() throws RecognitionException {
		IntLiterContext _localctx = new IntLiterContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_intLiter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(298);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PLUS || _la==MINUS) {
				{
				setState(297);
				intSign();
				}
			}

			setState(300);
			match(INTEGER);
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
		enterRule(_localctx, 40, RULE_arrayElem);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(302);
			match(IDENT);
			setState(307); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(303);
					match(OPEN_BRACKETS);
					setState(304);
					expr(0);
					setState(305);
					match(CLOSE_BRACKETS);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(309); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
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
		enterRule(_localctx, 42, RULE_arrayLiter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(311);
			match(OPEN_BRACKETS);
			setState(320);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << LEN) | (1L << ORD) | (1L << CHR) | (1L << OPEN_PARENTHESES) | (1L << INTEGER) | (1L << BOOL_LITER) | (1L << CHAR_LITER) | (1L << STR_LITER))) != 0) || _la==PAIR_LITER || _la==IDENT) {
				{
				setState(312);
				expr(0);
				setState(317);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(313);
					match(COMMA);
					setState(314);
					expr(0);
					}
					}
					setState(319);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(322);
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
		case 6:
			return stat_sempred((StatContext)_localctx, predIndex);
		case 12:
			return type_sempred((TypeContext)_localctx, predIndex);
		case 17:
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
			return precpred(_ctx, 12);
		case 3:
			return precpred(_ctx, 11);
		case 4:
			return precpred(_ctx, 10);
		case 5:
			return precpred(_ctx, 9);
		case 6:
			return precpred(_ctx, 8);
		case 7:
			return precpred(_ctx, 7);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3C\u0147\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\7\2\61\n\2"+
		"\f\2\16\2\64\13\2\3\2\7\2\67\n\2\f\2\16\2:\13\2\3\2\3\2\3\2\3\2\3\3\3"+
		"\3\3\3\3\3\7\3D\n\3\f\3\16\3G\13\3\3\3\3\3\3\4\3\4\3\4\7\4N\n\4\f\4\16"+
		"\4Q\13\4\3\4\5\4T\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6"+
		"\7\6b\n\6\f\6\16\6e\13\6\3\6\5\6h\n\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u00a1\n\b\3\b\3\b"+
		"\3\b\7\b\u00a6\n\b\f\b\16\b\u00a9\13\b\3\t\3\t\3\t\3\t\5\t\u00af\n\t\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\5\n\u00c6\n\n\3\13\3\13\3\13\7\13\u00cb\n\13\f\13\16\13\u00ce"+
		"\13\13\3\13\5\13\u00d1\n\13\3\f\3\f\3\f\3\f\5\f\u00d7\n\f\3\r\3\r\3\r"+
		"\3\r\3\16\3\16\3\16\5\16\u00e0\n\16\3\16\3\16\3\16\7\16\u00e5\n\16\f\16"+
		"\16\16\u00e8\13\16\3\17\3\17\3\17\3\17\5\17\u00ee\n\17\3\20\3\20\3\20"+
		"\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\5\22\u00fe\n\22"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u0106\n\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\5\23\u0111\n\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\7\23\u0125\n\23"+
		"\f\23\16\23\u0128\13\23\3\24\3\24\3\25\5\25\u012d\n\25\3\25\3\25\3\26"+
		"\3\26\3\26\3\26\3\26\6\26\u0136\n\26\r\26\16\26\u0137\3\27\3\27\3\27\3"+
		"\27\7\27\u013e\n\27\f\27\16\27\u0141\13\27\5\27\u0143\n\27\3\27\3\27\3"+
		"\27\2\5\16\32$\30\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,\2\7\4"+
		"\2\4\4\21\24\3\2\5\7\3\2\3\4\3\2\b\13\3\2\r\16\2\u016a\2.\3\2\2\2\4?\3"+
		"\2\2\2\6S\3\2\2\2\bU\3\2\2\2\ng\3\2\2\2\fi\3\2\2\2\16\u00a0\3\2\2\2\20"+
		"\u00ae\3\2\2\2\22\u00c5\3\2\2\2\24\u00d0\3\2\2\2\26\u00d6\3\2\2\2\30\u00d8"+
		"\3\2\2\2\32\u00df\3\2\2\2\34\u00ed\3\2\2\2\36\u00ef\3\2\2\2 \u00f3\3\2"+
		"\2\2\"\u00fd\3\2\2\2$\u0110\3\2\2\2&\u0129\3\2\2\2(\u012c\3\2\2\2*\u0130"+
		"\3\2\2\2,\u0139\3\2\2\2.\62\7(\2\2/\61\5\4\3\2\60/\3\2\2\2\61\64\3\2\2"+
		"\2\62\60\3\2\2\2\62\63\3\2\2\2\638\3\2\2\2\64\62\3\2\2\2\65\67\5\b\5\2"+
		"\66\65\3\2\2\2\67:\3\2\2\28\66\3\2\2\289\3\2\2\29;\3\2\2\2:8\3\2\2\2;"+
		"<\5\16\b\2<=\7)\2\2=>\7\2\2\3>\3\3\2\2\2?@\7&\2\2@A\7C\2\2AE\7\34\2\2"+
		"BD\5\6\4\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2FH\3\2\2\2GE\3\2\2\2"+
		"HI\7\35\2\2I\5\3\2\2\2JO\5\f\7\2KL\7\36\2\2LN\5\f\7\2MK\3\2\2\2NQ\3\2"+
		"\2\2OM\3\2\2\2OP\3\2\2\2PT\3\2\2\2QO\3\2\2\2RT\5\b\5\2SJ\3\2\2\2SR\3\2"+
		"\2\2T\7\3\2\2\2UV\5\32\16\2VW\7C\2\2WX\7\30\2\2XY\5\n\6\2YZ\7\31\2\2Z"+
		"[\7*\2\2[\\\5\16\b\2\\]\7)\2\2]\t\3\2\2\2^c\5\f\7\2_`\7\36\2\2`b\5\f\7"+
		"\2a_\3\2\2\2be\3\2\2\2ca\3\2\2\2cd\3\2\2\2dh\3\2\2\2ec\3\2\2\2fh\3\2\2"+
		"\2g^\3\2\2\2gf\3\2\2\2h\13\3\2\2\2ij\5\32\16\2jk\7C\2\2k\r\3\2\2\2lm\b"+
		"\b\1\2m\u00a1\7\67\2\2no\7C\2\2op\7C\2\2pq\7\f\2\2qr\7\'\2\2rs\7C\2\2"+
		"st\7\30\2\2t\u00a1\7\31\2\2uv\7C\2\2vw\7C\2\2wx\7\f\2\2x\u00a1\7C\2\2"+
		"yz\5\32\16\2z{\7C\2\2{|\7\f\2\2|}\5\22\n\2}\u00a1\3\2\2\2~\177\5\20\t"+
		"\2\177\u0080\7\f\2\2\u0080\u0081\5\22\n\2\u0081\u00a1\3\2\2\2\u0082\u0083"+
		"\7+\2\2\u0083\u00a1\5\20\t\2\u0084\u0085\7,\2\2\u0085\u00a1\5$\23\2\u0086"+
		"\u0087\7-\2\2\u0087\u00a1\5$\23\2\u0088\u0089\7.\2\2\u0089\u00a1\5$\23"+
		"\2\u008a\u008b\7/\2\2\u008b\u00a1\5$\23\2\u008c\u008d\7\60\2\2\u008d\u00a1"+
		"\5$\23\2\u008e\u008f\7\61\2\2\u008f\u0090\5$\23\2\u0090\u0091\7\62\2\2"+
		"\u0091\u0092\5\16\b\2\u0092\u0093\7\63\2\2\u0093\u0094\5\16\b\2\u0094"+
		"\u0095\7\64\2\2\u0095\u00a1\3\2\2\2\u0096\u0097\7\65\2\2\u0097\u0098\5"+
		"$\23\2\u0098\u0099\78\2\2\u0099\u009a\5\16\b\2\u009a\u009b\7\66\2\2\u009b"+
		"\u00a1\3\2\2\2\u009c\u009d\7(\2\2\u009d\u009e\5\16\b\2\u009e\u009f\7)"+
		"\2\2\u009f\u00a1\3\2\2\2\u00a0l\3\2\2\2\u00a0n\3\2\2\2\u00a0u\3\2\2\2"+
		"\u00a0y\3\2\2\2\u00a0~\3\2\2\2\u00a0\u0082\3\2\2\2\u00a0\u0084\3\2\2\2"+
		"\u00a0\u0086\3\2\2\2\u00a0\u0088\3\2\2\2\u00a0\u008a\3\2\2\2\u00a0\u008c"+
		"\3\2\2\2\u00a0\u008e\3\2\2\2\u00a0\u0096\3\2\2\2\u00a0\u009c\3\2\2\2\u00a1"+
		"\u00a7\3\2\2\2\u00a2\u00a3\f\3\2\2\u00a3\u00a4\7\37\2\2\u00a4\u00a6\5"+
		"\16\b\4\u00a5\u00a2\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7"+
		"\u00a8\3\2\2\2\u00a8\17\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00af\7C\2\2"+
		"\u00ab\u00af\5*\26\2\u00ac\u00af\5\26\f\2\u00ad\u00af\5\30\r\2\u00ae\u00aa"+
		"\3\2\2\2\u00ae\u00ab\3\2\2\2\u00ae\u00ac\3\2\2\2\u00ae\u00ad\3\2\2\2\u00af"+
		"\21\3\2\2\2\u00b0\u00c6\5$\23\2\u00b1\u00c6\5,\27\2\u00b2\u00b3\79\2\2"+
		"\u00b3\u00b4\7\30\2\2\u00b4\u00b5\5$\23\2\u00b5\u00b6\7\36\2\2\u00b6\u00b7"+
		"\5$\23\2\u00b7\u00b8\7\31\2\2\u00b8\u00c6\3\2\2\2\u00b9\u00c6\5\26\f\2"+
		"\u00ba\u00bb\7:\2\2\u00bb\u00bc\7C\2\2\u00bc\u00bd\7\30\2\2\u00bd\u00be"+
		"\5\24\13\2\u00be\u00bf\7\31\2\2\u00bf\u00c6\3\2\2\2\u00c0\u00c1\7:\2\2"+
		"\u00c1\u00c2\5\30\r\2\u00c2\u00c3\7\30\2\2\u00c3\u00c4\7\31\2\2\u00c4"+
		"\u00c6\3\2\2\2\u00c5\u00b0\3\2\2\2\u00c5\u00b1\3\2\2\2\u00c5\u00b2\3\2"+
		"\2\2\u00c5\u00b9\3\2\2\2\u00c5\u00ba\3\2\2\2\u00c5\u00c0\3\2\2\2\u00c6"+
		"\23\3\2\2\2\u00c7\u00cc\5$\23\2\u00c8\u00c9\7\36\2\2\u00c9\u00cb\5$\23"+
		"\2\u00ca\u00c8\3\2\2\2\u00cb\u00ce\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd"+
		"\3\2\2\2\u00cd\u00d1\3\2\2\2\u00ce\u00cc\3\2\2\2\u00cf\u00d1\3\2\2\2\u00d0"+
		"\u00c7\3\2\2\2\u00d0\u00cf\3\2\2\2\u00d1\25\3\2\2\2\u00d2\u00d3\7!\2\2"+
		"\u00d3\u00d7\5$\23\2\u00d4\u00d5\7\"\2\2\u00d5\u00d7\5$\23\2\u00d6\u00d2"+
		"\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d7\27\3\2\2\2\u00d8\u00d9\7C\2\2\u00d9"+
		"\u00da\7\25\2\2\u00da\u00db\7C\2\2\u00db\31\3\2\2\2\u00dc\u00dd\b\16\1"+
		"\2\u00dd\u00e0\5\34\17\2\u00de\u00e0\5 \21\2\u00df\u00dc\3\2\2\2\u00df"+
		"\u00de\3\2\2\2\u00e0\u00e6\3\2\2\2\u00e1\u00e2\f\4\2\2\u00e2\u00e3\7\32"+
		"\2\2\u00e3\u00e5\7\33\2\2\u00e4\u00e1\3\2\2\2\u00e5\u00e8\3\2\2\2\u00e6"+
		"\u00e4\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\33\3\2\2\2\u00e8\u00e6\3\2\2"+
		"\2\u00e9\u00ee\7;\2\2\u00ea\u00ee\7<\2\2\u00eb\u00ee\7=\2\2\u00ec\u00ee"+
		"\7>\2\2\u00ed\u00e9\3\2\2\2\u00ed\u00ea\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ed"+
		"\u00ec\3\2\2\2\u00ee\35\3\2\2\2\u00ef\u00f0\5\32\16\2\u00f0\u00f1\7\32"+
		"\2\2\u00f1\u00f2\7\33\2\2\u00f2\37\3\2\2\2\u00f3\u00f4\7 \2\2\u00f4\u00f5"+
		"\7\30\2\2\u00f5\u00f6\5\"\22\2\u00f6\u00f7\7\36\2\2\u00f7\u00f8\5\"\22"+
		"\2\u00f8\u00f9\7\31\2\2\u00f9!\3\2\2\2\u00fa\u00fe\5\34\17\2\u00fb\u00fe"+
		"\5\36\20\2\u00fc\u00fe\7 \2\2\u00fd\u00fa\3\2\2\2\u00fd\u00fb\3\2\2\2"+
		"\u00fd\u00fc\3\2\2\2\u00fe#\3\2\2\2\u00ff\u0105\b\23\1\2\u0100\u0106\5"+
		"(\25\2\u0101\u0106\7?\2\2\u0102\u0106\7@\2\2\u0103\u0106\7A\2\2\u0104"+
		"\u0106\7B\2\2\u0105\u0100\3\2\2\2\u0105\u0101\3\2\2\2\u0105\u0102\3\2"+
		"\2\2\u0105\u0103\3\2\2\2\u0105\u0104\3\2\2\2\u0106\u0111\3\2\2\2\u0107"+
		"\u0111\7C\2\2\u0108\u0111\5*\26\2\u0109\u010a\t\2\2\2\u010a\u0111\5$\23"+
		"\5\u010b\u010c\7\30\2\2\u010c\u010d\5$\23\2\u010d\u010e\7\31\2\2\u010e"+
		"\u0111\3\2\2\2\u010f\u0111\5\30\r\2\u0110\u00ff\3\2\2\2\u0110\u0107\3"+
		"\2\2\2\u0110\u0108\3\2\2\2\u0110\u0109\3\2\2\2\u0110\u010b\3\2\2\2\u0110"+
		"\u010f\3\2\2\2\u0111\u0126\3\2\2\2\u0112\u0113\f\16\2\2\u0113\u0114\t"+
		"\3\2\2\u0114\u0125\5$\23\17\u0115\u0116\f\r\2\2\u0116\u0117\t\4\2\2\u0117"+
		"\u0125\5$\23\16\u0118\u0119\f\f\2\2\u0119\u011a\t\5\2\2\u011a\u0125\5"+
		"$\23\r\u011b\u011c\f\13\2\2\u011c\u011d\t\6\2\2\u011d\u0125\5$\23\f\u011e"+
		"\u011f\f\n\2\2\u011f\u0120\7\17\2\2\u0120\u0125\5$\23\13\u0121\u0122\f"+
		"\t\2\2\u0122\u0123\7\20\2\2\u0123\u0125\5$\23\n\u0124\u0112\3\2\2\2\u0124"+
		"\u0115\3\2\2\2\u0124\u0118\3\2\2\2\u0124\u011b\3\2\2\2\u0124\u011e\3\2"+
		"\2\2\u0124\u0121\3\2\2\2\u0125\u0128\3\2\2\2\u0126\u0124\3\2\2\2\u0126"+
		"\u0127\3\2\2\2\u0127%\3\2\2\2\u0128\u0126\3\2\2\2\u0129\u012a\t\4\2\2"+
		"\u012a\'\3\2\2\2\u012b\u012d\5&\24\2\u012c\u012b\3\2\2\2\u012c\u012d\3"+
		"\2\2\2\u012d\u012e\3\2\2\2\u012e\u012f\7%\2\2\u012f)\3\2\2\2\u0130\u0135"+
		"\7C\2\2\u0131\u0132\7\32\2\2\u0132\u0133\5$\23\2\u0133\u0134\7\33\2\2"+
		"\u0134\u0136\3\2\2\2\u0135\u0131\3\2\2\2\u0136\u0137\3\2\2\2\u0137\u0135"+
		"\3\2\2\2\u0137\u0138\3\2\2\2\u0138+\3\2\2\2\u0139\u0142\7\32\2\2\u013a"+
		"\u013f\5$\23\2\u013b\u013c\7\36\2\2\u013c\u013e\5$\23\2\u013d\u013b\3"+
		"\2\2\2\u013e\u0141\3\2\2\2\u013f\u013d\3\2\2\2\u013f\u0140\3\2\2\2\u0140"+
		"\u0143\3\2\2\2\u0141\u013f\3\2\2\2\u0142\u013a\3\2\2\2\u0142\u0143\3\2"+
		"\2\2\u0143\u0144\3\2\2\2\u0144\u0145\7\33\2\2\u0145-\3\2\2\2\34\628EO"+
		"Scg\u00a0\u00a7\u00ae\u00c5\u00cc\u00d0\u00d6\u00df\u00e6\u00ed\u00fd"+
		"\u0105\u0110\u0124\u0126\u012c\u0137\u013f\u0142";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}