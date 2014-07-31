package com.system.distribute.config;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.system.distribute.config.ServiceConfig.ServiceType;
import com.system.distribute.context.Context;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 下午5:44:07 
 * @function: 通过链式编程 提供给用户一种 快捷的产生配置的方式
 */
public  class ConfigBuilder {

	private static ConfigBuilder configBuilder=new ConfigBuilder();
	//主机列表
	private List<InetSocketAddress> address=Lists.newArrayList();
	
	private InetSocketAddress currentHost=null;
	
	private List<ServiceConfig> services=Lists.newArrayList();
	//别名
	protected BiMap<String,InetSocketAddress> alias=HashBiMap.create();
	
	private String configFile;
	
    public ConfigBuilder() {
    	configFile=Context.defaultConfig;
    	Config config=ConfigFactory.load(Context.defaultConfig);
    	initConfig(config);
    }

	private void initConfig(Config config) {
		List<? extends ConfigObject> nodes=config.getObjectList("server.hosts");
    	for (ConfigObject node : nodes) {
    		  Integer remotePort=Integer.parseInt(node.get("remotePort").render());
   		      String remoteIp=node.get("remoteHost").unwrapped().toString();//远程主机的ip
   		      String name=node.containsKey("name")?node.get("name").unwrapped().toString():remoteIp;//主机别名
   		      InetSocketAddress host=new InetSocketAddress(remoteIp, remotePort);
   		      address.add(host);
    	      alias.put(name, host);
    	      //TODO 获取
    	      for(ServiceType serviceType : ServiceConfig.ServiceType.values()){
    	    	   String serviceName=serviceType.name().toLowerCase();
    	    	  if(node.containsKey(serviceName)){
    	    		  HashMap fcs=(HashMap) node.get(serviceName).unwrapped();
    	    		  ServiceConfig serviceConfig=new ServiceConfig();
    	    		  serviceConfig.setServiceType(serviceType);
    	    		  serviceConfig.setInfo(fcs);
    	    		  services.add(serviceConfig);
    	    	  }
    	      }
    	     
    	}
    	String chost=config.getString("client.currentHost");
		int port=config.getInt("client.currentPort");
		currentHost=new InetSocketAddress(chost, port);
	}
	
	public ConfigBuilder(String configFile) {
		this.configFile=configFile;
		Config config=ConfigFactory.load(configFile);
		initConfig(config);
	}
	public static ConfigBuilder getInstance(){
		if(configBuilder==null){
			return new ConfigBuilder();
		}else{
			return configBuilder;
		}
		
	}
	public static ConfigBuilder getInstance(String configFile){
		if(configBuilder==null){
			return new ConfigBuilder(configFile);
		}else{
			return configBuilder;
		}
		
	}
	/**
	 * 添加服务器主机
	 * @param host
	 * @param port
	 * @return
	 * 添加（修改）人：zhuyuping
	 */
	public ConfigBuilder addHost(String host,int port){
		InetSocketAddress h=new InetSocketAddress(host, port);
		address.add(h);
		alias.put(host+":"+port, h);
		return this;
	}
	private boolean containsHost(String host) {
		for (InetSocketAddress addr : address) {
			if(addr.getHostString().contains(host)) return true;
		}
		
		return false;
	}

	/**
	 * 添加主机并设置 主机别名
	 * @param host
	 * @param port
	 * @param nname
	 * @return
	 * 添加（修改）人：zhuyuping
	 */
    public ConfigBuilder addHost(String host,int port,String nname){
	     
	      InetSocketAddress h=new InetSocketAddress(host, port);
		  address.add(h);
	      alias.put(nname, new InetSocketAddress(host, port));
		return this;
	}
    /**
     * 移除主机
     * @param host
     * @param port
     * @return
     * 添加（修改）人：zhuyuping
     */
    public ConfigBuilder removeHost(String host,int port){
    	InetSocketAddress h=new InetSocketAddress(host, port);
		alias.inverse().remove(h);
		address.remove(h);
		return this;
	}
    /**
     * 设置当前自身的主机名 与端口 
     * @param host
     * @param port
     * @return
     * 添加（修改）人：zhuyuping
     */
    public ConfigBuilder setSelf(String host,int port){
		currentHost=new InetSocketAddress(host, port);
		return this;
	}
    /**
     * 写入到配置文件中
     * 
     * 添加（修改）人：zhuyuping
     */
    public void buildToFile(){
    	
    	String path=Thread.currentThread().getContextClassLoader().getResource("")+configFile;
    	try {
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path), "UTF8"));
			StringBuilder sb=new StringBuilder("server{");
			sb.append("\n\t").append("hosts=[");
			int i=0;
			int size=address.size();
			for (InetSocketAddress host : address) {
				sb.append("\t {");
				sb.append("name=").append(alias.inverse().get(host)).append("\n\t");
				sb.append("remoteHost=").append(host.getHostString()).append("\n\t");
				sb.append("remotePort=").append(host.getPort()).append("\n\t");
				 sb.append("\t }");
				    if(i!=size){
				    	sb.append(",");
				    }
			}
			sb.append("]").append("\n\t").append("}");	
			  //继续 保存client
			  
			   sb.append("\n\t").append("client{").append("\n\t").append("currentHost=").append(currentHost.getHostString()).append("\n\t");
			   sb.append("\n\t").append("currentPort=").append(currentHost.getPort()).append("\n\t");
			   sb.append("}");
			   out.write(sb.toString());
		} catch (Exception e) {
		
		}
    	
    	
    }
    
   
	
    
	
	@Override
	public String toString() {
		return "ConfigBuilder [address=" + address + ", currentHost="
				+ currentHost + ", services=" + services + ", alias=" + alias
				+ ", configFile=" + configFile + "]";
	}

	public static void main(String[] args) {
		ConfigBuilder build=ConfigBuilder.getInstance();
		build.addHost("192.168.8.8", 1234).addHost("192.168.9.9", 2222, "test");
		System.out.println(build);
	}
    /**
     * 
     * @param table
     * @param context
     * 添加（修改）人：zhuyuping
     */
	public void wrapTable(Table<InetSocketAddress, String, Object> table,
			Context context) {
		for (InetSocketAddress addr : address) {
			table.put(addr, addr.getHostString(), addr.getPort());
			table.put(addr, "name", alias.inverse().get(addr));
			for (ServiceConfig serviceConfig : services) {
				table.put(addr, serviceConfig.getServiceType().name(), serviceConfig.getInfo());
			}
			
		}
		context.setCurrHost(currentHost);
		
		
	}
}
