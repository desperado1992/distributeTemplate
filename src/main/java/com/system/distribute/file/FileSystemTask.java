package com.system.distribute.file;



import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

import org.antlr.v4.runtime.misc.FlexibleHashMap;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.system.distribute.file.monitor2.FileListener;
import com.system.distribute.file.monitor2.FileMonitor;
import com.system.distribute.util.FileNameUtils;


/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-10 下午6:40:28 
 * @function:返回 当前文件系统的根节点 
 */
public class FileSystemTask extends RecursiveTask<List<Document>>{
	

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
	protected List<Document> compute() {
		
		
	    
        List<Document> docs=Lists.newArrayList();
        File mfile=new File(path);
        if(!mfile.exists()) mfile.mkdirs();
		File content[] = mfile.listFiles();
		if (content == null) {
			return null;
		}
		
		List<FileSystemTask> tasks = Lists.newArrayList();
		for (File file2 : content) {
			if (file2.isDirectory()) {
				////path处理 file://host/xxx/xxxxx
				
				FileSystemTask task = new FileSystemTask(file2.getAbsolutePath());
				task.fork(); 
				tasks.add(task);
				
			} else {
				
//				if(FileNameUtils.getExtension(file2.getAbsolutePath()).equalsIgnoreCase("txt")){
//					
//				}
				Document doc=new Document();
				String filepath=file2.getAbsolutePath();
			  Field type=new StringField(FileQuery.FILE_QUERY.FILETYPE, FileNameUtils.getExtension(filepath), Field.Store.YES);
			  Field name=new TextField(FileQuery.FILE_QUERY.FILENAME, FileNameUtils.getBaseName(filepath), Field.Store.YES);
			  Field size=new StringField(FileQuery.FILE_QUERY.FILESIZE,String.valueOf(file2.length()),Field.Store.YES);
			  Field path=new TextField(FileQuery.FILE_QUERY.FILEPATH, FileNameUtils.getFullPathNoEndSeparator(filepath), Field.Store.YES);
			  
			 doc.add(type);
			 doc.add(path);
			 doc.add(name);
			 doc.add(size);
			 docs.add(doc);
				
			}
		}
		for (FileSystemTask item : tasks) {
			//list.addAll(item.join());
			
			docs.addAll(item.join());
		}
		
		
		
		return docs;
	}

}
