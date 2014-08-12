package com.system.distribute.file;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.sun.org.apache.xpath.internal.functions.Function2Args;
import com.system.distribute.core.Adapter;
import com.system.distribute.core.NodeManager;
import com.system.distribute.core.ResultSet;
import com.system.distribute.sqlparser.BaseQuery;
import com.system.distribute.sqlparser.Condition;
import com.system.distribute.sqlparser.Condition.Relative;
import com.system.distribute.sqlparser.Query;
/**
 * 
 *      
 *TODO 需要代码抽出    用户只要只需要实现adapter方法  这里还没有进行优化 
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-22 下午5:38:31 
 * @function:
 */
public class FileAdapter implements Adapter{

	private FileSystemManager fileSystemManager;
	
	
	private static final Pattern IPV4_PATTERN = 
	        Pattern.compile(
	                "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}");
	
	@Override
	public void setNodeManager(NodeManager nodeManager) {
		
		this.fileSystemManager=(FileSystemManager) nodeManager;
		//这里所谓的转换其实 因为service 接口本来传递过来的就是子类
	    
		
	}
	
	public static boolean isIPv4Address(final String input) {
	        return IPV4_PATTERN.matcher(input).matches();
	 }

	@Override
	public ResultSet adapter(Query quy) throws Exception{
         
         List<InetSocketAddress> address=getOperHosts(quy);
         //List<String> needs=query.getNeedvalues();
        // Multimap<InetSocketAddress,String> fields=needFields(needs);
		return fileSystemManager.loop(address,quy);
	}

	private Multimap<InetSocketAddress,String>  needFields(List<String> needs) {
		Multimap<InetSocketAddress,String> map=ArrayListMultimap.create();
         for (String need : needs) {
        	 if(need.contains(".")){
		    	String[] strs=need.split(".");
		    	
		    	map.put(tranform(strs[0]),strs[1]);
		     }	
		}
         return map;
	}

	private List<InetSocketAddress> getOperHosts(Query query) {
		List<String> opers=query.getOperations();
		List<InetSocketAddress> address=Lists.newArrayList();
         for (String oper : opers) {
        	
			if(isIPv4Address(oper)){
				//不是ip表示 需要转为ipv4的表示 192.168.0.1
				if(oper.contains(":")){
					String[] strs=oper.split(":");
				 InetSocketAddress add=new InetSocketAddress(strs[0], Integer.valueOf(strs[1]));
				address.add(add);
				}else{
				  
					List<InetSocketAddress> inets=findAddress(oper);
				    address.addAll(inets);
				}
				
			}else{
				
				InetSocketAddress host=tranform(oper);
				address.add(host);
			}
		 }
         
         return address;
	}

	private List<InetSocketAddress> findAddress(String oper) {
		List<InetSocketAddress> hosts=Lists.newArrayList();
		Set<InetSocketAddress> inets=fileSystemManager.getcNode().getConfig().getConfig().rowKeySet();
		for (InetSocketAddress inetSocketAddress : inets) {
			if(inetSocketAddress.toString().contains(oper)){
				hosts.add(inetSocketAddress);
			}
		}
		return hosts;
	}

	private InetSocketAddress tranform(String oper) {
		BiMap<String,InetSocketAddress> bmaps=fileSystemManager.getcNode().getConfig().getAlias();
		return bmaps.get(oper);
	}

}
