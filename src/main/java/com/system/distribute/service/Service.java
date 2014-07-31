package com.system.distribute.service;

import java.net.InetSocketAddress;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.system.distribute.config.IConfig;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 下午12:56:04 
 * @function:把 一些 常用的网络通讯作为基础 通过其他客服端 驱动 来进行基本访问的 功能  封装成服务 交由 服务来解析 
 */
public interface Service {
    /**
     * 启动服务
     * 
     * 添加（修改）人：zhuyuping
     */
	void start();
	
	/**
	 * 关闭服务 
	 * 
	 * 添加（修改）人：zhuyuping
	 */
	void stop(); 
	
	/**
	 * 服务的名称
	 * @return
	 * 添加（修改）人：zhuyuping
	 */
	String getName();
	
	
	void setName(String name);
	/**
	 * 获得配置 
	 * @return
	 * 添加（修改）人：zhuyuping
	 */
	IConfig getConfig();
	/**
	 * 注入配置
	 */
	void setConfig(IConfig config);
    /**
     * 往主机中 新增文件
     * @param address
     * @param build
     * 添加（修改）人：zhuyuping
     */
	void insert(List<InetSocketAddress> address,
			ImmutableMap<String, Object> build);
    /**
     * 删除文件 或文件夹
     * @param address
     * @param build
     * 添加（修改）人：zhuyuping
     */
	void delete(List<InetSocketAddress> address,
			ImmutableMap<String, Object> build);
    /**
     * 同步文件或者文件夹 如果用户没有传递条件 那么默认为同步区域
     * @param address
     * @param build
     * 添加（修改）人：zhuyuping
     */
	void sync(List<InetSocketAddress> address,
			ImmutableMap<String, Object> build);
}
