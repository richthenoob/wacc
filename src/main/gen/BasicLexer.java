// Generated from /Users/zirun/wacc_22/src/main/antlr/BasicLexer.g4 by ANTLR 4.9.1

    package ic.doc.antlr;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BasicLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"PLUS", "MINUS", "MUL", "DIV", "MOD", "GT", "GTE", "LT", "LTE", "ASSIGN", 
			"EQ", "NEQ", "AND", "OR", "NOT", "LEN", "ORD", "CHR", "DOT", "SQUOTE", 
			"DQUOTE", "OPEN_PARENTHESES", "CLOSE_PARENTHESES", "OPEN_BRACKETS", "CLOSE_BRACKETS", 
			"OPEN_CURLY_BRACES", "CLOSE_CURLY_BRACES", "COMMA", "SEMI", "PAIR", "FST", 
			"SND", "COMMENT", "WS", "ESCAPED", "CHARACTER", "INTEGER", "CLASS", "NEW", 
			"BEGIN", "END", "IS", "READ", "FREE", "RETURN", "EXIT", "PRINT", "PRINTLN", 
			"IF", "THEN", "ELSE", "FI", "WHILE", "DONE", "SKP", "DO", "NEWPAIR", 
			"CALL", "INT", "BOOL", "CHAR", "STR", "BOOL_LITER", "CHAR_LITER", "STR_LITER", 
			"PAIR_LITER", "IDENT"
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


	public BasicLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BasicLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2C\u01a5\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\3\2\3\2\3\3\3\3\3\4\3\4\3\5"+
		"\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f"+
		"\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\21"+
		"\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\26"+
		"\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35"+
		"\3\35\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3!\3!\3!\3!\3\"\3"+
		"\"\7\"\u00df\n\"\f\"\16\"\u00e2\13\"\3\"\5\"\u00e5\n\"\3\"\3\"\3\"\3\""+
		"\3#\6#\u00ec\n#\r#\16#\u00ed\3#\3#\3$\3$\3$\3$\5$\u00f6\n$\3%\3%\3%\5"+
		"%\u00fb\n%\3&\6&\u00fe\n&\r&\16&\u00ff\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3"+
		"(\3(\3)\3)\3)\3)\3)\3)\3*\3*\3*\3*\3+\3+\3+\3,\3,\3,\3,\3,\3-\3-\3-\3"+
		"-\3-\3.\3.\3.\3.\3.\3.\3.\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\60"+
		"\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\63\3\63\3\63"+
		"\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\66\3\66\3\66\3\66"+
		"\3\66\3\66\3\67\3\67\3\67\3\67\3\67\38\38\38\38\38\39\39\39\3:\3:\3:\3"+
		":\3:\3:\3:\3:\3;\3;\3;\3;\3;\3<\3<\3<\3<\3=\3=\3=\3=\3=\3>\3>\3>\3>\3"+
		">\3?\3?\3?\3?\3?\3?\3?\3@\3@\3@\3@\3@\3@\3@\3@\3@\5@\u018b\n@\3A\3A\3"+
		"A\3A\3B\3B\7B\u0193\nB\fB\16B\u0196\13B\3B\3B\3C\3C\3C\3C\3C\3D\3D\7D"+
		"\u01a1\nD\fD\16D\u01a4\13D\2\2E\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31"+
		"\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G\2I\2K%M&O\'Q(S)U*W+Y,[-]"+
		"._/a\60c\61e\62g\63i\64k\65m\66o\67q8s9u:w;y<{=}>\177?\u0081@\u0083A\u0085"+
		"B\u0087C\3\2\b\4\2\f\f\17\17\5\2\13\f\17\17\"\"\b\2\62\62ddhhppttvv\5"+
		"\2$$))^^\5\2C\\aac|\6\2\62;C\\aac|\2\u01ad\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2"+
		"\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2"+
		"\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2"+
		"\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2"+
		"\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q"+
		"\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2"+
		"\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2"+
		"\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w"+
		"\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2"+
		"\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\3\u0089\3\2\2\2\5\u008b"+
		"\3\2\2\2\7\u008d\3\2\2\2\t\u008f\3\2\2\2\13\u0091\3\2\2\2\r\u0093\3\2"+
		"\2\2\17\u0095\3\2\2\2\21\u0098\3\2\2\2\23\u009a\3\2\2\2\25\u009d\3\2\2"+
		"\2\27\u009f\3\2\2\2\31\u00a2\3\2\2\2\33\u00a5\3\2\2\2\35\u00a8\3\2\2\2"+
		"\37\u00ab\3\2\2\2!\u00ad\3\2\2\2#\u00b1\3\2\2\2%\u00b5\3\2\2\2\'\u00b9"+
		"\3\2\2\2)\u00bb\3\2\2\2+\u00bd\3\2\2\2-\u00bf\3\2\2\2/\u00c1\3\2\2\2\61"+
		"\u00c3\3\2\2\2\63\u00c5\3\2\2\2\65\u00c7\3\2\2\2\67\u00c9\3\2\2\29\u00cb"+
		"\3\2\2\2;\u00cd\3\2\2\2=\u00cf\3\2\2\2?\u00d4\3\2\2\2A\u00d8\3\2\2\2C"+
		"\u00dc\3\2\2\2E\u00eb\3\2\2\2G\u00f5\3\2\2\2I\u00fa\3\2\2\2K\u00fd\3\2"+
		"\2\2M\u0101\3\2\2\2O\u0107\3\2\2\2Q\u010b\3\2\2\2S\u0111\3\2\2\2U\u0115"+
		"\3\2\2\2W\u0118\3\2\2\2Y\u011d\3\2\2\2[\u0122\3\2\2\2]\u0129\3\2\2\2_"+
		"\u012e\3\2\2\2a\u0134\3\2\2\2c\u013c\3\2\2\2e\u013f\3\2\2\2g\u0144\3\2"+
		"\2\2i\u0149\3\2\2\2k\u014c\3\2\2\2m\u0152\3\2\2\2o\u0157\3\2\2\2q\u015c"+
		"\3\2\2\2s\u015f\3\2\2\2u\u0167\3\2\2\2w\u016c\3\2\2\2y\u0170\3\2\2\2{"+
		"\u0175\3\2\2\2}\u017a\3\2\2\2\177\u018a\3\2\2\2\u0081\u018c\3\2\2\2\u0083"+
		"\u0190\3\2\2\2\u0085\u0199\3\2\2\2\u0087\u019e\3\2\2\2\u0089\u008a\7-"+
		"\2\2\u008a\4\3\2\2\2\u008b\u008c\7/\2\2\u008c\6\3\2\2\2\u008d\u008e\7"+
		",\2\2\u008e\b\3\2\2\2\u008f\u0090\7\61\2\2\u0090\n\3\2\2\2\u0091\u0092"+
		"\7\'\2\2\u0092\f\3\2\2\2\u0093\u0094\7@\2\2\u0094\16\3\2\2\2\u0095\u0096"+
		"\7@\2\2\u0096\u0097\7?\2\2\u0097\20\3\2\2\2\u0098\u0099\7>\2\2\u0099\22"+
		"\3\2\2\2\u009a\u009b\7>\2\2\u009b\u009c\7?\2\2\u009c\24\3\2\2\2\u009d"+
		"\u009e\7?\2\2\u009e\26\3\2\2\2\u009f\u00a0\7?\2\2\u00a0\u00a1\7?\2\2\u00a1"+
		"\30\3\2\2\2\u00a2\u00a3\7#\2\2\u00a3\u00a4\7?\2\2\u00a4\32\3\2\2\2\u00a5"+
		"\u00a6\7(\2\2\u00a6\u00a7\7(\2\2\u00a7\34\3\2\2\2\u00a8\u00a9\7~\2\2\u00a9"+
		"\u00aa\7~\2\2\u00aa\36\3\2\2\2\u00ab\u00ac\7#\2\2\u00ac \3\2\2\2\u00ad"+
		"\u00ae\7n\2\2\u00ae\u00af\7g\2\2\u00af\u00b0\7p\2\2\u00b0\"\3\2\2\2\u00b1"+
		"\u00b2\7q\2\2\u00b2\u00b3\7t\2\2\u00b3\u00b4\7f\2\2\u00b4$\3\2\2\2\u00b5"+
		"\u00b6\7e\2\2\u00b6\u00b7\7j\2\2\u00b7\u00b8\7t\2\2\u00b8&\3\2\2\2\u00b9"+
		"\u00ba\7\60\2\2\u00ba(\3\2\2\2\u00bb\u00bc\7)\2\2\u00bc*\3\2\2\2\u00bd"+
		"\u00be\7$\2\2\u00be,\3\2\2\2\u00bf\u00c0\7*\2\2\u00c0.\3\2\2\2\u00c1\u00c2"+
		"\7+\2\2\u00c2\60\3\2\2\2\u00c3\u00c4\7]\2\2\u00c4\62\3\2\2\2\u00c5\u00c6"+
		"\7_\2\2\u00c6\64\3\2\2\2\u00c7\u00c8\7}\2\2\u00c8\66\3\2\2\2\u00c9\u00ca"+
		"\7\177\2\2\u00ca8\3\2\2\2\u00cb\u00cc\7.\2\2\u00cc:\3\2\2\2\u00cd\u00ce"+
		"\7=\2\2\u00ce<\3\2\2\2\u00cf\u00d0\7r\2\2\u00d0\u00d1\7c\2\2\u00d1\u00d2"+
		"\7k\2\2\u00d2\u00d3\7t\2\2\u00d3>\3\2\2\2\u00d4\u00d5\7h\2\2\u00d5\u00d6"+
		"\7u\2\2\u00d6\u00d7\7v\2\2\u00d7@\3\2\2\2\u00d8\u00d9\7u\2\2\u00d9\u00da"+
		"\7p\2\2\u00da\u00db\7f\2\2\u00dbB\3\2\2\2\u00dc\u00e0\7%\2\2\u00dd\u00df"+
		"\n\2\2\2\u00de\u00dd\3\2\2\2\u00df\u00e2\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0"+
		"\u00e1\3\2\2\2\u00e1\u00e4\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e3\u00e5\7\17"+
		"\2\2\u00e4\u00e3\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6"+
		"\u00e7\7\f\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00e9\b\"\2\2\u00e9D\3\2\2\2"+
		"\u00ea\u00ec\t\3\2\2\u00eb\u00ea\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00eb"+
		"\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0\b#\2\2\u00f0"+
		"F\3\2\2\2\u00f1\u00f6\t\4\2\2\u00f2\u00f6\5)\25\2\u00f3\u00f6\5+\26\2"+
		"\u00f4\u00f6\7^\2\2\u00f5\u00f1\3\2\2\2\u00f5\u00f2\3\2\2\2\u00f5\u00f3"+
		"\3\2\2\2\u00f5\u00f4\3\2\2\2\u00f6H\3\2\2\2\u00f7\u00fb\n\5\2\2\u00f8"+
		"\u00f9\7^\2\2\u00f9\u00fb\5G$\2\u00fa\u00f7\3\2\2\2\u00fa\u00f8\3\2\2"+
		"\2\u00fbJ\3\2\2\2\u00fc\u00fe\4\62;\2\u00fd\u00fc\3\2\2\2\u00fe\u00ff"+
		"\3\2\2\2\u00ff\u00fd\3\2\2\2\u00ff\u0100\3\2\2\2\u0100L\3\2\2\2\u0101"+
		"\u0102\7e\2\2\u0102\u0103\7n\2\2\u0103\u0104\7c\2\2\u0104\u0105\7u\2\2"+
		"\u0105\u0106\7u\2\2\u0106N\3\2\2\2\u0107\u0108\7p\2\2\u0108\u0109\7g\2"+
		"\2\u0109\u010a\7y\2\2\u010aP\3\2\2\2\u010b\u010c\7d\2\2\u010c\u010d\7"+
		"g\2\2\u010d\u010e\7i\2\2\u010e\u010f\7k\2\2\u010f\u0110\7p\2\2\u0110R"+
		"\3\2\2\2\u0111\u0112\7g\2\2\u0112\u0113\7p\2\2\u0113\u0114\7f\2\2\u0114"+
		"T\3\2\2\2\u0115\u0116\7k\2\2\u0116\u0117\7u\2\2\u0117V\3\2\2\2\u0118\u0119"+
		"\7t\2\2\u0119\u011a\7g\2\2\u011a\u011b\7c\2\2\u011b\u011c\7f\2\2\u011c"+
		"X\3\2\2\2\u011d\u011e\7h\2\2\u011e\u011f\7t\2\2\u011f\u0120\7g\2\2\u0120"+
		"\u0121\7g\2\2\u0121Z\3\2\2\2\u0122\u0123\7t\2\2\u0123\u0124\7g\2\2\u0124"+
		"\u0125\7v\2\2\u0125\u0126\7w\2\2\u0126\u0127\7t\2\2\u0127\u0128\7p\2\2"+
		"\u0128\\\3\2\2\2\u0129\u012a\7g\2\2\u012a\u012b\7z\2\2\u012b\u012c\7k"+
		"\2\2\u012c\u012d\7v\2\2\u012d^\3\2\2\2\u012e\u012f\7r\2\2\u012f\u0130"+
		"\7t\2\2\u0130\u0131\7k\2\2\u0131\u0132\7p\2\2\u0132\u0133\7v\2\2\u0133"+
		"`\3\2\2\2\u0134\u0135\7r\2\2\u0135\u0136\7t\2\2\u0136\u0137\7k\2\2\u0137"+
		"\u0138\7p\2\2\u0138\u0139\7v\2\2\u0139\u013a\7n\2\2\u013a\u013b\7p\2\2"+
		"\u013bb\3\2\2\2\u013c\u013d\7k\2\2\u013d\u013e\7h\2\2\u013ed\3\2\2\2\u013f"+
		"\u0140\7v\2\2\u0140\u0141\7j\2\2\u0141\u0142\7g\2\2\u0142\u0143\7p\2\2"+
		"\u0143f\3\2\2\2\u0144\u0145\7g\2\2\u0145\u0146\7n\2\2\u0146\u0147\7u\2"+
		"\2\u0147\u0148\7g\2\2\u0148h\3\2\2\2\u0149\u014a\7h\2\2\u014a\u014b\7"+
		"k\2\2\u014bj\3\2\2\2\u014c\u014d\7y\2\2\u014d\u014e\7j\2\2\u014e\u014f"+
		"\7k\2\2\u014f\u0150\7n\2\2\u0150\u0151\7g\2\2\u0151l\3\2\2\2\u0152\u0153"+
		"\7f\2\2\u0153\u0154\7q\2\2\u0154\u0155\7p\2\2\u0155\u0156\7g\2\2\u0156"+
		"n\3\2\2\2\u0157\u0158\7u\2\2\u0158\u0159\7m\2\2\u0159\u015a\7k\2\2\u015a"+
		"\u015b\7r\2\2\u015bp\3\2\2\2\u015c\u015d\7f\2\2\u015d\u015e\7q\2\2\u015e"+
		"r\3\2\2\2\u015f\u0160\7p\2\2\u0160\u0161\7g\2\2\u0161\u0162\7y\2\2\u0162"+
		"\u0163\7r\2\2\u0163\u0164\7c\2\2\u0164\u0165\7k\2\2\u0165\u0166\7t\2\2"+
		"\u0166t\3\2\2\2\u0167\u0168\7e\2\2\u0168\u0169\7c\2\2\u0169\u016a\7n\2"+
		"\2\u016a\u016b\7n\2\2\u016bv\3\2\2\2\u016c\u016d\7k\2\2\u016d\u016e\7"+
		"p\2\2\u016e\u016f\7v\2\2\u016fx\3\2\2\2\u0170\u0171\7d\2\2\u0171\u0172"+
		"\7q\2\2\u0172\u0173\7q\2\2\u0173\u0174\7n\2\2\u0174z\3\2\2\2\u0175\u0176"+
		"\7e\2\2\u0176\u0177\7j\2\2\u0177\u0178\7c\2\2\u0178\u0179\7t\2\2\u0179"+
		"|\3\2\2\2\u017a\u017b\7u\2\2\u017b\u017c\7v\2\2\u017c\u017d\7t\2\2\u017d"+
		"\u017e\7k\2\2\u017e\u017f\7p\2\2\u017f\u0180\7i\2\2\u0180~\3\2\2\2\u0181"+
		"\u0182\7v\2\2\u0182\u0183\7t\2\2\u0183\u0184\7w\2\2\u0184\u018b\7g\2\2"+
		"\u0185\u0186\7h\2\2\u0186\u0187\7c\2\2\u0187\u0188\7n\2\2\u0188\u0189"+
		"\7u\2\2\u0189\u018b\7g\2\2\u018a\u0181\3\2\2\2\u018a\u0185\3\2\2\2\u018b"+
		"\u0080\3\2\2\2\u018c\u018d\5)\25\2\u018d\u018e\5I%\2\u018e\u018f\5)\25"+
		"\2\u018f\u0082\3\2\2\2\u0190\u0194\5+\26\2\u0191\u0193\5I%\2\u0192\u0191"+
		"\3\2\2\2\u0193\u0196\3\2\2\2\u0194\u0192\3\2\2\2\u0194\u0195\3\2\2\2\u0195"+
		"\u0197\3\2\2\2\u0196\u0194\3\2\2\2\u0197\u0198\5+\26\2\u0198\u0084\3\2"+
		"\2\2\u0199\u019a\7p\2\2\u019a\u019b\7w\2\2\u019b\u019c\7n\2\2\u019c\u019d"+
		"\7n\2\2\u019d\u0086\3\2\2\2\u019e\u01a2\t\6\2\2\u019f\u01a1\t\7\2\2\u01a0"+
		"\u019f\3\2\2\2\u01a1\u01a4\3\2\2\2\u01a2\u01a0\3\2\2\2\u01a2\u01a3\3\2"+
		"\2\2\u01a3\u0088\3\2\2\2\u01a4\u01a2\3\2\2\2\f\2\u00e0\u00e4\u00ed\u00f5"+
		"\u00fa\u00ff\u018a\u0194\u01a2\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}