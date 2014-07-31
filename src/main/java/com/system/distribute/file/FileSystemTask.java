package com.system.distribute.file;



import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.system.distribute.file.monitor2.FileListener;
import com.system.distribute.file.monitor2.FileMonitor;


/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-10 下午6:40:28 
 * @function:返回 当前文件系统的根节点 
 */
public class FileSystemTask extends RecursiveTask<Map<String,FNode>>{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;
	

	//private FileMonitor monitor;//文件监听

	public FileSystemTask(String path) {
		super();
		this.path = path;
		//this.monitor=monitor;
		
	}




	@Override
	protected Map<String,FNode> compute() {
		
		
	    
        FNode fnode=null;
        File mfile=new File(path);
        if(!mfile.exists()) mfile.mkdirs();
		File content[] = mfile.listFiles();
		if (content == null) {
			return null;
		}
		Map<String,FNode> map=Maps.newHashMap();
		List<FileSystemTask> tasks = Lists.newArrayList();
		for (File file2 : content) {
			if (file2.isDirectory()) {
				////path处理 file://host/xxx/xxxxx
				fnode=new FNode();
				fnode.setId(Seq.nextId());
				fnode.setFolder(true);
				fnode.setModified(file2.lastModified());
				fnode.setPath(file2.getAbsolutePath());//需要减去配置的目录
				fnode.setName(file2.getPath());
				fnode.setParent(file2.getParentFile().getAbsolutePath());
				
				map.put(file2.getAbsolutePath(), fnode);
				FileSystemTask task = new FileSystemTask(file2.getAbsolutePath());
				task.fork(); 
				tasks.add(task);
				
			} else {
				
				//是文件直接加入node里面
				fnode=new FNode();
				fnode.setId(Seq.nextId());
				fnode.setFolder(false);
				fnode.setModified(file2.lastModified());
				fnode.setPath(file2.getAbsolutePath());//需要减去配置的目录
				fnode.setName(file2.getPath());
				fnode.setParent(file2.getParentFile().getAbsolutePath());
				fnode.setLength(file2.length());
				//monitor.addFile(new File(String.valueOf(file2.getAbsolutePath())));

				
				
				map.put(file2.getAbsolutePath(), fnode);
			}
		}
		for (FileSystemTask item : tasks) {
			//list.addAll(item.join());
			
			map.putAll(item.join());
		}
		
		
		
		return map;
	}

}
