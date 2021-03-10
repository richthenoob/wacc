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
		NEW=37, CLASS_OBJECT=38, BEGIN=39, END=40, IS=41, READ=42, FREE=43, RETURN=44, 
		EXIT=45, PRINT=46, PRINTLN=47, IF=48, THEN=49, ELSE=50, FI=51, WHILE=52, 
		DONE=53, SKP=54, DO=55, NEWPAIR=56, CALL=57, INT=58, BOOL=59, CHAR=60, 
		STR=61, BOOL_LITER=62, CHAR_LITER=63, STR_LITER=64, PAIR_LITER=65, IDENT=66;
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
			"CLASS_OBJECT", "BEGIN", "END", "IS", "READ", "FREE", "RETURN", "EXIT", 
			"PRINT", "PRINTLN", "IF", "THEN", "ELSE", "FI", "WHILE", "DONE", "SKP", 
			"DO", "NEWPAIR", "CALL", "INT", "BOOL", "CHAR", "STR", "BOOL_LITER", 
			"CHAR_LITER", "STR_LITER", "PAIR_LITER", "IDENT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", "'>='", "'<'", "'<='", 
			"'='", "'=='", "'!='", "'&&'", "'||'", "'!'", "'len'", "'ord'", "'chr'", 
			"'.'", "'''", "'\"'", "'('", "')'", "'['", "']'", "'{'", "'}'", "','", 
			"';'", "'pair'", "'fst'", "'snd'", null, null, null, "'class'", "'new'", 
			null, "'begin'", "'end'", "'is'", "'read'", "'free'", "'return'", "'exit'", 
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
			"CLASS_OBJECT", "BEGIN", "END", "IS", "READ", "FREE", "RETURN", "EXIT", 
			"PRINT", "PRINTLN", "IF", "THEN", "ELSE", "FI", "WHILE", "DONE", "SKP", 
			"DO", "NEWPAIR", "CALL", "INT", "BOOL", "CHAR", "STR", "BOOL_LITER", 
			"CHAR_LITER", "STR_LITER", "PAIR_LITER", "IDENT"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2D\u01ab\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\3\2\3\2\3\3\3\3\3\4\3"+
		"\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3"+
		"\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\21\3"+
		"\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3"+
		"\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3"+
		"\34\3\35\3\35\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3!\3!\3!"+
		"\3!\3\"\3\"\7\"\u00e1\n\"\f\"\16\"\u00e4\13\"\3\"\5\"\u00e7\n\"\3\"\3"+
		"\"\3\"\3\"\3#\6#\u00ee\n#\r#\16#\u00ef\3#\3#\3$\3$\3$\3$\5$\u00f8\n$\3"+
		"%\3%\3%\5%\u00fd\n%\3&\6&\u0100\n&\r&\16&\u0101\3\'\3\'\3\'\3\'\3\'\3"+
		"\'\3(\3(\3(\3(\3)\3)\3)\3)\3*\3*\3*\3*\3*\3*\3+\3+\3+\3+\3,\3,\3,\3-\3"+
		"-\3-\3-\3-\3.\3.\3.\3.\3.\3/\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60"+
		"\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62"+
		"\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\66"+
		"\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\38\38\38\38\38\39\39\39\39\3"+
		"9\3:\3:\3:\3;\3;\3;\3;\3;\3;\3;\3;\3<\3<\3<\3<\3<\3=\3=\3=\3=\3>\3>\3"+
		">\3>\3>\3?\3?\3?\3?\3?\3@\3@\3@\3@\3@\3@\3@\3A\3A\3A\3A\3A\3A\3A\3A\3"+
		"A\5A\u0191\nA\3B\3B\3B\3B\3C\3C\7C\u0199\nC\fC\16C\u019c\13C\3C\3C\3D"+
		"\3D\3D\3D\3D\3E\3E\7E\u01a7\nE\fE\16E\u01aa\13E\2\2F\3\3\5\4\7\5\t\6\13"+
		"\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'"+
		"\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G\2I\2K"+
		"%M&O\'Q(S)U*W+Y,[-]._/a\60c\61e\62g\63i\64k\65m\66o\67q8s9u:w;y<{=}>\177"+
		"?\u0081@\u0083A\u0085B\u0087C\u0089D\3\2\b\4\2\f\f\17\17\5\2\13\f\17\17"+
		"\"\"\b\2\62\62ddhhppttvv\5\2$$))^^\5\2C\\aac|\6\2\62;C\\aac|\2\u01b3\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2"+
		"\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2"+
		"\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2"+
		"\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2K\3\2\2\2"+
		"\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y"+
		"\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2"+
		"\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2"+
		"\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177"+
		"\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2"+
		"\2\2\u0089\3\2\2\2\3\u008b\3\2\2\2\5\u008d\3\2\2\2\7\u008f\3\2\2\2\t\u0091"+
		"\3\2\2\2\13\u0093\3\2\2\2\r\u0095\3\2\2\2\17\u0097\3\2\2\2\21\u009a\3"+
		"\2\2\2\23\u009c\3\2\2\2\25\u009f\3\2\2\2\27\u00a1\3\2\2\2\31\u00a4\3\2"+
		"\2\2\33\u00a7\3\2\2\2\35\u00aa\3\2\2\2\37\u00ad\3\2\2\2!\u00af\3\2\2\2"+
		"#\u00b3\3\2\2\2%\u00b7\3\2\2\2\'\u00bb\3\2\2\2)\u00bd\3\2\2\2+\u00bf\3"+
		"\2\2\2-\u00c1\3\2\2\2/\u00c3\3\2\2\2\61\u00c5\3\2\2\2\63\u00c7\3\2\2\2"+
		"\65\u00c9\3\2\2\2\67\u00cb\3\2\2\29\u00cd\3\2\2\2;\u00cf\3\2\2\2=\u00d1"+
		"\3\2\2\2?\u00d6\3\2\2\2A\u00da\3\2\2\2C\u00de\3\2\2\2E\u00ed\3\2\2\2G"+
		"\u00f7\3\2\2\2I\u00fc\3\2\2\2K\u00ff\3\2\2\2M\u0103\3\2\2\2O\u0109\3\2"+
		"\2\2Q\u010d\3\2\2\2S\u0111\3\2\2\2U\u0117\3\2\2\2W\u011b\3\2\2\2Y\u011e"+
		"\3\2\2\2[\u0123\3\2\2\2]\u0128\3\2\2\2_\u012f\3\2\2\2a\u0134\3\2\2\2c"+
		"\u013a\3\2\2\2e\u0142\3\2\2\2g\u0145\3\2\2\2i\u014a\3\2\2\2k\u014f\3\2"+
		"\2\2m\u0152\3\2\2\2o\u0158\3\2\2\2q\u015d\3\2\2\2s\u0162\3\2\2\2u\u0165"+
		"\3\2\2\2w\u016d\3\2\2\2y\u0172\3\2\2\2{\u0176\3\2\2\2}\u017b\3\2\2\2\177"+
		"\u0180\3\2\2\2\u0081\u0190\3\2\2\2\u0083\u0192\3\2\2\2\u0085\u0196\3\2"+
		"\2\2\u0087\u019f\3\2\2\2\u0089\u01a4\3\2\2\2\u008b\u008c\7-\2\2\u008c"+
		"\4\3\2\2\2\u008d\u008e\7/\2\2\u008e\6\3\2\2\2\u008f\u0090\7,\2\2\u0090"+
		"\b\3\2\2\2\u0091\u0092\7\61\2\2\u0092\n\3\2\2\2\u0093\u0094\7\'\2\2\u0094"+
		"\f\3\2\2\2\u0095\u0096\7@\2\2\u0096\16\3\2\2\2\u0097\u0098\7@\2\2\u0098"+
		"\u0099\7?\2\2\u0099\20\3\2\2\2\u009a\u009b\7>\2\2\u009b\22\3\2\2\2\u009c"+
		"\u009d\7>\2\2\u009d\u009e\7?\2\2\u009e\24\3\2\2\2\u009f\u00a0\7?\2\2\u00a0"+
		"\26\3\2\2\2\u00a1\u00a2\7?\2\2\u00a2\u00a3\7?\2\2\u00a3\30\3\2\2\2\u00a4"+
		"\u00a5\7#\2\2\u00a5\u00a6\7?\2\2\u00a6\32\3\2\2\2\u00a7\u00a8\7(\2\2\u00a8"+
		"\u00a9\7(\2\2\u00a9\34\3\2\2\2\u00aa\u00ab\7~\2\2\u00ab\u00ac\7~\2\2\u00ac"+
		"\36\3\2\2\2\u00ad\u00ae\7#\2\2\u00ae \3\2\2\2\u00af\u00b0\7n\2\2\u00b0"+
		"\u00b1\7g\2\2\u00b1\u00b2\7p\2\2\u00b2\"\3\2\2\2\u00b3\u00b4\7q\2\2\u00b4"+
		"\u00b5\7t\2\2\u00b5\u00b6\7f\2\2\u00b6$\3\2\2\2\u00b7\u00b8\7e\2\2\u00b8"+
		"\u00b9\7j\2\2\u00b9\u00ba\7t\2\2\u00ba&\3\2\2\2\u00bb\u00bc\7\60\2\2\u00bc"+
		"(\3\2\2\2\u00bd\u00be\7)\2\2\u00be*\3\2\2\2\u00bf\u00c0\7$\2\2\u00c0,"+
		"\3\2\2\2\u00c1\u00c2\7*\2\2\u00c2.\3\2\2\2\u00c3\u00c4\7+\2\2\u00c4\60"+
		"\3\2\2\2\u00c5\u00c6\7]\2\2\u00c6\62\3\2\2\2\u00c7\u00c8\7_\2\2\u00c8"+
		"\64\3\2\2\2\u00c9\u00ca\7}\2\2\u00ca\66\3\2\2\2\u00cb\u00cc\7\177\2\2"+
		"\u00cc8\3\2\2\2\u00cd\u00ce\7.\2\2\u00ce:\3\2\2\2\u00cf\u00d0\7=\2\2\u00d0"+
		"<\3\2\2\2\u00d1\u00d2\7r\2\2\u00d2\u00d3\7c\2\2\u00d3\u00d4\7k\2\2\u00d4"+
		"\u00d5\7t\2\2\u00d5>\3\2\2\2\u00d6\u00d7\7h\2\2\u00d7\u00d8\7u\2\2\u00d8"+
		"\u00d9\7v\2\2\u00d9@\3\2\2\2\u00da\u00db\7u\2\2\u00db\u00dc\7p\2\2\u00dc"+
		"\u00dd\7f\2\2\u00ddB\3\2\2\2\u00de\u00e2\7%\2\2\u00df\u00e1\n\2\2\2\u00e0"+
		"\u00df\3\2\2\2\u00e1\u00e4\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e2\u00e3\3\2"+
		"\2\2\u00e3\u00e6\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e5\u00e7\7\17\2\2\u00e6"+
		"\u00e5\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00e9\7\f"+
		"\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00eb\b\"\2\2\u00ebD\3\2\2\2\u00ec\u00ee"+
		"\t\3\2\2\u00ed\u00ec\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef"+
		"\u00f0\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f2\b#\2\2\u00f2F\3\2\2\2\u00f3"+
		"\u00f8\t\4\2\2\u00f4\u00f8\5)\25\2\u00f5\u00f8\5+\26\2\u00f6\u00f8\7^"+
		"\2\2\u00f7\u00f3\3\2\2\2\u00f7\u00f4\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7"+
		"\u00f6\3\2\2\2\u00f8H\3\2\2\2\u00f9\u00fd\n\5\2\2\u00fa\u00fb\7^\2\2\u00fb"+
		"\u00fd\5G$\2\u00fc\u00f9\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fdJ\3\2\2\2\u00fe"+
		"\u0100\4\62;\2\u00ff\u00fe\3\2\2\2\u0100\u0101\3\2\2\2\u0101\u00ff\3\2"+
		"\2\2\u0101\u0102\3\2\2\2\u0102L\3\2\2\2\u0103\u0104\7e\2\2\u0104\u0105"+
		"\7n\2\2\u0105\u0106\7c\2\2\u0106\u0107\7u\2\2\u0107\u0108\7u\2\2\u0108"+
		"N\3\2\2\2\u0109\u010a\7p\2\2\u010a\u010b\7g\2\2\u010b\u010c\7y\2\2\u010c"+
		"P\3\2\2\2\u010d\u010e\5\u0089E\2\u010e\u010f\5\'\24\2\u010f\u0110\5\u0089"+
		"E\2\u0110R\3\2\2\2\u0111\u0112\7d\2\2\u0112\u0113\7g\2\2\u0113\u0114\7"+
		"i\2\2\u0114\u0115\7k\2\2\u0115\u0116\7p\2\2\u0116T\3\2\2\2\u0117\u0118"+
		"\7g\2\2\u0118\u0119\7p\2\2\u0119\u011a\7f\2\2\u011aV\3\2\2\2\u011b\u011c"+
		"\7k\2\2\u011c\u011d\7u\2\2\u011dX\3\2\2\2\u011e\u011f\7t\2\2\u011f\u0120"+
		"\7g\2\2\u0120\u0121\7c\2\2\u0121\u0122\7f\2\2\u0122Z\3\2\2\2\u0123\u0124"+
		"\7h\2\2\u0124\u0125\7t\2\2\u0125\u0126\7g\2\2\u0126\u0127\7g\2\2\u0127"+
		"\\\3\2\2\2\u0128\u0129\7t\2\2\u0129\u012a\7g\2\2\u012a\u012b\7v\2\2\u012b"+
		"\u012c\7w\2\2\u012c\u012d\7t\2\2\u012d\u012e\7p\2\2\u012e^\3\2\2\2\u012f"+
		"\u0130\7g\2\2\u0130\u0131\7z\2\2\u0131\u0132\7k\2\2\u0132\u0133\7v\2\2"+
		"\u0133`\3\2\2\2\u0134\u0135\7r\2\2\u0135\u0136\7t\2\2\u0136\u0137\7k\2"+
		"\2\u0137\u0138\7p\2\2\u0138\u0139\7v\2\2\u0139b\3\2\2\2\u013a\u013b\7"+
		"r\2\2\u013b\u013c\7t\2\2\u013c\u013d\7k\2\2\u013d\u013e\7p\2\2\u013e\u013f"+
		"\7v\2\2\u013f\u0140\7n\2\2\u0140\u0141\7p\2\2\u0141d\3\2\2\2\u0142\u0143"+
		"\7k\2\2\u0143\u0144\7h\2\2\u0144f\3\2\2\2\u0145\u0146\7v\2\2\u0146\u0147"+
		"\7j\2\2\u0147\u0148\7g\2\2\u0148\u0149\7p\2\2\u0149h\3\2\2\2\u014a\u014b"+
		"\7g\2\2\u014b\u014c\7n\2\2\u014c\u014d\7u\2\2\u014d\u014e\7g\2\2\u014e"+
		"j\3\2\2\2\u014f\u0150\7h\2\2\u0150\u0151\7k\2\2\u0151l\3\2\2\2\u0152\u0153"+
		"\7y\2\2\u0153\u0154\7j\2\2\u0154\u0155\7k\2\2\u0155\u0156\7n\2\2\u0156"+
		"\u0157\7g\2\2\u0157n\3\2\2\2\u0158\u0159\7f\2\2\u0159\u015a\7q\2\2\u015a"+
		"\u015b\7p\2\2\u015b\u015c\7g\2\2\u015cp\3\2\2\2\u015d\u015e\7u\2\2\u015e"+
		"\u015f\7m\2\2\u015f\u0160\7k\2\2\u0160\u0161\7r\2\2\u0161r\3\2\2\2\u0162"+
		"\u0163\7f\2\2\u0163\u0164\7q\2\2\u0164t\3\2\2\2\u0165\u0166\7p\2\2\u0166"+
		"\u0167\7g\2\2\u0167\u0168\7y\2\2\u0168\u0169\7r\2\2\u0169\u016a\7c\2\2"+
		"\u016a\u016b\7k\2\2\u016b\u016c\7t\2\2\u016cv\3\2\2\2\u016d\u016e\7e\2"+
		"\2\u016e\u016f\7c\2\2\u016f\u0170\7n\2\2\u0170\u0171\7n\2\2\u0171x\3\2"+
		"\2\2\u0172\u0173\7k\2\2\u0173\u0174\7p\2\2\u0174\u0175\7v\2\2\u0175z\3"+
		"\2\2\2\u0176\u0177\7d\2\2\u0177\u0178\7q\2\2\u0178\u0179\7q\2\2\u0179"+
		"\u017a\7n\2\2\u017a|\3\2\2\2\u017b\u017c\7e\2\2\u017c\u017d\7j\2\2\u017d"+
		"\u017e\7c\2\2\u017e\u017f\7t\2\2\u017f~\3\2\2\2\u0180\u0181\7u\2\2\u0181"+
		"\u0182\7v\2\2\u0182\u0183\7t\2\2\u0183\u0184\7k\2\2\u0184\u0185\7p\2\2"+
		"\u0185\u0186\7i\2\2\u0186\u0080\3\2\2\2\u0187\u0188\7v\2\2\u0188\u0189"+
		"\7t\2\2\u0189\u018a\7w\2\2\u018a\u0191\7g\2\2\u018b\u018c\7h\2\2\u018c"+
		"\u018d\7c\2\2\u018d\u018e\7n\2\2\u018e\u018f\7u\2\2\u018f\u0191\7g\2\2"+
		"\u0190\u0187\3\2\2\2\u0190\u018b\3\2\2\2\u0191\u0082\3\2\2\2\u0192\u0193"+
		"\5)\25\2\u0193\u0194\5I%\2\u0194\u0195\5)\25\2\u0195\u0084\3\2\2\2\u0196"+
		"\u019a\5+\26\2\u0197\u0199\5I%\2\u0198\u0197\3\2\2\2\u0199\u019c\3\2\2"+
		"\2\u019a\u0198\3\2\2\2\u019a\u019b\3\2\2\2\u019b\u019d\3\2\2\2\u019c\u019a"+
		"\3\2\2\2\u019d\u019e\5+\26\2\u019e\u0086\3\2\2\2\u019f\u01a0\7p\2\2\u01a0"+
		"\u01a1\7w\2\2\u01a1\u01a2\7n\2\2\u01a2\u01a3\7n\2\2\u01a3\u0088\3\2\2"+
		"\2\u01a4\u01a8\t\6\2\2\u01a5\u01a7\t\7\2\2\u01a6\u01a5\3\2\2\2\u01a7\u01aa"+
		"\3\2\2\2\u01a8\u01a6\3\2\2\2\u01a8\u01a9\3\2\2\2\u01a9\u008a\3\2\2\2\u01aa"+
		"\u01a8\3\2\2\2\f\2\u00e2\u00e6\u00ef\u00f7\u00fc\u0101\u0190\u019a\u01a8"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}