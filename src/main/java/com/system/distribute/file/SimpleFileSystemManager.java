package com.system.distribute.file;

import java.io.File;
import java.io.OutputStream;

public interface SimpleFileSystemManager {

	/**
	 * 查询分布式系统上文件或者目录是否存在
	 * @param path
	 * 添加（修改）人：zhuyuping
	 */
	public boolean  exist(String path);
	/**
	 * 查询文件并返回所需要的实体
	 * @param path
	 * @param callback
	 * @return
	 * 添加（修改）人：zhuyuping
	 */
	public <T> T query(String path,FileCallback<T> callback);
	
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
	 * @return  该方法与query类似 只不过query 可以返回更多的信息 可以使用回调机制
	 * 添加（修改）人：zhuyuping
	 */
	public OutputStream downLoad(String path);
	/**
	 * 同步分布式系统的文件目录
	 * 
	 * 添加（修改）人：zhuyuping
	 */
	public void sync(String path);
	
}
