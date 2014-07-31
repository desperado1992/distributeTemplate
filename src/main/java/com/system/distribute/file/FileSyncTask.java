package com.system.distribute.file;


import java.io.File;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;



import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.system.distribute.file.helper.HttpUploadClient;
import com.system.distribute.file.helper.ServerHelper;
/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-28 上午12:50:33 
 * @function:添加分段上传
 */
public class FileSyncTask extends RecursiveTask<Map<String,String>>{

	
	private InetSocketAddress host;
	
	private File path;//要同步的文件或者目录
	
	private boolean isdelete=false;
	
	public FileSyncTask(InetSocketAddress host, File path) {
		this.host=host;
		this.path=path;
		this.isdelete=false;
	}

	
	
	public FileSyncTask(InetSocketAddress host, File path, boolean isdelete) {
		super();
		this.host = host;
		this.path = path;
		this.isdelete = isdelete;
	}


	//用来发送 文件 同步 上传
	@Override
	protected Map<String,String> compute() {
		
		if(!isdelete){
		Map<String,String> results=Maps.newHashMap();
		
		List<FileSyncTask> tasks=Lists.newArrayList();
		
			File[] content=path.listFiles();
			if(content==null){
				//是文件 
				//发送文件
				String uri=ServerHelper.genenerBaseUri(host);
				String fpath=ServerHelper.gennerFilePath(path.getAbsolutePath());
				try {
					//现在初期 只简单的 没有做 是否上传成功的判断 后面 还要添加md5sum验证 是否正确完整
					//添加分段上传
					//String baseUri,boolean isChunk,ImmutableMap<String,Object> map,Object obj
			        if(path.length()>FileChunk.CHUNKSIZE){//50M分割
			        	Map<Integer,FileChunk> fchunks=FileChunkFactory.createChunks(path, FileChunk.CHUNKSIZE, path.length());
			            for (Map.Entry<Integer, FileChunk> chunk : fchunks.entrySet()) {
			            	HttpUploadClient.post(uri,true,ImmutableMap.<String, Object>of("commandType",0,"name",fpath,"chunksizes",path.length(),"chunksum",fchunks.size()),chunk.getValue());
						}
			        }else{
					HttpUploadClient.post(uri,false,ImmutableMap.<String, Object>of("commandType",0,"name",fpath),fpath);
			        }
				} catch (Exception e) {
					results.put(uri, fpath);//吧没有成功的文件记录下来 返回
					
				}
			}else{
				//是目录
			for (File file : content) {
				//后期添加 修改目录属性  真正的变成 非常好的分布式文件系统 这里只是暂时同步文件
				FileSyncTask task=new FileSyncTask(host, file);
				task.fork(); 
				tasks.add(task);
			}
			}
		
		for (FileSyncTask task : tasks) {
			results.putAll(task.join());
		}
		return results;
		}else{
			//删除指令 
			String uri=ServerHelper.genenerBaseUri(host);
			String fpath=ServerHelper.gennerFilePath(path.getAbsolutePath());
			try {
				System.out.println("FileSyncTask.compute() "+uri+"----path is "+fpath);
				HttpUploadClient.get(uri, ImmutableMap.<String,Object>of("name",fpath,"commandType","5"));
			} catch (Exception e) {
				
				
			}
			
			return  null;
		}
		
		
	}

}
