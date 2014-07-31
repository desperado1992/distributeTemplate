package com.system.distribute.file.monitor2;



import java.io.File;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;



public class StableFileNotifier extends TimerTask {
	
	
	private volatile File directory;
	private Map<String, Long> modifiedTimeCache = new ConcurrentHashMap<String, Long>();

	public StableFileNotifier(String file) {
		this.directory = new File(file);
	}

	@Override
	public void run() {
		File[] listFiles = directory.listFiles();
		for (File file : listFiles) {
			if (file.exists() && file.isDirectory())
				continue;

			String key = file.getAbsolutePath();
			if (!modifiedTimeCache.containsKey(key)) {
				modifiedTimeCache.put(key, file.lastModified());
				continue;
			}

			long newModifiedTime = file.lastModified();
			long oldModifiedTime = modifiedTimeCache.get(key);
			if (newModifiedTime != oldModifiedTime) {
				modifiedTimeCache.put(key, file.lastModified());
			} else {
				
			}
		}
		
	}
}
