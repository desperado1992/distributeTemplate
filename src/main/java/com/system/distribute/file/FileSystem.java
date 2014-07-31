package com.system.distribute.file;



import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

import java.util.concurrent.ForkJoinPool;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;

import com.system.common.InfiniteLoopDaemon;
import com.system.distribute.config.IConfig;
import com.system.distribute.config.ServiceConfig;
import com.system.distribute.context.Context;
import com.system.distribute.core.Event;
import com.system.distribute.core.Node;
import com.system.distribute.core.NodeManager;

import com.system.distribute.file.helper.ServerHelper;

import com.system.distribute.file.monitor.FileEvent;
import com.system.distribute.file.monitor.FileEventHandler;
import com.system.distribute.service.FileSystemService;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-10 下午5:40:53 
 * @function:这就是文件系统 分布式的实体 维护信息 采用工厂方式创建 后期可以直接使用spring 注入 提供很多对外节后
 */
//extends  implements IFileSystem
public class FileSystem implements Node{
	
    public final static String NAME="distribute"+FileSystem.class.getSimpleName();
    /**
     * 本地文件系统的所有节点表 
     */
    Map<String,FNode> nodes=Maps.newConcurrentMap();
    
    InfiniteLoopDaemon thread=null;
    
    Object obj=null;
    
    /**
     * 本地文件监听的时间处理对象 为什么用并发容器是因为本地可能运行多个，端口不一样的 所以存在并发情况 否则直接ArrayList
     *  可以用synchronized 或者 new ReentrantLock() 来控制 应该性能差不多，容器底层也是它
     */
   // private ConcurrentLinkedQueue listener=Queues.newConcurrentLinkedQueue();
    //采用transferQuer 以保证完整的FIFO 先进先出的队列
    private LinkedTransferQueue listener=new LinkedTransferQueue();
    
    //这里由于 我们不使用rsync做进一步处理 所以 不需要进一步生存futrue 分割的结果
    //private ConcurrentLinkedQueue<Future<FileDataMessage>> futures=Queues.newConcurrentLinkedQueue();
    
    private IConfig config;
    
	public  Map<String, FNode> getNodes() {
		return nodes;
	}
    

	public void setNodes(Map<String, FNode> nodes) {
		this.nodes = nodes;
	}

    @Override
	public IConfig getConfig() {
		return config;
	}

    @Override
	public void setConfig(IConfig config) {
		this.config = config;
	}


	public FileSystem(IConfig config) {
		super();
		this.config = config;
		if(!init()){
		   throw new RuntimeException("配置文件无法初始化！");	
		}
		
	}
	
	
    //文件系统初始化
	private boolean init() {
		//然后使用config 初始化 nodes
		//初始化nodes
		Context context=config.getContext();
		
	   Object fileprop=config.getConfig().get(context.getCurrHost(), ServiceConfig.ServiceType.FILE.name());

		if(fileprop!=null){
		//注入到context上下文中 
		Map<String,Object> map=(Map<String, Object>) fileprop;	
		String path=ServerHelper.gennerFilePath(String.valueOf(map.get("syncPath")));
		context.putValue("filesync",path);
		//获取其他文件属性配置 
		ForkJoinPool pool = new ForkJoinPool();
		
		//FileMonitor monitor = new FileMonitor(1000);

	    //monitor.addFile(new File(String.valueOf(file)));
//        //monitor.addListener(new FileListener() {
//			
//			@Override
//			public void fileChanged(File file) {
//			
//				System.out
//						.println("====="+file.getAbsolutePath()+"---------"+file.getName()+"  parent "+file.getParentFile());
//			}
//		});
		//FileSystemTask task=new FileSystemTask(String.valueOf(file),monitor);
		FileSystemTask task=new FileSystemTask(path);
		pool.submit(task);
		pool.shutdown();
		this.nodes=task.join();
		 if(thread!=null&&this.thread.isAlive()){
	        	this.thread.stopThread();
	        }else{
	        this.thread=new InfiniteLoopDaemon() {
				
				@Override
				protected void loop() {
					//后台守护进程 一直 轮训 
					 doLoop(); 
					 //logger.info("-------------");
				}
			};
	        }
		return true;
	    }
		return false;
		
	}

	@Override
	public void stop(){
		if(thread.isAlive()){
			
			thread.stopThread();
		}
	   
	}

	public void addFileHandler(FileEvent fe, FileSystemManager fileSystemManager) {
		FileEventHandler fileEvent=new FileEventHandler(fe, (FileSystemService) fileSystemManager.getService());
		for (Iterator i = listener.iterator(); i.hasNext();) {
			WeakReference reference = (WeakReference) i.next();
			FileEventHandler ls=(FileEventHandler) reference.get();
			if (ls == fileEvent)
				return;
		}
		/**
		 * 使用弱引用  来避免内存泄露 优化内存
		 */
		listener.add(new WeakReference(fileEvent));
		
	}
	
//	public FileDataMessage doSync(){
//		Future<FileDataMessage> fu=null;
//		while((fu=futures.poll())!=null){
//			if(fu.isDone()){
//				//完成了 开始准备发送 
//				if(!fu.isCancelled()){
//				try {
//					return fu.get();
//				} catch (Exception e) {
//					return null;
//					
//					
//				} 
//				}
//			}else{
//				
//				futures.add(fu);
//			}
//			
//			
//		}
//		return null;
//	}
//	
	@Override
	public void doLoop(){
	
//				FileEventHandler handler=listener.poll(); //这里可以用this.wait /lock 一样的
		if((obj=listener.poll())==null) return;
		WeakReference reference = (WeakReference) obj;
		FileEventHandler handler=(FileEventHandler) reference.get();
				if(handler!=null){
					//文件不断的更新 这后台执行的 国语频繁 改为同步
					//executorService.submit(handler);
					sendFileHandler(handler);
				}				
	//这里有一个是 使用listener.poll() 每次 轮训一个 还是一次性把所有更改交由后台执行 全部执行完后返回
	//前者后者都有优势，但是用户每天大量上传的时候 需要不断的执行  后者不易控制 容器清空 这个过程 大部分要全部锁	
		      
	
	}

    /**
     * 把事件同步处理 交由后台守护进程 
     * @param handler
     * 添加（修改）人：zhuyuping
     */
	private void sendFileHandler(FileEventHandler handler) {
		FileEvent fileEvent=handler.getFileEvent();
		FileSystemService fileSystemService=handler.getFileSystemService();
		switch (fileEvent.getFilestate()) {
		case NEEDADD:
				fileSystemService.insert(fileEvent.getPath().getAbsolutePath());
			break;
		case NEEDDELETE:
				fileSystemService.delete(fileEvent.getPath().getAbsolutePath());
		    break;
		case NEEDUPDATE:
			  fileSystemService.update(fileEvent.getPath());
			break;
		default:
			//不需要
			break;
		}
		
	}


	@Override
	public InetSocketAddress getAddress() {
		
		return config.getContext().getCurrHost();
	}


	@Override
	public String getName() {
		
		return NAME;
	}


	@Override
	public void addHandler(Event event, NodeManager nodeManager) {
		FileEvent fe=(FileEvent) event;
		FileSystemManager fsm=(FileSystemManager) nodeManager;
		addFileHandler(fe, fsm);
	}



	
	
	
}
