package com.system.distribute.sqlparser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BaseQueryLexer extends Lexer {
	

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__2=1, T__1=2, T__0=3, INSERT=4, SELECT=5, UPDATE=6, DELETE=7, VALUES=8, 
		SYNC=9, AS=10, WHERE=11, SET=12, FROM=13, INTO=14, COMP=15, ID=16, WS=17;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'"
	};
	public static final String[] ruleNames = {
		"T__2", "T__1", "T__0", "INSERT", "SELECT", "UPDATE", "DELETE", "VALUES", 
		"SYNC", "AS", "WHERE", "SET", "FROM", "INTO", "COMP", "ID", "WS"
	};


	public BaseQueryLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BaseQuery.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\23\u00be\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\5\58\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6"+
		"F\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7T\n\7\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\bb\n\b\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\tp\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\5\nz\n\n\3\13\3\13\3\13\3\13\5\13\u0080\n\13\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\5\f\u008c\n\f\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0094\n\r\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u009e\n\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\5\17\u00a8\n\17\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\5\20\u00b1\n\20\3\21\6\21\u00b4\n\21\r\21\16\21\u00b5\3\22\6\22"+
		"\u00b9\n\22\r\22\16\22\u00ba\3\22\3\22\2\2\23\3\3\5\4\7\5\t\6\13\7\r\b"+
		"\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23\3\2\4\t\2%"+
		"%,,\60<C\\^^aac|\5\2\13\f\17\17\"\"\u00ce\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2"+
		"\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\3%\3\2\2\2\5\'\3\2\2\2"+
		"\7)\3\2\2\2\t\67\3\2\2\2\13E\3\2\2\2\rS\3\2\2\2\17a\3\2\2\2\21o\3\2\2"+
		"\2\23y\3\2\2\2\25\177\3\2\2\2\27\u008b\3\2\2\2\31\u0093\3\2\2\2\33\u009d"+
		"\3\2\2\2\35\u00a7\3\2\2\2\37\u00b0\3\2\2\2!\u00b3\3\2\2\2#\u00b8\3\2\2"+
		"\2%&\7+\2\2&\4\3\2\2\2\'(\7.\2\2(\6\3\2\2\2)*\7*\2\2*\b\3\2\2\2+,\7k\2"+
		"\2,-\7p\2\2-.\7u\2\2./\7g\2\2/\60\7t\2\2\608\7v\2\2\61\62\7K\2\2\62\63"+
		"\7P\2\2\63\64\7U\2\2\64\65\7G\2\2\65\66\7T\2\2\668\7V\2\2\67+\3\2\2\2"+
		"\67\61\3\2\2\28\n\3\2\2\29:\7u\2\2:;\7g\2\2;<\7n\2\2<=\7g\2\2=>\7e\2\2"+
		">F\7v\2\2?@\7U\2\2@A\7G\2\2AB\7N\2\2BC\7G\2\2CD\7E\2\2DF\7V\2\2E9\3\2"+
		"\2\2E?\3\2\2\2F\f\3\2\2\2GH\7w\2\2HI\7r\2\2IJ\7f\2\2JK\7c\2\2KL\7v\2\2"+
		"LT\7g\2\2MN\7W\2\2NO\7R\2\2OP\7F\2\2PQ\7C\2\2QR\7V\2\2RT\7G\2\2SG\3\2"+
		"\2\2SM\3\2\2\2T\16\3\2\2\2UV\7f\2\2VW\7g\2\2WX\7n\2\2XY\7g\2\2YZ\7v\2"+
		"\2Zb\7g\2\2[\\\7F\2\2\\]\7G\2\2]^\7N\2\2^_\7G\2\2_`\7V\2\2`b\7G\2\2aU"+
		"\3\2\2\2a[\3\2\2\2b\20\3\2\2\2cd\7x\2\2de\7c\2\2ef\7n\2\2fg\7w\2\2gh\7"+
		"g\2\2hp\7u\2\2ij\7X\2\2jk\7C\2\2kl\7N\2\2lm\7W\2\2mn\7G\2\2np\7U\2\2o"+
		"c\3\2\2\2oi\3\2\2\2p\22\3\2\2\2qr\7u\2\2rs\7{\2\2st\7p\2\2tz\7e\2\2uv"+
		"\7U\2\2vw\7[\2\2wx\7P\2\2xz\7E\2\2yq\3\2\2\2yu\3\2\2\2z\24\3\2\2\2{|\7"+
		"c\2\2|\u0080\7u\2\2}~\7C\2\2~\u0080\7U\2\2\177{\3\2\2\2\177}\3\2\2\2\u0080"+
		"\26\3\2\2\2\u0081\u0082\7y\2\2\u0082\u0083\7j\2\2\u0083\u0084\7g\2\2\u0084"+
		"\u0085\7t\2\2\u0085\u008c\7g\2\2\u0086\u0087\7Y\2\2\u0087\u0088\7J\2\2"+
		"\u0088\u0089\7G\2\2\u0089\u008a\7T\2\2\u008a\u008c\7G\2\2\u008b\u0081"+
		"\3\2\2\2\u008b\u0086\3\2\2\2\u008c\30\3\2\2\2\u008d\u008e\7u\2\2\u008e"+
		"\u008f\7g\2\2\u008f\u0094\7v\2\2\u0090\u0091\7U\2\2\u0091\u0092\7G\2\2"+
		"\u0092\u0094\7V\2\2\u0093\u008d\3\2\2\2\u0093\u0090\3\2\2\2\u0094\32\3"+
		"\2\2\2\u0095\u0096\7h\2\2\u0096\u0097\7t\2\2\u0097\u0098\7q\2\2\u0098"+
		"\u009e\7o\2\2\u0099\u009a\7H\2\2\u009a\u009b\7T\2\2\u009b\u009c\7Q\2\2"+
		"\u009c\u009e\7O\2\2\u009d\u0095\3\2\2\2\u009d\u0099\3\2\2\2\u009e\34\3"+
		"\2\2\2\u009f\u00a0\7k\2\2\u00a0\u00a1\7p\2\2\u00a1\u00a2\7v\2\2\u00a2"+
		"\u00a8\7q\2\2\u00a3\u00a4\7K\2\2\u00a4\u00a5\7P\2\2\u00a5\u00a6\7V\2\2"+
		"\u00a6\u00a8\7Q\2\2\u00a7\u009f\3\2\2\2\u00a7\u00a3\3\2\2\2\u00a8\36\3"+
		"\2\2\2\u00a9\u00b1\7>\2\2\u00aa\u00ab\7>\2\2\u00ab\u00b1\7?\2\2\u00ac"+
		"\u00b1\7?\2\2\u00ad\u00ae\7@\2\2\u00ae\u00b1\7?\2\2\u00af\u00b1\7@\2\2"+
		"\u00b0\u00a9\3\2\2\2\u00b0\u00aa\3\2\2\2\u00b0\u00ac\3\2\2\2\u00b0\u00ad"+
		"\3\2\2\2\u00b0\u00af\3\2\2\2\u00b1 \3\2\2\2\u00b2\u00b4\t\2\2\2\u00b3"+
		"\u00b2\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b5\u00b6\3\2"+
		"\2\2\u00b6\"\3\2\2\2\u00b7\u00b9\t\3\2\2\u00b8\u00b7\3\2\2\2\u00b9\u00ba"+
		"\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc"+
		"\u00bd\b\22\2\2\u00bd$\3\2\2\2\21\2\67ESaoy\177\u008b\u0093\u009d\u00a7"+
		"\u00b0\u00b5\u00ba\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}