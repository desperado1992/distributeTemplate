package com.system.distribute.core;

import com.system.distribute.config.IConfig;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 下午12:31:27 
 * @function:分布式通讯的会话 信息 类似context上下文 但是 需注入 SiteConfig 同步维护以及事件坚挺
 */
public interface Session {
    /**
     * 获得当前的配置文件
     * @return
     * 添加（修改）人：zhuyuping
     */
	public IConfig getCurrentConfig();
	/**
	 * 在分布式上维护的 信息
	 * @param key
	 * @return
	 * 添加（修改）人：zhuyuping
	 */
	public String getValue(String key);
}
