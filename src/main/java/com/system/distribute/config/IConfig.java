package com.system.distribute.config;

import java.net.InetSocketAddress;

import com.google.common.collect.BiMap;
import com.google.common.collect.Table;
import com.system.distribute.context.Context;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 上午11:33:32 
 * @function:配置的接口
 */
public interface IConfig {
	
	

    public static final String DEFAULTFILE="distribute.conf";
	/**
	 * 
	 * 从文件读取配置初始化
	 * @param file
	 * 添加（修改）人：zhuyuping
	 */
	public void readConfigFormFile(String file);
	/**
	 * 从用户提供的json字符串中初始化
	 * @param json
	 * 添加（修改）人：zhuyuping
	 */
	public void readConfigFormString(String json);
	/**
	 * 获得系统内存中维护的内存表
	 * 
	 * 添加（修改）人：zhuyuping
	 */
	public Table<InetSocketAddress, String, Object> getConfig();
	/**
	 * 摄入context上下文
	 * @param context
	 * 添加（修改）人：zhuyuping
	 */
	public void setContext(Context context);
	/**
	 * 获得上下文context
	 * @return
	 * 添加（修改）人：zhuyuping
	 */
	public Context getContext();
	
	public BiMap<String, InetSocketAddress> getAlias();
	
}
