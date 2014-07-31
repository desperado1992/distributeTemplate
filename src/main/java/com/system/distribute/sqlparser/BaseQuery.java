package com.system.distribute.sqlparser;

import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;





import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.system.distribute.sqlparser.Condition.Relative;

/**
 * 查询对象 query
 * @author Administrator
 *
 */
public  class BaseQuery implements Query{

	private Command command;//命令的对象
	
	private List<String> needvalues=Lists.newArrayList();//需要的获取对象
	
	private List<String> operations=Lists.newArrayList();//被操作的对象
	
	private List<Condition> conditions=Lists.newArrayList();

	private BiMap<String,String> alias= HashBiMap.create(); //别名 

   
	@Override
	public Query setCommand(Command select) {
		this.command=select;
		return this;
	}
    @Override
	public List<String> getNeedvalues() {
		return needvalues;
	}

	public void setNeedvalues(List<String> needvalues) {
		this.needvalues = needvalues;
	}
	@Override
	public List<String> getOperations() {
		return operations;
	}
	
	public void setOperations(List<String> operations) {
		this.operations = operations;
	}
	@Override
	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Query addReturnValues(String text) {
	    
		needvalues.add(text);
		//需要 加入别名 解析 
		return this;
	}
	
	@Override
	public Query addAlias(String alia, String origin) {
		alias.put(alia, origin);
		return this;
	}

	@Override
	public Query addOperations(String text) {
		
		operations.add(text);
		return this;
	}

	@Override
	public Query addConditions(String x, String y, Relative relative) {
		
		conditions.add(new Condition(x,relative,y));
		return this;
	}

	@Override
	public String toString() {
		return "BaseQuery [command=" + command + ", needvalues=" + needvalues
				+ ", operations=" + operations + ", conditions=" + conditions
				+ ", alias=" + alias + "]";
	}

	@Override
	public Command getCommand() {
		
		return command;
	}
	@Override
	public BiMap<String, String> getAlias() {
		
		return alias;
	}
	
	
	
	
}
