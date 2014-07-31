package com.system.distribute.file;





/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-10 下午5:17:31 
 * @function:文件目录节点  //以后最好 要替换成BTree 效率更高
 */
public class FNode {

	private Long id;//节点的id 
	private String name;//名称
	private String path;//路径 
	private long modified;//最后一次 文件修改的时间 
	private boolean folder;//是否是目录
	private long length;//文件大小 当目录时候 为0
	
	private String checkSum;//检查hash 短hash
	
	

	private String parent;//父类 
	
	//private String nextId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getModified() {
		return modified;
	}

	public void setModified(long modified) {
		this.modified = modified;
	}

	public boolean isFolder() {
		return folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	

	
	
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public FNode() {
		super();
		
	}

	@Override
	public String toString() {
		return "FNode [id=" + id + ", name=" + name + ", path=" + path
				+ ", modified=" + modified + ", folder=" + folder + ", length="
				+ length + "]";
	}
	
	
	
}
