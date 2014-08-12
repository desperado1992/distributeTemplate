package com.system.distribute.config;



import java.io.File;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.Table;
import com.system.distribute.config.ServiceConfig.ServiceType;
import com.system.distribute.context.AbstractContext;
import com.system.distribute.context.Context;
import com.system.distribute.core.DistributedOperations;

import com.system.distribute.file.FileSystem;
import com.system.distribute.file.FileSystemManager;
import com.system.distribute.service.FileSystemService;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;


/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 下午10:38:05 
 * @function:默认的配置  允许用户实现自定义的配置规则 只需要继承 AbstractConfig
 */
public class SimpleDistributeConfig extends AbstractConfig{
	public SimpleDistributeConfig(Context context) {
		super(context);
		
	}
	
	public SimpleDistributeConfig(Context context,ConfigBuilder builder) {
		super(context,builder);
	}
  
	
	
	

	

	@Override
	protected void wrapConfig(Config configObj) {
		//得到所有的节点
		List<? extends ConfigObject> nodes=configObj.getObjectList("server.hosts");
		int i=0;
		for (ConfigObject node : nodes) {
	
		   i++;
		   //如果后期添加其他mysql 等支持 这里就需要添加判断
		  
		   //Integer localport=node.containsKey("localPort")?Integer.parseInt(node.get("localPort").render()):LOCALPORT;//Integer.parseInt(node.get("localPort").render());
		   Integer remotePort=Integer.parseInt(node.get("remotePort").render());
		   String remoteIp=node.get("remoteHost").unwrapped().toString();//远程主机的ip
		   //开始初始化配置table
		   String name=node.containsKey("name")?node.get("name").unwrapped().toString():remoteIp+":"+remotePort;//主机别名
		   InetSocketAddress remoteHost=new InetSocketAddress(remoteIp, remotePort);
		   super.alias.put(name, remoteHost);
		  
		   super.config.put(remoteHost, "remoteHost", remoteHost);
		   //super.config.put(remoteHost, "localPort", localport);
		   super.config.put(remoteHost, "remotePort", remotePort);
		   super.config.put(remoteHost, "name", name);
		   
		 
		   if(node.containsKey("file")){
			   HashMap fcs=(HashMap) node.get("file").unwrapped();
			   //String syncPath=fcs.get("syncPath").toString();//文件同步的路径
			  // System.out.println("SimpleDistributeConfig.wrapConfig() "+syncPath);
			   super.config.put(remoteHost, ServiceType.FILE.name(), fcs);//以后配置多的时候 分装成一个bean存入
		   }
		   
		   
		}
		String chost=configObj.getString("client.currentHost");
		int port=configObj.getInt("client.currentPort");
		super.context.setCurrHost(chost, port);
		
		//config.root().containsKey(key)
		//config.getString(path);
	}
	
	
	@Override
	protected String wrapTable(Table<String, String, Object> table) {
		StringBuilder sb=new StringBuilder("server{");
		
		sb.append("\n\t").append("hosts=[");
		Set<String> rows=table.rowKeySet();
		int size=rows.size();
		int i=0;
		for (String row : rows) {
			i++;
			Map<String,Object> map=table.row(row);
			if(!map.containsKey("remoteHost")) continue;
			sb.append("\t {");
			Object remoteHost=map.get("remoteHost");	
			Object remotePort=map.get("remotePort");
			//Object localPort=map.get("localPort");
			Object name=map.get("name");
			sb.append("name=").append(name).append("\n\t");
			//sb.append("localPort=").append(localPort).append("\n\t");
			sb.append("remoteHost=").append(remoteHost).append("\n\t");
			sb.append("remotePort=").append(remotePort).append("\n\t");
		    if(map.containsKey("file")){
		    	sb.append("file{").append("\n\t").append("syncPath=").append(map.get("syncPath")).append("}");
		    }
		   
		    sb.append("\t }");
		    if(i!=size){
		    	sb.append(",");
		    }
		}
	   sb.append("]").append("\n\t").append("}");	
	  //继续 保存client
	  
	   sb.append("\n\t").append("client{").append("\n\t").append("currentHost=").append(context.getCurrHost().getHostString()).append("\n\t");
	   sb.append("\n\t").append("currentPort=").append(context.getCurrHost().getPort()).append("\n\t");
	   sb.append("}");
	   
	   return sb.toString();
		
	}

	

	@Override
	protected void sync() {
		
		
	}

	

	@Override
	protected void wrapConfig(ConfigBuilder builder) {
		this.alias=builder.alias;
		builder.wrapTable(super.config,super.context);
		
	}

}
