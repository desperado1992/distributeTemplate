package com.system.distribute.core;

import com.system.distribute.file.FNode;
import com.system.distribute.file.FileAdapter;
import com.system.distribute.sqlparser.Query;
//extends NodeManager 吧基层改为 set 注入 便于后面整合spring  implenments ApplicationContextWare/FactoryBean
public class DistributedTemplate extends DSLParser implements DistributedOperations{

	//node 分装一些用户前面facede 模板用户用户操作需要的 方法的基本
	private NodeManager nodeManager;
	
	public NodeManager getNodeManager() {
		return nodeManager;
	}

	public void setNodeManager(NodeManager nodeManager) {
		this.nodeManager = nodeManager;
	}

	public DistributedTemplate(NodeManager nodeManager) {
		super();
		this.nodeManager = nodeManager;
		
	}

	public DistributedTemplate() {
		super();
		
	}
	
	
    

	/**
     * callback为回调  adapter为来源的处理 
     */
	@Override
	public <T> T query(String sql, Callback<T> callback, Adapter adapter)
			throws Exception {
		
	      return callback.action(executeCommand(sql, adapter));
	    
		
	}
	public void insertFile(String sql) throws Exception{
		
		insert(sql, new FileAdapter<FNode>());
	}

	@Override
	public <T> void insert(String sql, Adapter<T> adapter) throws Exception {
		  executeCommand(sql, adapter);
	}

	private <T> ResultSet executeCommand(String sql, Adapter<T> adapter)
			throws Exception {
		Query query=parser(sql);
	      adapter.setNodeManager(nodeManager);
		  return adapter.adapter(query);
	}

	@Override
	public <T> void sync(String sql, Adapter<T> adapter) throws Exception {
		
		 executeCommand(sql, adapter);
	}

	public  void syncFile(String sql) throws Exception {
		
		sync(sql, new FileAdapter<FNode>());
	}
	
	@Override
	public <T> void delete(String sql, Adapter<T> adapter) throws Exception {
		
		 executeCommand(sql, adapter);
	}

	public  void deleteFile(String sql) throws Exception {
		
		delete(sql, new FileAdapter<FNode>());
	}

	

}
