package com.system.distribute.service;



import io.netty.channel.Channel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Set;

import java.util.concurrent.ForkJoinPool;

import java.util.zip.Adler32;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.common.io.InputSupplier;

import com.system.distribute.core.Node;
import com.system.distribute.core.NodeFactory;
import com.system.distribute.file.FileSummary;
import com.system.distribute.file.FileSyncTask;
import com.system.distribute.file.FileSystem;
import com.system.distribute.file.helper.ServerHelper;
import com.system.distribute.file.monitor.FileDataMessage;

public class FileSystemService extends NetWorkService{
	
   
	private ForkJoinPool pool=null;
    /**
     * 所有节点 需要便利的
     */
	private List<InetSocketAddress> hosts=Lists.newCopyOnWriteArrayList();
	
    public FileSystemService(NodeFactory nodeFactory) {
		super(nodeFactory.createNode(FileSystem.class));
	}
	@Override
	public void init() {
		//启动文件同步服务系需要的资源
		//初始化所有节点信息 同时需要快速找到
		pool=new ForkJoinPool(Runtime.getRuntime().availableProcessors()*2);
		Set<InetSocketAddress> rows=getConfig().getConfig().rowKeySet();
		List<InetSocketAddress> sockets=Lists.newArrayListWithCapacity(rows.size());
		for (InetSocketAddress row : rows) {
			if(row.equals(getConfig().getContext().getCurrHost())) continue;
			
			//排除当前的主机
			sockets.add(row);
		}
		hosts.addAll(sockets);
		
	}
    //需要别名转换 
	public String getHost(String hostName){
		for (InetSocketAddress socket : hosts) {
			if(socket.getHostName().equalsIgnoreCase(hostName)){
				return hostName;
			}
		}
		return null;
		
	}
	
	@Override
	public void stop() {
		//停止
		pool=null;
		hosts=Lists.newCopyOnWriteArrayList();
		dostop();
	}
    /**
     * 往所有节点里面插入文件或目录
     * @param path
     * @param isdir  以后为了性能可能需要添加组播 的方式
     * 添加（修改）人：zhuyuping
     * @throws Exception 
     */
	public void insert(String path){
		insert(hosts, ImmutableMap.<String,Object>of("name", path));
	}
    private void checkPool() {
    	 if (pool == null) {
    		 //双检查
             synchronized (ForkJoinPool.class) {
                 if (pool == null) {
                     // when short time async task, use cachedThreadPool.
                     pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors()*2);
                 }
             }
         } else {
             if (pool.isShutdown()) {
                 synchronized (ForkJoinPool.class) {
                     if (pool.isShutdown()) {
                    	 pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors()*2);
                     }
                 }
             }
         }
    	
		
	}
	/**
     * 删除所有节点上的该文件或目录
     * @param path
     * @param isdir
     * 添加（修改）人：zhuyuping
     * @throws Exception 
     */
	public void delete(String path) {
		
		delete(hosts, ImmutableMap.<String,Object>of("name", path));
		
		
	}
    /**
     * 更新 替换掉该文件或目录 先删除 再插入
     * @param path
     * @param isdir
     * 添加（修改）人：zhuyuping
     * @throws Exception 
     */
	public void update(File path) {
		//后面可能的话 只更新 那变了的部分 rsync
		
		for (InetSocketAddress host : hosts) {
			if(ServerHelper.listening(host)){
				//说明远程主机连上了 没有连上的 把它放到死信 日志文件中 下次重启服务器时候 进行日志逐一同步 
				FileSyncTask task=new FileSyncTask(host,path);
				//task.join() 获得失败的节点 写入 死信日志(长度大了 打包zip)  from   to     path   date
				pool.submit(task);
			}else{
				//写入 同步 死信日志 
				//TODO 
				
			}
			
		}
		
		
	}
   
	
	
	public void sendDir(File file,Channel channel){
		if(file.isDirectory()){
			File[] files=file.listFiles();
			for (File f : files) {
				sendDir(f, channel);
			}
		}else{
			if(file.length()>1024*1024*30){//大于50M
				List<FileDataMessage> fms=wrapSplitFile(file,10*1024*1024);
				for (FileDataMessage fileDataMessage : fms) {
					
					
					channel.writeAndFlush(fileDataMessage);
				}
				
			}else{
				
				FileDataMessage  fms=wrapFile(file);
			
				channel.writeAndFlush(fms);
			}
			
		}
		
	}
	/**
	 * 将文件打包成消息协议
	 * @param file
	 * @return
	 * 添加（修改）人：zhuyuping
	 * @throws Exception 
	 * @throws FileNotFoundException 
	 */
    private FileDataMessage wrapFile(File file){
		FileDataMessage fm=new FileDataMessage();
		try{
		final InputStream input=new FileInputStream(file);
		fm.setBytes(ByteStreams.toByteArray(input));
		fm.setSessionId(System.identityHashCode(file.getAbsolutePath()));
		fm.setHead(1314);
		fm.setCommand(1);
		fm.setLenth(file.length());
		fm.setPath(file.getAbsolutePath());
		fm.setStart(0);
		fm.setEnd(file.length());
		fm.setHash(ByteStreams.getChecksum(new InputSupplier<InputStream>() {

			@Override
			public InputStream getInput() throws IOException {
				
				return input;
			}
		}, new Adler32()));//crc32最强验证 adl32最弱验证
		}catch(Exception e){
			
		}
		return fm;
	}


	/**
     * 对大文件进行分割
     * @param file
     * @return
     * 添加（修改）人：zhuyuping
     */
    private List<FileDataMessage> wrapSplitFile(File file,int size) {
    	long numBlocks = file.length() / size;
    	long blocks=file.length()%size==0?numBlocks:numBlocks+1;
		int tmp = 0;
		
		List<FileDataMessage> bls =Lists.newArrayListWithExpectedSize((int) blocks);
		try {
			 FileDataMessage fm=null;
			byte[] buffer = new byte[size];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			int i=0;
			while((tmp = bis.read(buffer)) > 0) {
				i++;
				fm=new FileDataMessage();
				fm.setBytes(buffer);//0 -- sizwe-1  size size*2-1  @*size
				fm.setStart(size*(i-1));
				fm.setPath(file.getAbsolutePath());
				fm.setSessionId(System.identityHashCode(file.getAbsolutePath()));
				fm.setHead(1314);
				fm.setCommand(1);
				fm.setLenth(file.length());
				fm.setPath(file.getAbsolutePath());
				fm.setHash(FileSummary.computeAdler32(buffer,tmp));
				
				if(tmp != size){
					fm.setEnd(file.length()-1);
					bls.add(fm);
					break;
					
				}else{
					buffer = new byte[size];
					fm.setEnd(size*i-1);
					bls.add(fm);
				}
				
			}
			bis.close();
			
		} catch (IOException e) {
				
				e.printStackTrace();
		}
		return bls;
	}


	/**
     * 包裹删除消息
     * @param path
     * @param host
     * @param isdir
     * 添加（修改）人：zhuyuping
     */
	public void wrapdelete(File path) {
		System.out.println("NetWorkService.delete()");
		//Channel client=TCPClient.getChannel(ChannelType.NIO, host);
		
		
		FileDataMessage fm=new FileDataMessage();
		fm.setSessionId(System.identityHashCode(path.getAbsolutePath()));
		fm.setHead(1314);
		fm.setCommand(0);
		fm.setLenth(path.length());
		fm.setPath(path.getAbsolutePath());
		fm.setStart(0);
		fm.setEnd(path.length());
		
		//client.writeAndFlush(client);
	}
	@Override
	public void insert(List<InetSocketAddress> address,
			ImmutableMap<String, Object> build) {
		for (InetSocketAddress host : address) {
			if(ServerHelper.listening(host)){
				//说明远程主机连上了 没有连上的 把它放到死信 日志文件中 下次重启服务器时候 进行日志逐一同步 
				for (java.util.Map.Entry<String, Object> entry : build.entrySet()) {
					if(entry.getKey().equalsIgnoreCase("name")){
						//todo 这里要考虑到 以后 用户提供的是不完整的 只是文件名 或者 文件不可读 等等其他情况 
						FileSyncTask task=new FileSyncTask(host, new File(String.valueOf(entry.getValue())));
						pool.submit(task);
					}
				}
				
				
				//task.join() 获得失败的节点 写入 死信日志(长度大了 打包zip)  from   to     path   date
				
			}else{
				//写入 同步 死信日志 
				//TODO 
				
			}
		}
		
	}
	@Override
	public void delete(List<InetSocketAddress> address,
			ImmutableMap<String, Object> build) {
	
		for (InetSocketAddress host : address) {
			
			if(ServerHelper.listening(host)){
				//说明远程主机连上了 没有连上的 把它放到死信 日志文件中 下次重启服务器时候 进行日志逐一同步 
				for (java.util.Map.Entry<String, Object> entry : build.entrySet()) {
					if(entry.getKey().equalsIgnoreCase("name")){
						//todo 这里要考虑到 以后 用户提供的是不完整的 只是文件名 或者 文件不可读 等等其他情况 
						
						FileSyncTask task=new FileSyncTask(host, new File(String.valueOf(entry.getValue())),true);
						pool.submit(task);
					}
				}
				
				
				//task.join() 获得失败的节点 写入 死信日志(长度大了 打包zip)  from   to     path   date
				
			}else{
				//写入 同步 死信日志 
				//TODO 
				System.out.println("远程服务器无法连接 "+host);
			}
		}
		
	}
	@Override
	public void sync(List<InetSocketAddress> address,
			ImmutableMap<String, Object> build) {
		
		
	}

   
	
	

	

	
	
	
	
}
