package com.system.distribute.config;



import java.io.File;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Table;
import com.system.distribute.context.Context;
import com.system.distribute.util.TypeSafeConfigLoadUtils;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 下午4:07:00  如果后期需要支持xml 其他 只需要使用策略模式 
 * @function:基类config 主要维持 配置基本信息 以及配置信息的同步 备份 同时内存中维持一张内存表table
 */
public abstract class AbstractConfig implements IConfig{
    
	protected Table<InetSocketAddress, String, Object> config=HashBasedTable.create();//table信息  table 信息 这里无需要用到 哪个ConcurrentHashMap<K, V> 因为这个只会加载读取 加载 
	
	
	protected BiMap<String,InetSocketAddress> alias=HashBiMap.create();
	
	protected Context context;
	
	

     


    public BiMap<String, InetSocketAddress> getAlias() {
		return alias;
	}




	/**
     * context 需要提供当前主机 以及 
     * @param context
     */
	public AbstractConfig(Context context) {
		super();
		this.context = context;
		
		wrapConfig(ConfigFactory.load(context.getDefaultFc()));
	}

	/**
     * context 需要提供当前主机 以及 
     * @param context
     */
	public AbstractConfig(Context context,ConfigBuilder builder) {
		super();
		this.context = context;
		
		wrapConfig(builder);
	}


	protected abstract void wrapConfig(ConfigBuilder builder);




	@Override
	public void setContext(Context context) {
		this.context=context;
		
	}




	@Override
	public Context getContext() {
		
		return context;
	}




	@Override
	public void readConfigFormFile(String file) {
		Config config=TypeSafeConfigLoadUtils.loadFromFile(new File(file));
		wrapConfig(config);
		
	}




	@Override
	public void readConfigFormString(String json) {
		Config config=TypeSafeConfigLoadUtils.loadFromString(json);
		wrapConfig(config);
	}
	/**
	 * 对config进行初始化 table
	 * @param config
	 * 添加（修改）人：zhuyuping
	 */
	protected abstract void wrapConfig(Config config);
	/**
	 * 把table 从内存中读取从新写入到配置文件中 
	 * @param config
	 * 添加（修改）人：zhuyuping
	 */
	protected abstract String wrapTable(Table<String, String, Object> config);
	
	
	
	
	
    @Override
	public Table<InetSocketAddress, String, Object> getConfig() {
		
		return config;
	}


	
	/**
	 * 
	 * 提交对配置的修改 如果一个人在一个节点上 更改了配置 需要核对版本 并从新更新本地的配置文件
	 * 添加（修改）人：zhuyuping
	 */
	protected abstract void sync();
    
}
