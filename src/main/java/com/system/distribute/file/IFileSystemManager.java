package com.system.distribute.file;

import java.io.File;
import java.io.OutputStream;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-10 下午5:42:23 
 * @function:这就是文件系统 分布式的接口 维护信息 采用工厂方式创建 后期可以直接使用spring 注入 提供很多对外节后
 */
public interface IFileSystemManager {

	/**
	 * 查询分布式系统上文件或者目录是否存在
	 * @param path
	 * 添加（修改）人：zhuyuping
	 */
	public boolean  exist(String path);
	
	/**
	 * 通过类似sql 语法进行 查询sql 删除delete 同步update
	 * @param sql
	 * @param callback
	 * @return
	 * 添加（修改）人：zhuyuping
	 */
	public <T> T  querybySQL(String sql,FileCallback<T> callback);
	
	/**
	 * 统计当前所有 被管理的节点 
	 * @param path
	 * 添加（修改）人：zhuyuping
	 */
	public int count();
	/**
	 * 上传文件到当前配置文件目录下（所有节点之间同步）
	 * 
	 * @param path
	 * @return
	 * 添加（修改）人：zhuyuping
	 */
	public boolean upload(File file);
	//还需要提供 文件名 流 的接口
	public Boolean upload(String path);
	/**
	 * 下载文件 从各个分布式节点上查找 如果 文件在一个节点上没有 一次逐一查找 发现该文件 
	 * @param path
	 * @return
	 * 添加（修改）人：zhuyuping
	 */
	public OutputStream downLoad(String path);
	/**
	 * 同步分布式系统的文件目录
	 * 
	 * 添加（修改）人：zhuyuping
	 */
	public void sync();
	
	
}
