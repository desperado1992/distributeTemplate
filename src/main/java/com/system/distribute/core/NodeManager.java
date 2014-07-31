package com.system.distribute.core;

import java.net.InetSocketAddress;
import java.util.List;

import com.system.distribute.config.IConfig;
import com.system.distribute.file.FileSyncTask;
import com.system.distribute.file.helper.ServerHelper;
import com.system.distribute.service.Service;
import com.system.distribute.sqlparser.BaseQuery;
import com.system.distribute.sqlparser.Query;

/*
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-11 下午3:28:01 
 * @function:默认节点管理器  分装一些用户前面facede 模板用户用户操作需要的 方法的基本
 */
public abstract class NodeManager extends AbstractNodeManager implements NodeOperations{

	
	protected Node cNode;//当前系统节点
	
	
	
	public NodeManager(NodeFactory nodeFactory, Service service) {
		super(nodeFactory, service);
		initNode(nodeFactory);
		service.setConfig(cNode.getConfig());
		startService();
	}
    /**
     * 交给子类 才能初始化当前系统节点类型
     * @param factory
     * 添加（修改）人：zhuyuping
     */
	public abstract void initNode(NodeFactory factory);
	
	
	
	
    public Node getcNode() {
		return cNode;
	}
	/**
     * 交给子类 去实现 去 启动 
     * 
     * 添加（修改）人：zhuyuping
     */
	protected abstract void startService();
	
	protected abstract void stopService();
	/**
	 * 轮训节点 这里 以后版本需加上策略  实现各种负载均衡的手段 优先级 权重  以及 固定队列大小的
	 * 
	 * 添加（修改）人：zhuyuping
	 */
	protected abstract ResultSet loop(List<InetSocketAddress> address,Query query);
	
	
}
