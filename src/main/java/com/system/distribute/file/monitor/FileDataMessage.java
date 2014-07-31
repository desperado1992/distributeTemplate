package com.system.distribute.file.monitor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.Serializable;

import com.system.common.FileState;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0        这里我就不再head上添加加密了  内网之间不必要
 * @created 2014-7-11 下午5:31:05               body byte[]长度                                            path长度
 * @function:文件传输协议 1.head 识别头 sessionId lenth lenth2 2.body command path bytes[] start end 
 */
public class FileDataMessage implements Serializable{

	
	//文件的hash校验
	private long hash;//hashmd5 checksum
	
	private Integer head;//文件识别头 我们默认让他为0522 也就是十进制 1314 不是该头的排除
	
	private Integer sessionId;//
	
	private String path;//当前本地文件系统的路径 
	
	private long lenth;//文件长度  如果目录使用 另外便利之后 变成文件处理 
    
	private byte[] bytes;//文件需要传输的
	
    private int command=1;// 0 代表删除 1 代表需要同步  如果接受的是0 代表 直接移除该文件 不需要网络传输了bytes 
    
	public long getHash() {
		return hash;
	}

	public void setHash(long hash) {
		this.hash = hash;
	}

	private long start=0;
	
	private long end=0;
	
	
	
	public ByteBuf  toBuffer(){
		byte[] path=this.getPath().getBytes();
		int size=(int) (27+this.getLenth()+path.length);
		ByteBuf buf=Unpooled.directBuffer(size);
		buf.writeShort(1314);
		buf.writeInt(this.getSessionId());
		buf.writeInt((int) this.getHash());
		buf.writeInt(path.length);
		buf.writeInt((int) this.getLenth());
		buf.writeInt((int) this.getStart());
		buf.writeInt((int) this.getEnd());
		buf.writeByte(this.getCommand());
		buf.writeBytes(path);
		buf.writeBytes(this.getBytes());
		return buf;
	}

	
	public Integer getHead() {
		return head;
	}

	public void setHead(Integer head) {
		this.head = head;
	}

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getLenth() {
		return lenth;
	}

	public void setLenth(long lenth) {
		this.lenth = lenth;
	}

	public FileDataMessage() {
		super();
	
	}

	@Override
	public String toString() {
		return "FileDataMessage [hash=" + hash + ", head=" + head
				+ ", sessionId=" + sessionId + ", path=" + path + ", lenth="
				+ lenth + ", command=" + command + ", start=" + start
				+ ", end=" + end + "]";
	}

	public void toBuffer(ByteBuf buf) {
		
		byte[] path=this.getPath().getBytes();
		int size=(int) (4+27+this.getLenth()+path.length);
		System.out.println("FileDataMessage.toBuffer() "+size);
		buf.writeInt(size);
		buf.writeShort(1314);
		buf.writeInt(this.getSessionId());
		buf.writeInt((int) this.getHash());
		buf.writeInt(path.length);
		buf.writeInt((int) this.getLenth());
		buf.writeInt((int) this.getStart());
		buf.writeInt((int) this.getEnd());
		buf.writeByte(this.getCommand());
		buf.writeBytes(path);
		buf.writeBytes(this.getBytes());
	}
	
	
	
	
}
