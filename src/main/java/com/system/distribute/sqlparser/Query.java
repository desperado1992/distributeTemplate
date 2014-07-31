package com.system.distribute.sqlparser;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

import com.google.common.collect.BiMap;
import com.system.distribute.sqlparser.Condition.Relative;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-18 下午12:18:52 
 * @function:查询 对象的接口 链式模式
 */
public interface Query {
   
//	/**
//	 * 设置解析基类 有默认sql 的 ---策略模式 代理模式  交由parser去解析 
//	 * @param parser
//	 * 添加（修改）人：zhuyuping
//	 */
//	 void  setParser(QueryParser parser);
	 
	 /**
	  * 设置 查询的命令类型 
	  * @param select
	  */
	Query setCommand(Command select);
	
	Command getCommand();

	/**
	 * 往返回的
	 * @param text
	 * @return
	 */
	Query addReturnValues(String text);
    /**
     * 添加别名
     * @param pchild
     * @param text
     * @return
     */
	Query addAlias(String pchild, String text);
    /**
     * 添加被操作的对象
     * @param text
     * @return
     */
	Query addOperations(String text);
    /**
     * 添加查询条件
     * @param text
     * @param value
     * @param relative
     * @return
     */
	Query addConditions(String text, String value, Relative relative);

	List<Condition> getConditions();

	List<String> getOperations();

	List<String> getNeedvalues();

	BiMap<String, String> getAlias();
	
	
	
	//添加构造函数 传入sql 的 或者 提供解析 sql 生存 query的 
	
}
