package com.system.distribute.config;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-28 下午2:45:21 
 * @function:服务的实体信息 比如 文件 mysql 等等所需要的配置信息
 */
public class ServiceConfig {

	
	public static enum ServiceType{
		FILE,//暂时只有实现文件服务
		MYSQL,
		MONGODB,
		MEMCACHE,
		REDIS;
	}
	
	private ServiceType serviceType;//服务的
	
	private Map<String,Object> info=Maps.newConcurrentMap();//信息 key --- value 

	

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}

	public ServiceConfig() {
		super();
		
	}

	@Override
	public String toString() {
		return "ServiceConfig [serviceType=" + serviceType + ", info=" + info
				+ "]";
	}
	
	
	
}
