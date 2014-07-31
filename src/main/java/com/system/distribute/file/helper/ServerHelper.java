package com.system.distribute.file.helper;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;



/** 
 * @author qgan
 * @version 2014年2月12日 上午9:31:53
 */
public class ServerHelper {
	public static File TMP_DIR;
	public static File ROOT_DIR;
	public static File DEST_DIR; // 文件上传目录
	static {
		try {
			String path = ServerHelper.class.getResource("/").toURI().getPath();
			ROOT_DIR = new File(path);
			TMP_DIR = new File(ROOT_DIR.getAbsoluteFile() + File.separator + "tmp");
			if(!TMP_DIR.exists())
				TMP_DIR.mkdir();
		} catch (Exception ingore) {
		}
	}
	
	public static Properties SERVER_CONF = new Properties();
	public static int getPort(int defaultPort) {
		String port = SERVER_CONF.getProperty("server.port");
		if(null == port || port.trim().equals("")) {
			return defaultPort;
		} else {
			return Integer.valueOf(port);
		}
	} 
	
	public static String getPlayServer() {
		return SERVER_CONF.getProperty("play.server");
	}
	
	public static File getDestDir() {
		if(DEST_DIR == null) {
			DEST_DIR = new File(SERVER_CONF.getProperty("play.store.path"));
			if(!DEST_DIR.exists())
				DEST_DIR.mkdirs();
		}	
		return DEST_DIR;
	}
	
	public static String getStorePath(String filename) {
		StringBuilder sb = new StringBuilder();
		sb.append(filename.substring(0, 2))
				.append(File.separator).append(filename.substring(2, 4)).append(File.separator);
		return sb.toString();
    }
	
	public static void deleteTmpFile(String filename) {
		try {
			File dir = new File(getDestDir().getAbsolutePath() + File.separator + getStorePath(filename));
			File tempFile = new File(dir, filename + ".utmp");
	        if(tempFile.exists()) { // 文件已经存在可能是上次上传遗留的
	        	tempFile.delete();
	        }
		} catch (Throwable ingore) {
		}
	}
	public static boolean listening(InetSocketAddress address)
	{
		Socket s = new Socket();

		try
		{    	
			s.connect(new InetSocketAddress(address.getAddress(), address.getPort()), 1000);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
		finally
		{
			if(s != null)
				try {s.close();}
			catch(Exception e){
				System.out.println("Error during Receiver.Listening");
				System.exit(-1);
			}
		}
	}
	
	public static String genenerBaseUri(InetSocketAddress address){
		
		return genenerBaseUri(address.getHostName(),address.getPort());
	}

	public static String genenerBaseUri(String hostName, int port) {
		StringBuffer sb=new StringBuffer("http://");
		sb.append(hostName).append(":").append(port);
		return sb.toString();
	}
	
	public static String gennerFilePath(String path){
		return path.replace(File.separator, "/");
	}
	
	public static void main(String[] args) {
		System.out.println(ServerHelper.gennerFilePath("E:/asaaa/vvvvv/cccc"));
	}
}
