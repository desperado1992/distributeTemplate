package com.system.distribute.core;

import com.system.distribute.sqlparser.Query;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 下午12:37:38 
 * @function:外部数据源，后期需引入dsl 解析器 主要有 也许要提供xml adapter解析定义
 */
public interface Adapter {
    /**
     * 设置 节点管理器  用户 对外部数据源的查询
     * @param nodeManager
     * 添加（修改）人：zhuyuping
     */
	void setNodeManager(NodeManager nodeManager);
    //来源
    /**
     * 通过NodeManger进行查询 得到结果集
     * @param query
     * @return
     * 添加（修改）人：zhuyuping
     * @throws Exception 
     */
	ResultSet adapter(Query query) throws Exception;
	
	
}
