package com.system.distribute.context;

import java.net.InetSocketAddress;

import com.system.distribute.core.DistributedOperations;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 上午11:38:45 
 * @function:上下文接口  他只是存储用户上面类所有过程中的变量不是config配置而且分布式中不会同步的只会在单点上有效 切记  、、后期如果想支持xml 配置或者其他配置 可以添加策略模式 
 */
public interface Context {

	public final static String defaultConfig="distribute.conf";//默认配置名
	
	public void putValue(String key,Object value);
	
	public Object getValue(String key);
	
	public void setCurrHost(InetSocketAddress address);
	
	public void setCurrHost(String host,int port);
	
	public InetSocketAddress getCurrHost();
	/**
	 * 获得默认配置文件
	 * @return
	 * 添加（修改）人：zhuyuping
	 */
	public String getDefaultFc();
//	/**
//	 * 设置默认属性文件的名称
//	 * @param pfile
//	 * 添加（修改）人：zhuyuping
//	 */
//	public void setDefaultFc(String pfile);
//	/**
//	 * 注入template 门面 便于后面直接通过上下文来使用  如果要整合spring ApplicationContextAware
//	 * @param distributedTemplate
//	 * 添加（修改）人：zhuyuping
//	 */
//	public void setTemplate(DistributedOperations distributedTemplate);
//	
//	public DistributedOperations getTemplate();
	
	
}
