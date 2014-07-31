package com.system.distribute.context;

import java.net.InetSocketAddress;
import java.util.Map;

import com.google.common.collect.Maps;

import com.typesafe.config.ConfigFactory;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 下午5:58:37 
 * @function:抽象的上下文 主要是 管理context的资源 还有就是提供用户自定义 整合spring使用该类 //这里后期需整合策略 实现
 */
public abstract class AbstractContext implements Context{
    //?也可以使用LocalThread 也可以
	private Map<String,Object> context=Maps.newConcurrentMap();
	
	private InetSocketAddress currHost;//当前主机 比如192.168.0.1 8888
	
	private String dfConfig;//默认读取的配置文件 当用户 提供就修改 没有提供就默认 
	
    
	public AbstractContext(String dfConfig) {
		super();
		
		this.dfConfig = dfConfig;
		//当前classes 下的文件
		//currentPort
		this.currHost=new InetSocketAddress(ConfigFactory.load(dfConfig).getString("client.currentHost"),ConfigFactory.load(dfConfig).getInt("client.currentPort"));
	}

	
	
	@Override
	public InetSocketAddress getCurrHost() {
		
		return currHost;
	}

    

	@Override
	public void setCurrHost(InetSocketAddress address) {
		 this.currHost=address;
		
	}



	@Override
	public void setCurrHost(String host,int port) {
		
		this.currHost=new InetSocketAddress(host, port);
		
	}



	@Override
	public String getDefaultFc() {
		
		return dfConfig!=null?dfConfig:defaultConfig;
	}



	public AbstractContext() {
		super();
		this.dfConfig=defaultConfig;

		this.currHost=new InetSocketAddress(ConfigFactory.load(defaultConfig).getString("client.currentHost"),ConfigFactory.load(defaultConfig).getInt("client.currentPort"));
	    
	}



	@Override
	public void putValue(String key, Object value) {
		
		context.put(key, value);
		
	}

	@Override
	public Object getValue(String key) {
		return context.get(key);
	}
	
	
	
}
