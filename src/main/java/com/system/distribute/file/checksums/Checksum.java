package com.system.distribute.file.checksums;

import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Checksum {
	
	
	public MessageDigest md;
	private FileInputStream fis;

	public Checksum() {
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
   //获得cheksum 后面我们要使用他来鉴别文件完整性 
	public String generateChecksum(String filepath, String filename) {
		try {
			fis = new FileInputStream(filepath);

			byte[] dataBytes = new byte[1024];

			int nread = 0;

			while ((nread = fis.read(dataBytes)) != -1) {
				md.update(dataBytes, 0, nread);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] mdbytes = md.digest();

		String result = byteToHex(mdbytes);
		
//		System.out.println(filename + " :: " + result);

		return result;
	}

	public String chunking(byte[] dataBytes){
		md.update(dataBytes);
		byte[] mdbytes = md.digest();
		String result = byteToHex(mdbytes);
		return result;
	}
	
	
	public static String byteToHex(byte[] mdbytes) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}
	
	
	public static String calcChecksum(String filepath){
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			FileInputStream finstream = new FileInputStream(filepath); 
		    
		
			DigestInputStream dinstream = new DigestInputStream(finstream, md);
			
			byte[] buffer = new byte[10485760]; //10MB
		
		    @SuppressWarnings("unused")
			int line = 0;
		    while ((line = dinstream.read(buffer)) != -1) {
		    
		    }
		    dinstream.close();
		    finstream.close();
			
		    return new BigInteger(1, md.digest()).toString(16);
		    
		} catch (Exception e) {
	    
	    }
	      
		return null;
	}
	
	public static boolean verifyChecksum(String check1, String check2){
		boolean result = false;
		int compare;
		
		compare = check1.compareTo(check2);
		if (compare == 0) result = true;
		
		return result;
	}

}
