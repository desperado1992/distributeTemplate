package com.system.distribute.core;

import com.system.distribute.sqlparser.Query;

public abstract class SQLParser implements Parser{

	
	protected abstract Query parser(String sql);
	
	//TODO 这里提供基本的解析 select update delete 以及其他的 
	
}
