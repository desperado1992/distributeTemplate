package com.system.distribute.file.monitor;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class FileMonitorImpl implements FileMonitor {
	

   

    private List<FileMonitorEventListener> listeners = Lists.newArrayList();

    private List<Path> entries = Lists.newArrayList();

    private Map<Path, DirectoryMonitor> monitors = Maps.newHashMap();

    public FileMonitorImpl() {

    }

    public FileMonitorImpl(FileMonitorEventListener listener) {

        listeners.add(listener);
    }

    public FileMonitorImpl(Path entry, FileMonitorEventListener listener) {

        addListener(listener);
        add(entry);
    }

    public FileMonitorImpl(List<Path> entries, FileMonitorEventListener listener) {

        addListener(listener);
        add(entries);
    }

    @Override
    public void start() {

       

        for(Path entry: entries) {
            monitor(entry);
        }
    }

    @Override
    public void stop() {

        for(Path entry: entries) {
            unmonitor(entry);
        }

       
    }

    @Override
    public void addListener(FileMonitorEventListener listener) {

        listeners.add(listener);
    }

    @Override
    public void removeListener(FileMonitorEventListener listener) {

        listeners.remove(listener);
    }

    @Override
    public void add(Path entry) {

        if(!entries.contains(entry)) {
            entries.add(entry);
            monitor(entry);
        }
    }

    @Override
    public void add(List<Path> entries) {

        for(Path entry: entries) {
            add(entry);
        }
    }

    @Override
    public void remove(Path entry) {

        if(entries.contains(entry)) {
            unmonitor(entry);
            entries.remove(entry);
        }
    }

    @Override
    public void remove(List<Path> entries) {

        for(Path entry: entries) {
            remove(entry);
        }
    }

    @Override
    public void removeAll() {

        for(Path entry: entries) {
            remove(entry);
        }
    }

    @Override
    public List<Path> getEntries() {

        return entries;
    }

    private void monitor(Path entry) {
         
        Path dir = entry.getParent();
        if(monitors.containsKey(dir)) {
            return;
        }

        try {
            DirectoryMonitor dm = new DirectoryMonitorImpl(dir, false, new DirectoryMonitorEventListener() {

                @Override
                public void action(Path dir, Path cause, Kind<Path> event) {

                    if(entries.contains(cause)) {
                        for(FileMonitorEventListener listener: listeners) {
                            listener.action(cause, event);
                        }
                    }
                }
            });
            dm.start();
            monitors.put(dir, dm);
        }
        catch(Exception e) {
           throw new RuntimeException("监控文件服务启动失败"+e.getMessage());
        }
    }

    private void unmonitor(Path entry) {

        Path dir = entry.getParent();
        if(!monitors.containsKey(dir)) {
            return;
        }

        DirectoryMonitor dm = monitors.get(dir);
        dm.stop();
        monitors.remove(dir);
    }

}
