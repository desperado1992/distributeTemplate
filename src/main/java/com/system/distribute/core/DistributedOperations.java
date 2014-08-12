package com.system.distribute.core;


/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * 
 * 1.file 1.0
 * 2.mysql(jdbc)/mongodb 2.0 2.1 
 * 3.memcache/redis amqp/rabbitmq 3.0 3.1 3.2 3.3
 * 4.提供hadoop整合  4.0
 * 5.整合提供esb功能 5.0
 * 
 * @created 2014-7-9 上午11:42:05 
 * @function:分布式一些操作接口的定义 提供sql方式  对分布式文件系统 等其他进行分布式上增删改查 
  */
public interface DistributedOperations {

	<T> T query(String sql,Callback<T> callback,Adapter adapter) throws Exception;
	<T> void insert(String sql,Adapter adapter) throws Exception;
	<T> void sync(String sql,Adapter adapter) throws Exception;
	<T>	 void delete(String sql, Adapter adapter) throws Exception;
}
