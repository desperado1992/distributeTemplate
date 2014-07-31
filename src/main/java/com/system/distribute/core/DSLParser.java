package com.system.distribute.core;

import com.system.distribute.sqlparser.BaseQuery;
import com.system.distribute.sqlparser.Query;
import com.system.distribute.sqlparser.SQLListener;
import com.system.distribute.sqlparser.SqlParserUtils;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-11 下午3:40:44 
 * @function:领域驱动的解析类 //暂时只是去实现基本sql解析  用户只需要继承SQLParser实现自定义的解析方式
 */
public class DSLParser extends SQLParser{

	@Override
	protected Query parser(String sql) {
		Query query=new BaseQuery();
		SQLListener listener=new SQLListener(query);
		SqlParserUtils.parser(sql, listener);
		
		return query;
	}
	//测试方法
	public static void main(String[] args) {
		System.out.println("DSLParser.main() "+new DSLParser().parser("select * from user where name=c:/upload"));
	}

}
