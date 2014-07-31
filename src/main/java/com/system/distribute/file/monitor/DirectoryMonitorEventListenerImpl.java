package com.system.distribute.file.monitor;


import com.system.common.FileState;
import com.system.distribute.config.IConfig;
import com.system.distribute.file.FileSystem;
import com.system.distribute.file.FileSystemManager;



import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent.Kind;



public class DirectoryMonitorEventListenerImpl implements DirectoryMonitorEventListener {
	

    private FileSystemManager manager;
    
    

    public DirectoryMonitorEventListenerImpl(FileSystemManager manager) {
		super();
		this.manager=manager;
	}



	@Override
    public void action(Path dir, Path cause, Kind<Path> event){
    	
    	FileEvent fileEvent=null;
   	
    	
        if(event == StandardWatchEventKinds.ENTRY_CREATE||event==StandardWatchEventKinds.ENTRY_MODIFY) {
        	if(event==StandardWatchEventKinds.ENTRY_MODIFY&&Files.isDirectory(cause)){
        		return;
        	}
        	fileEvent=new FileEvent();
        	fileEvent.setFilestate(FileState.NEEDADD);
        	fileEvent.setIsdir(Files.isDirectory(cause));
        }else if(event == StandardWatchEventKinds.ENTRY_DELETE) {
        	
        
              
         		fileEvent=new FileEvent();
         		
            	fileEvent.setFilestate(FileState.NEEDDELETE);
            	
            	
            	fileEvent.setIsdir(Files.isDirectory(cause));
        	
        }
        
        fileEvent.setPath(cause.toFile());
        manager.addFileHandler(fileEvent);
       
      
    }

}
