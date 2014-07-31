package com.system.distribute.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

import java.util.List;

import com.google.common.collect.Lists;
import com.system.common.ChannelType;

import com.system.distribute.client.UDPClient;
import com.system.distribute.config.IConfig;
import com.system.distribute.file.monitor.FileDataMessage;
import com.system.distribute.server.UDPServer;




@Deprecated
public abstract class CopyOfNetWorkService implements Service{

	
	protected IConfig config;//配置文件
	
	
    private UDPClient client;//这里 后面要改为组播方式 
	private UDPServer server;
	
	protected String name;
	
	


	
	public void start() {
		//初始化 基本  比如常见的加过滤器 handler过滤连 转换器都在这里放初始化
		//client=new UDPClient(ChannelType.NIO, ); 这是远程地址 
		//客服端采用多播 client 需初始化组  以后可能会换成组播
		client=new UDPClient(ChannelType.NIO, new InetSocketAddress(0),1);
		client.start();
		server=new UDPServer(ChannelType.NIO, config.getContext().getCurrHost().getPort());
		server.start();
		//交给用户后面服务初始化
		start();
	}


	
	public void stop() {
	   //停止基本
		client.stop();
		server.stop();
	  //交给用户后面服务停止
		stop();
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
	
	public void send(){
		
	}
	
	public void receive(){
		
		
	}

    /**
     * 往某一台主机上插入文件或目录
     * @param path
     * @param host
     * @param isdir
     * 添加（修改）人：zhuyuping
     */
	public void insert(File path, InetSocketAddress host, boolean isdir) {
		if(isdir){
			//获得目录下所有文件 
			File[] files=path.listFiles();
			for (File file : files) {
				if(file.length()>1024*1024*30){//大于50M
					List<FileDataMessage> fms=wrapSplitFile(file,10*1024);
				}else{
					
					FileDataMessage  fms=wrapFile(file);
				}
				
			}
		}else{
			
			//上传文件 这里可以直接使用zerocopy netty底层也是使用了他 
			//FileChannel.transferTo(0, fc.size(), new InetSocketAddress(xx,yy));;
			
			
		}
		
	}
	/**
	 * 将文件打包成消息协议
	 * @param file
	 * @return
	 * 添加（修改）人：zhuyuping
	 */
    private FileDataMessage wrapFile(File file) {
		
		return null;
	}


	/**
     * 对大文件进行分割
     * @param file
     * @return
     * 添加（修改）人：zhuyuping
     */
    private List<FileDataMessage> wrapSplitFile(File file,int size) {
    	long numBlocks = file.length() / size;
		int tmp = 0;
		
		List<FileDataMessage> blocks =Lists.newArrayListWithExpectedSize((int) numBlocks + 1);
		try {
			byte[] buffer = new byte[size];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			
			while((tmp = bis.read(buffer)) > 0) {
				//blocks.add(buffer);
				if(tmp != size)
					break;
				else
					buffer = new byte[size];
			}
			bis.close();
			
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return null;
	}


	/**
     * 叫某一台主机删除文件或目录
     * @param path
     * @param host
     * @param isdir
     * 添加（修改）人：zhuyuping
     */
	public void delete(File path, InetSocketAddress host, boolean isdir) {
		
		
	}

    /**
     * 某一台主机电脑上文件目录更新 
     * @param path
     * @param host
     * @param isdir
     * 添加（修改）人：zhuyuping
     */
	public void update(File path, InetSocketAddress host, boolean isdir) {
		
		
	}
	
	
}
