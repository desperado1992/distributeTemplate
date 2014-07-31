package com.system.distribute.core;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.system.distribute.service.Service;
import com.system.distribute.sqlparser.BaseQuery;
import com.system.distribute.sqlparser.Query;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 下午12:01:57 
 * @function:节点管理其 采用工厂生成 需要注入工厂 以及 session管理器  以及 节点通讯访问的服务
 */
public abstract class AbstractNodeManager {
   
	/**
     * 维护节点的信息
     */
	protected Map<String,Node> nodes=Maps.newConcurrentMap();
	
	
	/**
	 * 需要注入  节点工厂
	 * @param nodeFactory
	 * @param service
	 */
	public AbstractNodeManager(NodeFactory nodeFactory, Service service) {
		super();
		this.nodeFactory = nodeFactory;
		this.service = service;
	}

	
	
	private SessionManager sessionManager;//生命周期会话管理器
	
	private NodeFactory nodeFactory;//节点工厂
	
	private Service service;//服务
	
	

	public Map<String, Node> getNodes() {
		return nodes;
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public NodeFactory getNodeFactory() {
		return nodeFactory;
	}

	public Service getService() {
		return service;
	}
	
	
	
}
