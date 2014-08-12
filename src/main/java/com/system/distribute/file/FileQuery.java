package com.system.distribute.file;

import com.system.distribute.sqlparser.BaseQuery;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-8-4 下午1:49:55 
 * @function:文件查询对象
 */
public class FileQuery extends BaseQuery{

	
	public static final FileQuery FILE_QUERY=new FileQuery();
	
	public final String FILENAME="name";
	
	public final String FILESIZE="size";
	
	public final String FILETYPE="type";
	
	public final String FILEPATH="path";
	
	public final String FILEOUT="out";

	public String getFILENAME() {
		return FILENAME;
	}

	public String getFILESIZE() {
		return FILESIZE;
	}

	public String getFILETYPE() {
		return FILETYPE;
	}

	public String getFILEPATH() {
		return FILEPATH;
	}

	public String getFILEOUT() {
		return FILEOUT;
	}
	
	
	
	
}
