package com.system.distribute.service;


import io.netty.channel.Channel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.common.io.InputSupplier;

import com.system.common.ChannelType;
import com.system.common.InfiniteLoopDaemon;
import com.system.distribute.client.BIOClient;
import com.system.distribute.client.TCPClient;
import com.system.distribute.config.IConfig;
import com.system.distribute.core.Node;
import com.system.distribute.core.NodeFactory;
import com.system.distribute.file.FileSummary;
import com.system.distribute.file.FileSystem;
import com.system.distribute.file.helper.HttpUploadServer;
import com.system.distribute.file.monitor.FileDataMessage;

import com.system.distribute.server.TCPServer;






public abstract class NetWorkService implements Service{

	
	protected IConfig config;//配置文件
	
	//private  TCPServer server;//tcp服务端
    //protected  BIOServer  server;
	
	//protected  BIOClient client;
	
	private Node node;
	
	
	
	public NetWorkService(Node node) {
		super();
		this.node = node;
	}


	protected String name;
	
	private Thread thread=null;
	
	@Override
	public void start(){
		this.dostart();
		init();
	}
	//交给后面的子类去 初始化相关内容
	
	protected abstract void init();


	
	public void dostart(){
		//启动tcp服务端
		
		//server=new TCPServer(ChannelType.NIO,  config.getContext().getCurrHost());//自身是服务器
		//server.start();
		try{
			//异步不能阻塞
			
		  thread=new Thread(new HttpUploadServer(config.getContext().getCurrHost().getPort(),new NodeFactory(config).createNode(FileSystem.class)));
		  thread.start();
		  
		}catch(Exception e){
			
			throw new RuntimeException("服务器启动失败 请查看端口是否占用");
		}
//		Thread thread=new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				logger.info("----------服务器正在接受----------------");
//				try {
//					//server.Accept(config.getContext().getCurrHost().getPort());
//					//server.recevie(config.getContext().getCurrHost().getPort());
//				} catch (Exception e) {
//					
//					e.printStackTrace();
//				}
//				
//			}
//		});
//					
//		thread.setDaemon(true);
//		thread.start();
					
		
		
		
	}



	public void dostop() {
	   //停止基本
	  if(thread!=null){
		  if(thread.isInterrupted()){
			  
			  thread.interrupt();
		  }
		  
	  }
	  //交给用户后面服务停止
		
	}


	@Override
	public String getName() {
	
		return name;
	}


	@Override
	public void setName(String name) {
		this.name=name;
		
	}


	@Override
	public IConfig getConfig() {
		
		return config;
	}


	@Override
	public void setConfig(IConfig config) {
		
		this.config=config;
	}
	
	

    
	
	
}
