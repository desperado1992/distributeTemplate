package com.system.distribute.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;


public class FileSummary {
	
	public int blockSize;
	
	public FileSummary(File input) {
		blockSize = computeBlockSize(input);
		ArrayList<byte[]> fileBlocks = createBlocks(input, blockSize);
		
	}
	
	private int computeBlockSize(File input) {
		//Computes optimal block size based on rsync algorithm.
		//Replace 5 with the expected number of edits in a file
		return (int) Math.sqrt((double) ((input.length() * 24) / 5));
	}
	
	//Computes a single block of the Adler-32 Hash
	public static int computeAdler32(byte[] data, int length) {
		long A = 1;
		long B = 0;
		
		int addlerMod = 65536;
		for(int i = 0; i < length; i++) {
			A = (A + ((byte)data[i] & 0xFF));
	        A = A % addlerMod;
	        B = (B + A) % addlerMod;
		}

		int retVal = (int) ((B << 16) | A);
		
		//Return format: [B 31:16][A 15:0]
		return retVal;
	}
	
	public static ArrayList<byte[]> createBlocks(File input, int size) {
		long numBlocks = input.length() / size;
		int tmp = 0;
		
		ArrayList<byte[]> blocks = new ArrayList<byte[]>((int) numBlocks + 1);
		try {
			byte[] buffer = new byte[size];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(input));
			
			while((tmp = bis.read(buffer)) > 0) {
				blocks.add(buffer);
				if(tmp != size)
					break;
				else
					buffer = new byte[size];
			}
			bis.close();
			
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
				
		
		return blocks;
	}
	
	
	public static String computeStrongHash(byte[] data) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(data);
			return new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ERROR HASH";
	}

	
}
