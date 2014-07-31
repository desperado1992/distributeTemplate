package com.system.distribute.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;




import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.system.common.InfiniteLoopDaemon;
import com.system.distribute.file.monitor.FileDataMessage;
/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-18 下午1:50:16 
 * @function:服务器的测试类
 */
public class ClientLauncher {

	private static ServerSocket serverSocket;
	private static Socket server;
	
	public static void Accept(){

		try {
			if (serverSocket == null){
				serverSocket = new ServerSocket(465);
			}

			server = serverSocket.accept(); //this is a blocking call
          

		} catch (IOException e) {
			System.out.println("Error during Receiver.Accept.");
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	public static void recevie() throws Exception{
		
		if (serverSocket == null){
			serverSocket = new ServerSocket(465);
		}
		server = serverSocket.accept(); 
		ObjectInputStream objectInput = new ObjectInputStream(new BufferedInputStream(server.getInputStream()));
		Object object = objectInput.readObject();
		final FileDataMessage fm=(FileDataMessage) object;
		//File file=new File(fm.getPath());
		File dsr=new File("e:/","upload.jpg");
		if(dsr.exists()) dsr.createNewFile();
		
		System.out.println("ClientLauncher.recevie()"+fm.getBytes().length);
		final InputStream input=new ByteArrayInputStream(fm.getBytes());
		OutputStream out=new FileOutputStream(dsr);
		
		Files.copy(new InputSupplier<InputStream>() {

			@Override
			public InputStream getInput() throws IOException {
				
				return input;
			}
		}, dsr);
		out.flush();
		out.close();
		input.close();
		objectInput.close();
		//IOUtils.copy(, );
		System.out.println("ClientLauncher.recevie()"+fm);
	}
	
	public static boolean Listening(InetAddress host)
	{
		Socket s = new Socket();

		try
		{    	
			s.connect(new InetSocketAddress(host, 8888), 1000);
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
	
	public static void main(String[] args) throws Exception {
		
		while(true){
			
			new ClientLauncher().recevie();
		}
		
			
	
		
	}
}
