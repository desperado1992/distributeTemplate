package com.system.distribute.file.monitor;

import java.nio.file.Paths;
import java.util.Map;

import com.system.distribute.config.SimpleDistributeConfig;
import com.system.distribute.context.AbstractContext;
import com.system.distribute.context.Context;

import com.system.distribute.file.FileSystem;
import com.system.distribute.file.FileSystemManager;
import com.system.distribute.service.FileSystemService;


public class MonitorTest {

	
	public static void main(String[] args) throws Exception {
//		Context context=new AbstractContext() {
//			
//		};
//		SimpleDistributeConfig config=new SimpleDistributeConfig(context);
//		FileSystem fileSystem=new FileSystem(config);
//		
////		for (Map.Entry<String, FNode> entry : fileSystem.getNodes().entrySet()) {
////			String key=entry.getKey();
////			FNode fnode=entry.getValue();
////			
////		}
//		//FileSystem 后面要基层Node 采用NodeFactory来创建 这里要实现自己的NodeFactory 以及使用FileSystemService
//		//FileSystemManager要基层AbstractNodeManger 
//		FileSystemService fileSystemService=new FileSystemService();
//		FileSystemManager fsm=new FileSystemManager(fileSystem,fileSystemService);
	    
		//fsm上的各种查询方法
		
		
		//TODO 
		
		//Thread.sleep(10000);
		//monitor.stop();
	  //  FileMonitor fmonitor=new FileMonitorImpl(new FileMonitorEventListenerImpl());
	    //Path path=Thread.currentThread().getContextClassLoader().getResource("/").getPath();
	   // System.out.println("MonitorTest.main() ");//MonitorTest.class.getClassLoader().getResource("distribute.conf").getPath().substring(1)
//	    fmonitor.add(Paths.get("c:/distribute.conf"));
//	    fmonitor.start();
	    while(true){
	    	
	    	Thread.sleep(10000);
	    }
	    
	}
	

	
}
