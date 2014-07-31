package com.system.distribute.file.monitor;

import java.io.File;
import java.io.Serializable;



import com.system.common.FileState;
import com.system.distribute.core.Event;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-11 下午5:31:05 
 * @function:文件监听返回的文件事件 
 */
public class FileEvent implements Event{

	//文件状态
	private FileState filestate;
	
	
	private boolean isdir=false;//如果是目录 需要同步目录 与文件系统
	
	private File path;//当前本地文件系统的路径
	


	public FileState getFilestate() {
		return filestate;
	}

	public void setFilestate(FileState filestate) {
		this.filestate = filestate;
	}

	

	public boolean isIsdir() {
		return isdir;
	}

	public void setIsdir(boolean isdir) {
		this.isdir = isdir;
	}

	public File getPath() {
		return path;
	}

	public void setPath(File path) {
		this.path = path;
	}

	

	public FileEvent() {
		super();
	
	}

	@Override
	public String toString() {
		return "FileEvent [filestate=" + filestate + ", isdir=" + isdir
				+ ", path=" + path + "]";
	}
	
	
	
	
}
