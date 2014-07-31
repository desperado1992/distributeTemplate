package com.system.distribute.client;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class TransferToClient {
	
	public static void main(String[] args) throws IOException, InterruptedException{
		TransferToClient sfc = new TransferToClient();
		sfc.testSendfile("e:/networker-master.zip");
	}
	public void testSendfile(String name) throws IOException, InterruptedException {
	    String host = "localhost";
	    //host = args[1];
	    int port = 8888;
	    SocketAddress sad = new InetSocketAddress(host, port);
	    SocketChannel sc = SocketChannel.open();
	    sc.connect(sad);
	    sc.configureBlocking(true);

	    String fname = name;
	    
	    
	    FileChannel fc = new FileInputStream(fname).getChannel();
            long start = System.currentTimeMillis();
	    long nsent = 0, curnset = 0;
	    curnset =  fc.transferTo(0, fc.size(), sc);
	    System.out.println("total bytes transferred--"+curnset+" and time taken in MS--"+(System.currentTimeMillis() - start));

	    // need to call this otherwise the connection is closed abruptly
	    sc.close();
	  }


}
