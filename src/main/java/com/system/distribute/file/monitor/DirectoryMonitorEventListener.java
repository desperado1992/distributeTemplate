package com.system.distribute.file.monitor;

import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.util.EventListener;

public interface DirectoryMonitorEventListener extends EventListener {

    public void action(Path dir, Path cause, Kind<Path> event);

}
