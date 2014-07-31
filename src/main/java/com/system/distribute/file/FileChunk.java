package com.system.distribute.file;

import java.io.Serializable;

/**
 * 文件chunk分割快
 * @author zhuyuping
 *
 */
public class FileChunk implements Serializable{

	
	private String hash;//bytehash
	
	private int chunk;//所属快
	
	private byte[] bytes;//内容
	
	//private FileChunk next;//一下个文件快 
	
	public static final Integer CHUNKSIZE=1024*1024*50; //50M分割

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public int getChunk() {
		return chunk;
	}

	public void setChunk(int chunk) {
		this.chunk = chunk;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	
	
	
	
}
