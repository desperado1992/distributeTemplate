package com.system.distribute.sqlparser;


import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;

import com.system.distribute.sqlparser.BaseQueryParser.CommaContext;
import com.system.distribute.sqlparser.BaseQueryParser.CompContext;
import com.system.distribute.sqlparser.BaseQueryParser.ExprContext;
import com.system.distribute.sqlparser.BaseQueryParser.Expr_lstContext;
import com.system.distribute.sqlparser.BaseQueryParser.From_causeContext;
import com.system.distribute.sqlparser.BaseQueryParser.IdContext;
import com.system.distribute.sqlparser.BaseQueryParser.Into_causeContext;
import com.system.distribute.sqlparser.BaseQueryParser.KasContext;
import com.system.distribute.sqlparser.BaseQueryParser.KdeleteContext;
import com.system.distribute.sqlparser.BaseQueryParser.KinsertContext;
import com.system.distribute.sqlparser.BaseQueryParser.KselectContext;
import com.system.distribute.sqlparser.BaseQueryParser.KsyncContext;
import com.system.distribute.sqlparser.BaseQueryParser.KupdateContext;
import com.system.distribute.sqlparser.BaseQueryParser.SqlContext;
import com.system.distribute.sqlparser.BaseQueryParser.Values_causeContext;
import com.system.distribute.sqlparser.BaseQueryParser.Where_causeContext;
import com.system.distribute.sqlparser.Condition.Relative;
/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-18 下午12:20:42 
 * @function:sql 监听类 获取 从sql中提取 有效数据
 */
public class SQLListener extends BaseQueryBaseListener implements
ParseTreeListener{
  //(sql (select_statement (kselect select) (expr_lst (expr (id u.name)) (comma ,) (expr (id u.password))) (from_cause from (expr_lst (expr (id user) u))) (where_cause where (expr_lst (expr (expr (id u.name)) (comp =) (expr (id zhuyuping)))))))
	private Query query;//生成query查询对象
    
	private int count=0;//统计
	
	private int flag=0;

	private boolean hasalis=false;
	
	private boolean iscomp=false;
	boolean isinsert=false;
	
	private Relative relative;
	
	IdContext pchild=null;
	 
	
	//TODO  一个是空格 别名问题  2.括号

	public SQLListener(Query query) {
		super();
		this.query = query;
	}

	@Override
	public void enterKselect(KselectContext ctx) {
		//进入select 语句 查询
		//
       query.setCommand(Command.SELECT);
		
	}

	@Override
	public void enterKupdate(KupdateContext ctx) {
		query.setCommand(Command.UPDATE);
	
	}

	@Override
	public void enterKsync(KsyncContext ctx) {
		query.setCommand(Command.SYNC);
		
	}

	@Override
	public void enterKdelete(KdeleteContext ctx) {
		query.setCommand(Command.DELETE);
		
	}

	@Override
	public void enterKinsert(KinsertContext ctx) {
		query.setCommand(Command.INSERT);
	}

	@Override
	public void enterExpr_lst(Expr_lstContext ctx) {
		
		
		count++;
		
	}
	
	

	@Override
	public void enterKas(KasContext ctx) {
		hasalis=true;
	}

	
	
	

	

	@Override
	public void exitExpr_lst(Expr_lstContext ctx) {
		if(hasalis) hasalis=false;
		if(iscomp) iscomp=false;
	}

	@Override
	public void exitComma(CommaContext ctx) {
		if(hasalis) hasalis=false;
	}

	@Override
	public void enterId(IdContext ctx) {
		pchild=ctx;
		if(isinsert){
			query.addOperations(ctx.getText());
		}
	}

	
	
	
	@Override
	public void enterValues_cause(@NotNull Values_causeContext ctx) {
	  flag=3;
	}

	@Override
	public void enterComp(CompContext ctx) {
		
		iscomp=true;
		if("=".equals(ctx.getText().trim())){
			relative=Relative.EQUAL;
		}else{
			relative=Relative.LIKE;
		}
		
	}

	

	@Override
	public void enterExpr(ExprContext ctx) {
		 int num= ctx.getChildCount();
		 ParseTree cchild=null;
		
		  if(count==1&flag==0){
			  //第一个李彪
			
			  for (int i=0; i<num;i++) {
				 
				 cchild=ctx.getChild(i);
//				 pchild=ctx.getChild(i-1>=0?i-1:0);
//				 nchild=ctx.getChild(i+1>num?num:i+1);
				 
				 if(cchild instanceof IdContext){
					 IdContext id=(IdContext) cchild;
					 query.addReturnValues(id.getText());
					 
				 }
//				 }else if(child instanceof LpContext){
//					  flag=true;
//					 
//				 }else if(child instanceof RpContext){
//					 flag=false;
//					 
//				 }
// (sql (select_statement (kselect select) (expr_lst (expr (lp () (id name) (rp ))) (comma ,) (expr (lp () (id password) (rp )))) (from_cause from (expr_lst (expr (expr (id user)) (kas as) (expr (id u))))) (where_cause where (expr_lst (expr (expr (id name)) (comp =) (expr (id zhuyuping)))))))				 
//				 (sql (insert_statement (kinsert insert) (into_cause into (id u)) (values_cause values (expr_lst (expr (lp () (id d:/upload) (rp )))))))		
			  }
			
		  }else if(count>=1&&flag==1){
			  //from 语句
			
			 
			  for (int i=0; i<num;i++) {
					 
					 cchild=ctx.getChild(i);
//					 pchild=ctx.getChild(i-1>=0?i-1:0);
//					 nchild=ctx.getChild(i+1>num?num:i+1);
					
					 if(cchild instanceof IdContext){
						 IdContext id=(IdContext) cchild;
						 String value=id.getText();
						 if(hasalis){
							  //别名
							
							  query.addAlias(value,pchild.getText());
						  }else{
							  
							  query.addOperations(value); 
						  }
						 
					 }

					 
				}
			 
			 
			  
			  
		  }else if(count>1&&flag==2){
			  //where 语句 
			  for (int i=0; i<num;i++) {
					 
					 cchild=ctx.getChild(i);
				
					
					
					 if(cchild instanceof IdContext){
						 IdContext id=(IdContext) cchild;
						 String value=id.getText();
						 if(iscomp){
							  //别名
							  
							  query.addConditions(pchild.getText(),value,relative);
						  }
						 
					 }

					 
				}
		  }else if(count==1&&flag==3){
			  //values值得解析
			  for (int i=0; i<num;i++) {
					 
					 cchild=ctx.getChild(i);
				
					
					
					 if(cchild instanceof IdContext){
						 IdContext id=(IdContext) cchild;
						 String value=id.getText();
						 System.out.println("=============== "+value); 
							 
							 //放入需要的文件名 
							query.addConditions("name", value, Relative.EQUAL);
						 
						 
					 }

					 
				}
			  
			  
		  }

	}

	@Override
	public void enterFrom_cause(From_causeContext ctx) {
		flag=1;
	}

	

	@Override
	public void exitFrom_cause(From_causeContext ctx) {
		hasalis=false;
	}

	@Override
	public void enterWhere_cause(Where_causeContext ctx) {
		flag=2;
		
	}

	@Override
	public void exitWhere_cause(Where_causeContext ctx) {
		iscomp=false;
	}

	@Override
	public void exitSql(SqlContext ctx) {
	  flag=0;
	  count=0;
	}

	@Override
	public void enterInto_cause(Into_causeContext ctx) {
		isinsert=true;
		
	}

	@Override
	public void exitInto_cause(Into_causeContext ctx) {
		isinsert=false;
	}

	
	
	
	
	
	
	
}
