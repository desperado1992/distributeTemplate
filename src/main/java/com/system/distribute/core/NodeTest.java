 package com.system.distribute.core;

import java.net.InetSocketAddress;
import java.util.List;

import com.system.distribute.config.SimpleDistributeConfig;
import com.system.distribute.context.AbstractContext;
import com.system.distribute.context.Context;

import com.system.distribute.file.FileAdapter;
import com.system.distribute.file.FileSystem;
import com.system.distribute.file.FileSystemManager;
import com.system.distribute.service.FileSystemService;
import com.system.distribute.sqlparser.Query;

public class NodeTest {

	public static void main(String[] args) throws Exception {
       
		SimpleDistributeConfig config=new SimpleDistributeConfig(new AbstractContext(){
			
		});
		NodeFactory nodeFactory=new NodeFactory(config);
		NodeManager manager=new FileSystemManager(nodeFactory,new FileSystemService(nodeFactory));
		DistributedTemplate template=new DistributedTemplate(manager);
		//template.setNodeManager(manager);
		template.insert("insert into h2 values(F:/html20131128/hostel)", new FileAdapter());
		//template.deleteFile("delete from h2 where name=d:/upload/zhuyuping.jpg");
		//template.initContext(context);
		//context.setTemplate(distributedTemplate)
		//template.query(\, callback, adapter)
		
		//manager.stopService(); 这是停止服务
	}
	
	
}
