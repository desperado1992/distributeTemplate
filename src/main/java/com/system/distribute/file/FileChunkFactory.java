package com.system.distribute.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;
import com.system.distribute.file.checksums.Checksum;

public class FileChunkFactory {
	
	//private Map<Integer,FileChunk> chunks=Maps.newHashMap();//块管理
	
	/**
	 * 文件快的生成 
	 * @param f
	 * @param chunkSize
	 * @param fileSize
	 * @return
	 */
	public static Map<Integer,FileChunk> createChunks(File f,int chunkSize, long fileSize){
		  HashMap<Integer, FileChunk> file = Maps.newHashMap();
	        FileInputStream fis = null;
	        MessageDigest md;
	        try {
	            fis = new FileInputStream(f);
	            BufferedInputStream bfis = new BufferedInputStream(fis);
	            md = MessageDigest.getInstance("SHA1");
	            byte[] chunk;
	            int chunkID;

	            int numChunks = (int) ((fileSize / chunkSize) + 1);
	            System.out.println("numChunks = " + numChunks);
	            for (chunkID = 0; chunkID < numChunks; chunkID++) {
	                chunk = new byte[chunkSize];
	                FileChunk fchunk=new FileChunk();
	                 int read=fis.read(chunk);
	                
	                 if(read==-1) continue;
	                 md.update(chunk, 0, read);
	                 if(read!=chunkSize){
	                	 //说明最后一块
	                	 byte[] tmp = new byte[read];
	         			System.arraycopy(tmp, 0, chunk, 0, read);
	         			chunk = tmp;
	                 }
	                fchunk.setBytes(chunk);
	                fchunk.setChunk(chunkID+1);
	                fchunk.setHash(Checksum.byteToHex(md.digest()));
	                file.put(chunkID, fchunk);
	               
	            }
                bfis.close();
	         
	        } catch (Exception ioe) {
	        }finally{
	        	if(fis!=null)
					try {
						fis.close();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
	        	
	        }
	        return file;
		
	}
	
	

}
