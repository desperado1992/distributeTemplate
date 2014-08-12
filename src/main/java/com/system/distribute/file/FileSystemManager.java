  package com.system.distribute.file;


import java.net.InetSocketAddress;
import java.nio.file.Paths;
import java.util.List;

import com.google.common.collect.BiMap;

import com.google.common.collect.ImmutableMap;
import com.system.distribute.core.NodeFactory;
import com.system.distribute.core.NodeManager;
import com.system.distribute.core.ResultSet;
import com.system.distribute.file.helper.HttpUploadClient;
import com.system.distribute.file.helper.ServerHelper;
import com.system.distribute.file.monitor.DirectoryMonitor;
import com.system.distribute.file.monitor.DirectoryMonitorEventListenerImpl;
import com.system.distribute.file.monitor.DirectoryMonitorImpl;
import com.system.distribute.file.monitor.FileEvent;
import com.system.distribute.service.Service;
import com.system.distribute.sqlparser.Command;
import com.system.distribute.sqlparser.Condition;
import com.system.distribute.sqlparser.Query;
import com.system.distribute.sqlparser.Condition.Relative;
/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 下午1:07:13  后期可以扩展 加入同步区 非同步去
 * @function:就是windows的文件管理器 但是他是分布式上的维护者 维护各个主机上的filesystem 维护者文件节点 文件节点里面有目录 文件的节点树 
 */
public class FileSystemManager extends NodeManager{// implements SimpleFileSystemManager{

	
    
	
	

	public FileSystemManager(NodeFactory nodeFactory, Service service) {
		super(nodeFactory, service);
	}
	private DirectoryMonitor directoryMonitor;
    /**
     * 停止 服务
     * 
     * 添加（修改）人：zhuyuping
     */
	@Override
	public void stopService(){
		getService().stop();
		directoryMonitor.stop();
		getcNode().stop();
	}
	@Override
	public void startService(){
		getService().start();
		Object path=cNode.getConfig().getContext().getValue("filesync");
		if(path==null) throw new RuntimeException("文件系统配置没有启动");
		directoryMonitor=new DirectoryMonitorImpl(Paths.get(ServerHelper.gennerFilePath(String.valueOf(path))), true, new DirectoryMonitorEventListenerImpl(this));
		directoryMonitor.start();
	}

	


	public void addFileHandler(FileEvent fileEvent) {
		
		cNode.addHandler(fileEvent,this);
		
	}

	@Override
	public void initNode(NodeFactory factory) {
		
		cNode=factory.createNode(FileSystem.class);
	}
	@Override
	protected ResultSet loop(List<InetSocketAddress> address, Query query) {
	     BiMap<String, String> alias=query.getAlias();
	     com.google.common.collect.ImmutableMap.Builder<String,Object> build=ImmutableMap.<String,Object>builder();
	     List<Condition> conditions=query.getConditions();
	     switch (query.getCommand()) {
		case INSERT:
			 build.put("commandType",String.valueOf(Command.INSERT.ordinal()));
			 for (Condition condition : conditions) {
				String name=condition.getX();
				String path=condition.getY();
				build.put(name,path);
			 }
			 getService().insert(address,build.build());
			
			break;
		case SELECT:
			
	         for (Condition condition : conditions) {
				     Relative relative=condition.getRelative();
				     if(relative.equals(Relative.LIKE)){
				    	 build.put(condition.getX(), "%"+condition.getY()+"%"); 
				     }else{
					 build.put(condition.getX(), condition.getY());
				     }

			}
	        
	        build.put("commandType",String.valueOf(Command.SELECT.ordinal()));//命令类型 标示为查询
			for (InetSocketAddress host : address) {
				String uri=ServerHelper.genenerBaseUri(host);
				try {
					 Object result=HttpUploadClient.get(uri, build.build());
					 //服务器端解析 返回需要的类型
					 
				} catch (Exception e) {
					return null;
				}
			} 
             break;
		case UPDATE:
			
			 break;
		case DELETE:
			build.put("commandType",String.valueOf(Command.DELETE.ordinal()));
			 for (Condition condition : conditions) {
					String name=condition.getX();
					String path=condition.getY();
					build.put(name,path);
				 }
			getService().delete(address,build.build());
			break;
		case SYNC:
			 build.put("commandType",String.valueOf(Command.SYNC.ordinal()));
			 for (Condition condition : conditions) {
					String name=condition.getX();
					String path=condition.getY();
					build.put(name,path);
				 }
			getService().sync(address,build.build());
			break;
		default:
			break;
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
}
