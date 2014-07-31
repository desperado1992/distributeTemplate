package com.system.distribute.file.monitor;


import com.system.distribute.service.FileSystemService;


/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-11 下午5:36:49 
 * @function:文件事件处理器
 */

public class FileEventHandler implements Runnable{
	

	private FileEvent fileEvent;//文件事件对象
	
	private FileSystemService fileSystemService;
	
	public FileEventHandler(FileEvent fileEvent,FileSystemService service) {
		super();
		this.fileEvent = fileEvent;
		this.fileSystemService=service;
	}



	



	public FileEvent getFileEvent() {
		return fileEvent;
	}







	public void setFileEvent(FileEvent fileEvent) {
		this.fileEvent = fileEvent;
	}







	public FileSystemService getFileSystemService() {
		return fileSystemService;
	}







	public void setFileSystemService(FileSystemService fileSystemService) {
		this.fileSystemService = fileSystemService;
	}







	@Override
	public void run() {
		//logger.info(fileEvent.toString());
	
		try{
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
		}catch(Exception e){
			
		}
		
	}

}
