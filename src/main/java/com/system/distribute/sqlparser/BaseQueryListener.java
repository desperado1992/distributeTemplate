package com.system.distribute.sqlparser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BaseQueryParser}.
 */
public interface BaseQueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRp(@NotNull BaseQueryParser.RpContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRp(@NotNull BaseQueryParser.RpContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#sync_statement}.
	 * @param ctx the parse tree
	 */
	void enterSync_statement(@NotNull BaseQueryParser.Sync_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#sync_statement}.
	 * @param ctx the parse tree
	 */
	void exitSync_statement(@NotNull BaseQueryParser.Sync_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#expr_lst}.
	 * @param ctx the parse tree
	 */
	void enterExpr_lst(@NotNull BaseQueryParser.Expr_lstContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#expr_lst}.
	 * @param ctx the parse tree
	 */
	void exitExpr_lst(@NotNull BaseQueryParser.Expr_lstContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#comp}.
	 * @param ctx the parse tree
	 */
	void enterComp(@NotNull BaseQueryParser.CompContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#comp}.
	 * @param ctx the parse tree
	 */
	void exitComp(@NotNull BaseQueryParser.CompContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#comma}.
	 * @param ctx the parse tree
	 */
	void enterComma(@NotNull BaseQueryParser.CommaContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#comma}.
	 * @param ctx the parse tree
	 */
	void exitComma(@NotNull BaseQueryParser.CommaContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#kas}.
	 * @param ctx the parse tree
	 */
	void enterKas(@NotNull BaseQueryParser.KasContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#kas}.
	 * @param ctx the parse tree
	 */
	void exitKas(@NotNull BaseQueryParser.KasContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#kupdate}.
	 * @param ctx the parse tree
	 */
	void enterKupdate(@NotNull BaseQueryParser.KupdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#kupdate}.
	 * @param ctx the parse tree
	 */
	void exitKupdate(@NotNull BaseQueryParser.KupdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#ksync}.
	 * @param ctx the parse tree
	 */
	void enterKsync(@NotNull BaseQueryParser.KsyncContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#ksync}.
	 * @param ctx the parse tree
	 */
	void exitKsync(@NotNull BaseQueryParser.KsyncContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#kdelete}.
	 * @param ctx the parse tree
	 */
	void enterKdelete(@NotNull BaseQueryParser.KdeleteContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#kdelete}.
	 * @param ctx the parse tree
	 */
	void exitKdelete(@NotNull BaseQueryParser.KdeleteContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#lp}.
	 * @param ctx the parse tree
	 */
	void enterLp(@NotNull BaseQueryParser.LpContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#lp}.
	 * @param ctx the parse tree
	 */
	void exitLp(@NotNull BaseQueryParser.LpContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#update_statement}.
	 * @param ctx the parse tree
	 */
	void enterUpdate_statement(@NotNull BaseQueryParser.Update_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#update_statement}.
	 * @param ctx the parse tree
	 */
	void exitUpdate_statement(@NotNull BaseQueryParser.Update_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#values_cause}.
	 * @param ctx the parse tree
	 */
	void enterValues_cause(@NotNull BaseQueryParser.Values_causeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#values_cause}.
	 * @param ctx the parse tree
	 */
	void exitValues_cause(@NotNull BaseQueryParser.Values_causeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(@NotNull BaseQueryParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(@NotNull BaseQueryParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#set_cause}.
	 * @param ctx the parse tree
	 */
	void enterSet_cause(@NotNull BaseQueryParser.Set_causeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#set_cause}.
	 * @param ctx the parse tree
	 */
	void exitSet_cause(@NotNull BaseQueryParser.Set_causeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#insert_statement}.
	 * @param ctx the parse tree
	 */
	void enterInsert_statement(@NotNull BaseQueryParser.Insert_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#insert_statement}.
	 * @param ctx the parse tree
	 */
	void exitInsert_statement(@NotNull BaseQueryParser.Insert_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#into_cause}.
	 * @param ctx the parse tree
	 */
	void enterInto_cause(@NotNull BaseQueryParser.Into_causeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#into_cause}.
	 * @param ctx the parse tree
	 */
	void exitInto_cause(@NotNull BaseQueryParser.Into_causeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#id}.
	 * @param ctx the parse tree
	 */
	void enterId(@NotNull BaseQueryParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#id}.
	 * @param ctx the parse tree
	 */
	void exitId(@NotNull BaseQueryParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#delete_statement}.
	 * @param ctx the parse tree
	 */
	void enterDelete_statement(@NotNull BaseQueryParser.Delete_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#delete_statement}.
	 * @param ctx the parse tree
	 */
	void exitDelete_statement(@NotNull BaseQueryParser.Delete_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#sql}.
	 * @param ctx the parse tree
	 */
	void enterSql(@NotNull BaseQueryParser.SqlContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#sql}.
	 * @param ctx the parse tree
	 */
	void exitSql(@NotNull BaseQueryParser.SqlContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#kinsert}.
	 * @param ctx the parse tree
	 */
	void enterKinsert(@NotNull BaseQueryParser.KinsertContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#kinsert}.
	 * @param ctx the parse tree
	 */
	void exitKinsert(@NotNull BaseQueryParser.KinsertContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#from_cause}.
	 * @param ctx the parse tree
	 */
	void enterFrom_cause(@NotNull BaseQueryParser.From_causeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#from_cause}.
	 * @param ctx the parse tree
	 */
	void exitFrom_cause(@NotNull BaseQueryParser.From_causeContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#select_statement}.
	 * @param ctx the parse tree
	 */
	void enterSelect_statement(@NotNull BaseQueryParser.Select_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#select_statement}.
	 * @param ctx the parse tree
	 */
	void exitSelect_statement(@NotNull BaseQueryParser.Select_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#kselect}.
	 * @param ctx the parse tree
	 */
	void enterKselect(@NotNull BaseQueryParser.KselectContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#kselect}.
	 * @param ctx the parse tree
	 */
	void exitKselect(@NotNull BaseQueryParser.KselectContext ctx);
	/**
	 * Enter a parse tree produced by {@link BaseQueryParser#where_cause}.
	 * @param ctx the parse tree
	 */
	void enterWhere_cause(@NotNull BaseQueryParser.Where_causeContext ctx);
	/**
	 * Exit a parse tree produced by {@link BaseQueryParser#where_cause}.
	 * @param ctx the parse tree
	 */
	void exitWhere_cause(@NotNull BaseQueryParser.Where_causeContext ctx);
}