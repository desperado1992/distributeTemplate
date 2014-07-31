package com.system.distribute.core;

import java.net.InetSocketAddress;

import com.system.distribute.config.IConfig;
import com.system.distribute.file.monitor.FileEvent;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 下午13:01:19 
 * @function:分布式文件的节点
 */
public interface Node {

	public InetSocketAddress getAddress();//获得当前节点的地址
	
	
	
	public String getName();//别名 用户sql查询
	
	
	//public Long getVersion();//版本可能还不需要
	public IConfig getConfig();


	public void setConfig(IConfig config);
	
	/**
	 * 本地系统节点事件的轮训
	 * 
	 * 添加（修改）人：zhuyuping
	 */
	public void doLoop();
	
	/**
	 * 停止节点服务
	 * 
	 */
     public void stop();

    /**
     * 给系统节点添加事件 不同的实现 实现不同
     * @param Event
     * @param NodeManager
     * 添加（修改）人：zhuyuping
     */
	public void addHandler(Event event,
			NodeManager nodeManager);
	
}
