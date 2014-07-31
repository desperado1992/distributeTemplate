package com.system.distribute.file.monitor;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent.Kind;



public class FileMonitorEventListenerImpl implements FileMonitorEventListener {
	
    

    @Override
    public void action(Path entry, Kind<Path> event) {
        
        if(event == StandardWatchEventKinds.ENTRY_CREATE) {
            
        }
        else if(event == StandardWatchEventKinds.ENTRY_DELETE) {
           
        }
        else if(event == StandardWatchEventKinds.ENTRY_MODIFY) {
            
        }
    }

}
