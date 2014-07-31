package com.system.distribute.file.monitor;


import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;



import com.system.common.InfiniteLoopDaemon;

public class DirectoryMonitorImpl implements DirectoryMonitor {
	

  

    private Path dir;

    private boolean recursive;

    private DirectoryMonitorEventListener listener;

    private WatchService watcher;

    private Map<WatchKey, Path> entries;

    private Thread thread;

    public DirectoryMonitorImpl() {

    }

    public DirectoryMonitorImpl(Path dir, boolean recursive, DirectoryMonitorEventListener listener){
        
        this.dir = dir;
        this.recursive = recursive;
        this.listener = listener;
        
    }

    @Override
    public void setDir(Path dir) {

        this.dir = dir;
    }

    @Override
    public void setRecursive(boolean recursive) {

        this.recursive = recursive;
    }

    @Override
    public void setListener(DirectoryMonitorEventListener listener) {

        this.listener = listener;
    }

    @Override
    public void start() {

       
        
        if(!Files.isDirectory(dir)) {
            throw new FileMonitorException("Entry must be a directory");
        }

        // prepare
        try {
            watcher = dir.getFileSystem().newWatchService();
            entries = new HashMap<WatchKey, Path>();
            if(recursive) {
                registerAll(dir);
            }
            else {
                register(dir);
            }
        }
        catch(IOException e) {
            throw new FileMonitorException("Cannot create monitor", e);
        }

        if(thread!=null&&this.thread.isAlive()){
        	this.thread.interrupt();
        }else{
            thread=new Thread(new Runnable() {
				
				@Override
				public void run() {
				   process();	
					
				}
			});
            thread.setDaemon(true);
            thread.start();
        }
        
    }

    @Override
    public void stop() {

        

        // clean up
        try {
            watcher.close();
            entries.clear();
        }
        catch(IOException e) {
        }

        if(!thread.isInterrupted()) thread.interrupt();
    }

    @SuppressWarnings("unchecked")
    public void process() {

        while(!Thread.currentThread().isInterrupted()) {

            // wait for an event
            WatchKey key;
            try {
                key = watcher.take();
            }
            catch(Exception e) {
                return;
            }

            // pool an event
            Path dir = entries.get(key);
            for(WatchEvent<?> event: key.pollEvents()) {

                // get details
                Kind<?> kind = event.kind();
                if(kind == StandardWatchEventKinds.OVERFLOW) {
                   
                    continue;
                }
                Path child = dir.resolve(((WatchEvent<Path>) event).context());

                // notify
                listener.action(dir, child, (Kind<Path>) kind);

                // register child if created
                if(kind == StandardWatchEventKinds.ENTRY_CREATE && Files.isDirectory(child)) {
                    try {
                        if(recursive) {
                            registerAll(child);
                        }
                    }
                    catch(IOException e) {
                        
                    }
                }
            }

            if(!key.reset()) {
                entries.remove(key);
            }
        }
    }

    private void registerAll(Path dir) throws IOException {

        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attrs) throws IOException {

                register(directory);

                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void register(Path dir) throws IOException {

        

        if(Files.isDirectory(dir)) {
            WatchKey key = dir.register(watcher,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
            entries.put(key, dir);
        }
    }

}
