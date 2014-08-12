package com.system.distribute.sqlparser;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class SqlParserUtils {

	public static void parser(String sql,SQLListener listener){
//        ANTLRInputStream input = new ANTLRInputStream(sql);
//		
//		BaseQueryLexer lexer = new BaseQueryLexer(input);
//		                 
//		CommonTokenStream tokens = new CommonTokenStream(lexer);
//		
//		BaseQueryParser parser = new BaseQueryParser(tokens); 
//		
//		ParseTree tree = parser.sql();
//	
//		ParseTreeWalker walker = new ParseTreeWalker();
//		walker.walk(listener, tree);
		
	}
	
	public static void main(String[] args) throws Exception {
//		ANTLRInputStream input = new ANTLRInputStream("select u.name,u.password from u where name=c:/upload");
//		//(sql (sync_statement (ksync sync) (from_cause from (expr_lst (expr (id u)))) (where_cause where (expr_lst (expr (expr (id name)) (comp =) (expr (id d:/upload)))))))
//		BaseQueryLexer lexer = new BaseQueryLexer(input);
//		//[command=INSERT, needvalues=[], operations=[], conditions=[Condition [x=name, relative=EQUAL, y=d:/upload]], alias={}]                 
//		CommonTokenStream tokens = new CommonTokenStream(lexer);
//		
//		BaseQueryParser parser = new BaseQueryParser(tokens); 
//		
//		ParseTree tree = parser.sql();
//		System.out.println(tree.toStringTree(parser));
//		
//		Query query=new BaseQuery();
//		
//		ParseTreeWalker walker = new ParseTreeWalker();
//		walker.walk(new SQLListener(query), tree);
//		System.out.println("SqlParserUtils.main() "+query);
		String name="name";
		String[] strs=name.split("\\.");
		for (String string : strs) {
			System.out.println("SqlParserUtils.parser() "+string);
		}
	}
}
