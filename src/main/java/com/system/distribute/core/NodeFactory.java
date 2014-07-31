package com.system.distribute.core;

import java.util.Map;

import com.google.common.collect.Maps;
import com.system.distribute.config.IConfig;
import com.system.distribute.file.FileSystem;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 下午12:36:34 
 * @function:节点的管理工厂 用户创建以后各种工厂 比如 文件的  mysql 的 memcache rabbitmq的 节点 
 */
public class NodeFactory {
	 
	
	
	
	public IConfig getConfig() {
		return config;
	}

	public void setConfig(IConfig config) {
		this.config = config;
	}
    
	
	public NodeFactory(IConfig config) {
		super();
		this.config = config;
	}


	private IConfig config;

	private static Map<String,Node> caches=Maps.newHashMap();
	
	public  Node createNode(Class<? extends Node> clazz){
		Node nd=null;
		if(clazz.isAssignableFrom(FileSystem.class)){
		
			nd=caches.get(FileSystem.NAME);
			if(nd==null){
				nd=new FileSystem(config);
				caches.put(FileSystem.NAME, nd);
				}
		}//....
		
		return nd;
		
	}
	
	
	
	
	
}
