package com.system.distribute.sqlparser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BaseQueryParser extends Parser {
	

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__2=1, T__1=2, T__0=3, INSERT=4, SELECT=5, UPDATE=6, DELETE=7, VALUES=8, 
		SYNC=9, AS=10, WHERE=11, SET=12, FROM=13, INTO=14, COMP=15, ID=16, WS=17;
	public static final String[] tokenNames = {
		"<INVALID>", "')'", "','", "'('", "INSERT", "SELECT", "UPDATE", "DELETE", 
		"VALUES", "SYNC", "AS", "WHERE", "SET", "FROM", "INTO", "COMP", "ID", 
		"WS"
	};
	public static final int
		RULE_sql = 0, RULE_insert_statement = 1, RULE_select_statement = 2, RULE_update_statement = 3, 
		RULE_delete_statement = 4, RULE_sync_statement = 5, RULE_expr_lst = 6, 
		RULE_from_cause = 7, RULE_into_cause = 8, RULE_values_cause = 9, RULE_where_cause = 10, 
		RULE_set_cause = 11, RULE_expr = 12, RULE_kinsert = 13, RULE_kselect = 14, 
		RULE_kupdate = 15, RULE_kdelete = 16, RULE_ksync = 17, RULE_kas = 18, 
		RULE_lp = 19, RULE_rp = 20, RULE_comma = 21, RULE_comp = 22, RULE_id = 23;
	public static final String[] ruleNames = {
		"sql", "insert_statement", "select_statement", "update_statement", "delete_statement", 
		"sync_statement", "expr_lst", "from_cause", "into_cause", "values_cause", 
		"where_cause", "set_cause", "expr", "kinsert", "kselect", "kupdate", "kdelete", 
		"ksync", "kas", "lp", "rp", "comma", "comp", "id"
	};

	@Override
	public String getGrammarFileName() { return "BaseQuery.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public BaseQueryParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class SqlContext extends ParserRuleContext {
		public Update_statementContext update_statement() {
			return getRuleContext(Update_statementContext.class,0);
		}
		public Insert_statementContext insert_statement() {
			return getRuleContext(Insert_statementContext.class,0);
		}
		public Select_statementContext select_statement() {
			return getRuleContext(Select_statementContext.class,0);
		}
		public Sync_statementContext sync_statement() {
			return getRuleContext(Sync_statementContext.class,0);
		}
		public Delete_statementContext delete_statement() {
			return getRuleContext(Delete_statementContext.class,0);
		}
		public SqlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sql; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterSql(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitSql(this);
		}
	}

	public final SqlContext sql() throws RecognitionException {
		SqlContext _localctx = new SqlContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_sql);
		try {
			setState(53);
			switch (_input.LA(1)) {
			case INSERT:
				enterOuterAlt(_localctx, 1);
				{
				setState(48); insert_statement();
				}
				break;
			case SELECT:
				enterOuterAlt(_localctx, 2);
				{
				setState(49); select_statement();
				}
				break;
			case UPDATE:
				enterOuterAlt(_localctx, 3);
				{
				setState(50); update_statement();
				}
				break;
			case DELETE:
				enterOuterAlt(_localctx, 4);
				{
				setState(51); delete_statement();
				}
				break;
			case SYNC:
				enterOuterAlt(_localctx, 5);
				{
				setState(52); sync_statement();
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

	public static class Insert_statementContext extends ParserRuleContext {
		public Values_causeContext values_cause() {
			return getRuleContext(Values_causeContext.class,0);
		}
		public Into_causeContext into_cause() {
			return getRuleContext(Into_causeContext.class,0);
		}
		public KinsertContext kinsert() {
			return getRuleContext(KinsertContext.class,0);
		}
		public Insert_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insert_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterInsert_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitInsert_statement(this);
		}
	}

	public final Insert_statementContext insert_statement() throws RecognitionException {
		Insert_statementContext _localctx = new Insert_statementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_insert_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55); kinsert();
			setState(56); into_cause();
			setState(57); values_cause();
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

	public static class Select_statementContext extends ParserRuleContext {
		public From_causeContext from_cause(int i) {
			return getRuleContext(From_causeContext.class,i);
		}
		public List<From_causeContext> from_cause() {
			return getRuleContexts(From_causeContext.class);
		}
		public Expr_lstContext expr_lst() {
			return getRuleContext(Expr_lstContext.class,0);
		}
		public Where_causeContext where_cause(int i) {
			return getRuleContext(Where_causeContext.class,i);
		}
		public List<Where_causeContext> where_cause() {
			return getRuleContexts(Where_causeContext.class);
		}
		public KselectContext kselect() {
			return getRuleContext(KselectContext.class,0);
		}
		public Select_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterSelect_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitSelect_statement(this);
		}
	}

	public final Select_statementContext select_statement() throws RecognitionException {
		Select_statementContext _localctx = new Select_statementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_select_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59); kselect();
			setState(60); expr_lst();
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FROM) {
				{
				{
				setState(61); from_cause();
				}
				}
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(70);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WHERE) {
				{
				{
				setState(67); where_cause();
				}
				}
				setState(72);
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

	public static class Update_statementContext extends ParserRuleContext {
		public Set_causeContext set_cause() {
			return getRuleContext(Set_causeContext.class,0);
		}
		public Expr_lstContext expr_lst() {
			return getRuleContext(Expr_lstContext.class,0);
		}
		public Where_causeContext where_cause(int i) {
			return getRuleContext(Where_causeContext.class,i);
		}
		public List<Where_causeContext> where_cause() {
			return getRuleContexts(Where_causeContext.class);
		}
		public KupdateContext kupdate() {
			return getRuleContext(KupdateContext.class,0);
		}
		public Update_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_update_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterUpdate_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitUpdate_statement(this);
		}
	}

	public final Update_statementContext update_statement() throws RecognitionException {
		Update_statementContext _localctx = new Update_statementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_update_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73); kupdate();
			setState(74); expr_lst();
			setState(75); set_cause();
			setState(79);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WHERE) {
				{
				{
				setState(76); where_cause();
				}
				}
				setState(81);
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

	public static class Delete_statementContext extends ParserRuleContext {
		public From_causeContext from_cause() {
			return getRuleContext(From_causeContext.class,0);
		}
		public KdeleteContext kdelete() {
			return getRuleContext(KdeleteContext.class,0);
		}
		public Where_causeContext where_cause(int i) {
			return getRuleContext(Where_causeContext.class,i);
		}
		public List<Where_causeContext> where_cause() {
			return getRuleContexts(Where_causeContext.class);
		}
		public Delete_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delete_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterDelete_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitDelete_statement(this);
		}
	}

	public final Delete_statementContext delete_statement() throws RecognitionException {
		Delete_statementContext _localctx = new Delete_statementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_delete_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82); kdelete();
			setState(83); from_cause();
			setState(87);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WHERE) {
				{
				{
				setState(84); where_cause();
				}
				}
				setState(89);
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

	public static class Sync_statementContext extends ParserRuleContext {
		public From_causeContext from_cause() {
			return getRuleContext(From_causeContext.class,0);
		}
		public Where_causeContext where_cause(int i) {
			return getRuleContext(Where_causeContext.class,i);
		}
		public List<Where_causeContext> where_cause() {
			return getRuleContexts(Where_causeContext.class);
		}
		public KsyncContext ksync() {
			return getRuleContext(KsyncContext.class,0);
		}
		public Sync_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sync_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterSync_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitSync_statement(this);
		}
	}

	public final Sync_statementContext sync_statement() throws RecognitionException {
		Sync_statementContext _localctx = new Sync_statementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_sync_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90); ksync();
			setState(91); from_cause();
			setState(95);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WHERE) {
				{
				{
				setState(92); where_cause();
				}
				}
				setState(97);
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

	public static class Expr_lstContext extends ParserRuleContext {
		public List<CommaContext> comma() {
			return getRuleContexts(CommaContext.class);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public CommaContext comma(int i) {
			return getRuleContext(CommaContext.class,i);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public Expr_lstContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_lst; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterExpr_lst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitExpr_lst(this);
		}
	}

	public final Expr_lstContext expr_lst() throws RecognitionException {
		Expr_lstContext _localctx = new Expr_lstContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_expr_lst);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98); expr(0);
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(99); comma();
				setState(100); expr(0);
				}
				}
				setState(106);
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

	public static class From_causeContext extends ParserRuleContext {
		public Expr_lstContext expr_lst() {
			return getRuleContext(Expr_lstContext.class,0);
		}
		public TerminalNode FROM() { return getToken(BaseQueryParser.FROM, 0); }
		public From_causeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_from_cause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterFrom_cause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitFrom_cause(this);
		}
	}

	public final From_causeContext from_cause() throws RecognitionException {
		From_causeContext _localctx = new From_causeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_from_cause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107); match(FROM);
			setState(108); expr_lst();
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

	public static class Into_causeContext extends ParserRuleContext {
		public CommaContext comma() {
			return getRuleContext(CommaContext.class,0);
		}
		public LpContext lp() {
			return getRuleContext(LpContext.class,0);
		}
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public TerminalNode INTO() { return getToken(BaseQueryParser.INTO, 0); }
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public Into_causeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_into_cause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterInto_cause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitInto_cause(this);
		}
	}

	public final Into_causeContext into_cause() throws RecognitionException {
		Into_causeContext _localctx = new Into_causeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_into_cause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110); match(INTO);
			setState(111); id();
			setState(119);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(112); lp();
				setState(113); id();
				{
				setState(114); comma();
				setState(115); id();
				}
				setState(117); rp();
				}
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

	public static class Values_causeContext extends ParserRuleContext {
		public Expr_lstContext expr_lst() {
			return getRuleContext(Expr_lstContext.class,0);
		}
		public TerminalNode VALUES() { return getToken(BaseQueryParser.VALUES, 0); }
		public Values_causeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_values_cause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterValues_cause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitValues_cause(this);
		}
	}

	public final Values_causeContext values_cause() throws RecognitionException {
		Values_causeContext _localctx = new Values_causeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_values_cause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121); match(VALUES);
			setState(122); expr_lst();
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

	public static class Where_causeContext extends ParserRuleContext {
		public TerminalNode WHERE() { return getToken(BaseQueryParser.WHERE, 0); }
		public Expr_lstContext expr_lst() {
			return getRuleContext(Expr_lstContext.class,0);
		}
		public Where_causeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_where_cause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterWhere_cause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitWhere_cause(this);
		}
	}

	public final Where_causeContext where_cause() throws RecognitionException {
		Where_causeContext _localctx = new Where_causeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_where_cause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124); match(WHERE);
			setState(125); expr_lst();
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

	public static class Set_causeContext extends ParserRuleContext {
		public TerminalNode SET() { return getToken(BaseQueryParser.SET, 0); }
		public Expr_lstContext expr_lst() {
			return getRuleContext(Expr_lstContext.class,0);
		}
		public Set_causeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_set_cause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterSet_cause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitSet_cause(this);
		}
	}

	public final Set_causeContext set_cause() throws RecognitionException {
		Set_causeContext _localctx = new Set_causeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_set_cause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127); match(SET);
			setState(128); expr_lst();
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
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public LpContext lp() {
			return getRuleContext(LpContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public CompContext comp() {
			return getRuleContext(CompContext.class,0);
		}
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public KasContext kas() {
			return getRuleContext(KasContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitExpr(this);
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
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			switch (_input.LA(1)) {
			case T__0:
				{
				setState(131); lp();
				setState(132); id();
				setState(133); rp();
				}
				break;
			case ID:
				{
				setState(135); id();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(148);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(146);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(138);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(139); comp();
						setState(140); expr(5);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(142);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(143); kas();
						setState(144); expr(3);
						}
						break;
					}
					} 
				}
				setState(150);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
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

	public static class KinsertContext extends ParserRuleContext {
		public TerminalNode INSERT() { return getToken(BaseQueryParser.INSERT, 0); }
		public KinsertContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_kinsert; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterKinsert(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitKinsert(this);
		}
	}

	public final KinsertContext kinsert() throws RecognitionException {
		KinsertContext _localctx = new KinsertContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_kinsert);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151); match(INSERT);
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

	public static class KselectContext extends ParserRuleContext {
		public TerminalNode SELECT() { return getToken(BaseQueryParser.SELECT, 0); }
		public KselectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_kselect; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterKselect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitKselect(this);
		}
	}

	public final KselectContext kselect() throws RecognitionException {
		KselectContext _localctx = new KselectContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_kselect);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153); match(SELECT);
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

	public static class KupdateContext extends ParserRuleContext {
		public TerminalNode UPDATE() { return getToken(BaseQueryParser.UPDATE, 0); }
		public KupdateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_kupdate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterKupdate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitKupdate(this);
		}
	}

	public final KupdateContext kupdate() throws RecognitionException {
		KupdateContext _localctx = new KupdateContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_kupdate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155); match(UPDATE);
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

	public static class KdeleteContext extends ParserRuleContext {
		public TerminalNode DELETE() { return getToken(BaseQueryParser.DELETE, 0); }
		public KdeleteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_kdelete; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterKdelete(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitKdelete(this);
		}
	}

	public final KdeleteContext kdelete() throws RecognitionException {
		KdeleteContext _localctx = new KdeleteContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_kdelete);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(157); match(DELETE);
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

	public static class KsyncContext extends ParserRuleContext {
		public TerminalNode SYNC() { return getToken(BaseQueryParser.SYNC, 0); }
		public KsyncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ksync; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterKsync(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitKsync(this);
		}
	}

	public final KsyncContext ksync() throws RecognitionException {
		KsyncContext _localctx = new KsyncContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_ksync);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159); match(SYNC);
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

	public static class KasContext extends ParserRuleContext {
		public TerminalNode AS() { return getToken(BaseQueryParser.AS, 0); }
		public KasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_kas; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterKas(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitKas(this);
		}
	}

	public final KasContext kas() throws RecognitionException {
		KasContext _localctx = new KasContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_kas);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161); match(AS);
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

	public static class LpContext extends ParserRuleContext {
		public LpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterLp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitLp(this);
		}
	}

	public final LpContext lp() throws RecognitionException {
		LpContext _localctx = new LpContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_lp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163); match(T__0);
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

	public static class RpContext extends ParserRuleContext {
		public RpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterRp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitRp(this);
		}
	}

	public final RpContext rp() throws RecognitionException {
		RpContext _localctx = new RpContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_rp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165); match(T__2);
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

	public static class CommaContext extends ParserRuleContext {
		public CommaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comma; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterComma(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitComma(this);
		}
	}

	public final CommaContext comma() throws RecognitionException {
		CommaContext _localctx = new CommaContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_comma);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(167); match(T__1);
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

	public static class CompContext extends ParserRuleContext {
		public TerminalNode COMP() { return getToken(BaseQueryParser.COMP, 0); }
		public CompContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterComp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitComp(this);
		}
	}

	public final CompContext comp() throws RecognitionException {
		CompContext _localctx = new CompContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_comp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(169); match(COMP);
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

	public static class IdContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(BaseQueryParser.ID, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BaseQueryListener ) ((BaseQueryListener)listener).exitId(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171); match(ID);
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
		case 12: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 4);
		case 1: return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\23\u00b0\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\3\2\3\2\3\2\3\2\3\2\5\28\n\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\7\4A\n\4\f\4"+
		"\16\4D\13\4\3\4\7\4G\n\4\f\4\16\4J\13\4\3\5\3\5\3\5\3\5\7\5P\n\5\f\5\16"+
		"\5S\13\5\3\6\3\6\3\6\7\6X\n\6\f\6\16\6[\13\6\3\7\3\7\3\7\7\7`\n\7\f\7"+
		"\16\7c\13\7\3\b\3\b\3\b\3\b\7\bi\n\b\f\b\16\bl\13\b\3\t\3\t\3\t\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\nz\n\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r"+
		"\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u008b\n\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\7\16\u0095\n\16\f\16\16\16\u0098\13\16\3\17"+
		"\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26"+
		"\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\31\2\3\32\32\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&(*,.\60\2\2\u00a5\2\67\3\2\2\2\49\3\2\2\2\6=\3"+
		"\2\2\2\bK\3\2\2\2\nT\3\2\2\2\f\\\3\2\2\2\16d\3\2\2\2\20m\3\2\2\2\22p\3"+
		"\2\2\2\24{\3\2\2\2\26~\3\2\2\2\30\u0081\3\2\2\2\32\u008a\3\2\2\2\34\u0099"+
		"\3\2\2\2\36\u009b\3\2\2\2 \u009d\3\2\2\2\"\u009f\3\2\2\2$\u00a1\3\2\2"+
		"\2&\u00a3\3\2\2\2(\u00a5\3\2\2\2*\u00a7\3\2\2\2,\u00a9\3\2\2\2.\u00ab"+
		"\3\2\2\2\60\u00ad\3\2\2\2\628\5\4\3\2\638\5\6\4\2\648\5\b\5\2\658\5\n"+
		"\6\2\668\5\f\7\2\67\62\3\2\2\2\67\63\3\2\2\2\67\64\3\2\2\2\67\65\3\2\2"+
		"\2\67\66\3\2\2\28\3\3\2\2\29:\5\34\17\2:;\5\22\n\2;<\5\24\13\2<\5\3\2"+
		"\2\2=>\5\36\20\2>B\5\16\b\2?A\5\20\t\2@?\3\2\2\2AD\3\2\2\2B@\3\2\2\2B"+
		"C\3\2\2\2CH\3\2\2\2DB\3\2\2\2EG\5\26\f\2FE\3\2\2\2GJ\3\2\2\2HF\3\2\2\2"+
		"HI\3\2\2\2I\7\3\2\2\2JH\3\2\2\2KL\5 \21\2LM\5\16\b\2MQ\5\30\r\2NP\5\26"+
		"\f\2ON\3\2\2\2PS\3\2\2\2QO\3\2\2\2QR\3\2\2\2R\t\3\2\2\2SQ\3\2\2\2TU\5"+
		"\"\22\2UY\5\20\t\2VX\5\26\f\2WV\3\2\2\2X[\3\2\2\2YW\3\2\2\2YZ\3\2\2\2"+
		"Z\13\3\2\2\2[Y\3\2\2\2\\]\5$\23\2]a\5\20\t\2^`\5\26\f\2_^\3\2\2\2`c\3"+
		"\2\2\2a_\3\2\2\2ab\3\2\2\2b\r\3\2\2\2ca\3\2\2\2dj\5\32\16\2ef\5,\27\2"+
		"fg\5\32\16\2gi\3\2\2\2he\3\2\2\2il\3\2\2\2jh\3\2\2\2jk\3\2\2\2k\17\3\2"+
		"\2\2lj\3\2\2\2mn\7\17\2\2no\5\16\b\2o\21\3\2\2\2pq\7\20\2\2qy\5\60\31"+
		"\2rs\5(\25\2st\5\60\31\2tu\5,\27\2uv\5\60\31\2vw\3\2\2\2wx\5*\26\2xz\3"+
		"\2\2\2yr\3\2\2\2yz\3\2\2\2z\23\3\2\2\2{|\7\n\2\2|}\5\16\b\2}\25\3\2\2"+
		"\2~\177\7\r\2\2\177\u0080\5\16\b\2\u0080\27\3\2\2\2\u0081\u0082\7\16\2"+
		"\2\u0082\u0083\5\16\b\2\u0083\31\3\2\2\2\u0084\u0085\b\16\1\2\u0085\u0086"+
		"\5(\25\2\u0086\u0087\5\60\31\2\u0087\u0088\5*\26\2\u0088\u008b\3\2\2\2"+
		"\u0089\u008b\5\60\31\2\u008a\u0084\3\2\2\2\u008a\u0089\3\2\2\2\u008b\u0096"+
		"\3\2\2\2\u008c\u008d\f\6\2\2\u008d\u008e\5.\30\2\u008e\u008f\5\32\16\7"+
		"\u008f\u0095\3\2\2\2\u0090\u0091\f\4\2\2\u0091\u0092\5&\24\2\u0092\u0093"+
		"\5\32\16\5\u0093\u0095\3\2\2\2\u0094\u008c\3\2\2\2\u0094\u0090\3\2\2\2"+
		"\u0095\u0098\3\2\2\2\u0096\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097\33"+
		"\3\2\2\2\u0098\u0096\3\2\2\2\u0099\u009a\7\6\2\2\u009a\35\3\2\2\2\u009b"+
		"\u009c\7\7\2\2\u009c\37\3\2\2\2\u009d\u009e\7\b\2\2\u009e!\3\2\2\2\u009f"+
		"\u00a0\7\t\2\2\u00a0#\3\2\2\2\u00a1\u00a2\7\13\2\2\u00a2%\3\2\2\2\u00a3"+
		"\u00a4\7\f\2\2\u00a4\'\3\2\2\2\u00a5\u00a6\7\5\2\2\u00a6)\3\2\2\2\u00a7"+
		"\u00a8\7\3\2\2\u00a8+\3\2\2\2\u00a9\u00aa\7\4\2\2\u00aa-\3\2\2\2\u00ab"+
		"\u00ac\7\21\2\2\u00ac/\3\2\2\2\u00ad\u00ae\7\22\2\2\u00ae\61\3\2\2\2\r"+
		"\67BHQYajy\u008a\u0094\u0096";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}