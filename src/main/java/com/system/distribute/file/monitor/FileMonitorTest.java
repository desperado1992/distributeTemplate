package com.system.distribute.file.monitor;



import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import java.util.Map;

import com.system.common.InfiniteLoopDaemon;
@Deprecated
public class FileMonitorTest extends InfiniteLoopDaemon{
	
    
	private String path;//文件目录 
	
	
	@Override
	protected void loop() {
		
		
	}

	public void stopMonitor(){
		stopThread();
	}
	
	
	
	


	public FileMonitorTest(String path) {
		super();
		this.path = path;
		//形成文件管理的节点结构
		
	}

	public static void trackFile(String pathname,Map<String, Object> events ) throws IOException,
			InterruptedException {
		// Path path = FileSystems.getDefault().getPath("/var/log/");

		Path file = FileSystems.getDefault().getPath(pathname);
		String filename = file.getFileName().toString();
	
		Path path = file.getParent();
		WatchService watcher = FileSystems.getDefault().newWatchService();
		path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
		while (true) {
			
			WatchKey poll = watcher.take();
			for (WatchEvent evt : poll.pollEvents()) {
				if (((Path) evt.context()).toString().equals(filename)) {
				
					//synchronized(events){
//						FileNode node = new FileNode();
//						node.setPath(pathname);
//						node.setModified(new Date());
//						events.put(pathname, node);
//						events.notifyAll();
						//todo 去提供毁掉机制 交给线程轮训
					//}
					
					/**
					 *     boolean valid = wk.reset();
            if (!valid) {
                System.out.println("Key has been unregisterede");
            }
					 * 
					 * 
					 */
					return;
				} else {
					
					watcher.close();
				}
			}
			 //重设WatchKey  
	          boolean valid=poll.reset();  
	          //监听失败，退出监听  
	          if(!valid){  
	              break;  
	          } 
		}
	}


		 public static void main(String[] args)throws Exception {  
	          
		        //获取当前文件系统的WatchService监控对象  
		        WatchService watchService=FileSystems.getDefault().newWatchService();  
		        //监听的事件类型，有创建，删除，以及修改  
		        //Path file = FileSystems.getDefault().getPath(pathname);
		        Paths.get("E:\\女神密电").register(watchService, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.OVERFLOW);  
		      
		      while(true){  
		          //获取下一个文件变化事件  
		          WatchKey key=watchService.take();  
		          for(WatchEvent<?> event:key.pollEvents()){  
		                
		              System.out.println(event.kind().name()+"文件发生了"+event.context().toString()+"事件"+"此事件发生的次数: "+event.count());  
		          }  
		          //重设WatchKey  
		          boolean valid=key.reset();  
		          //监听失败，退出监听  
		          if(!valid){  
		              break;  
		          }  
		      }  
		    } 
}
