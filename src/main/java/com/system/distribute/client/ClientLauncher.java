package com.system.distribute.client;

import io.netty.channel.Channel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.zip.Adler32;

import com.google.common.io.ByteStreams;
import com.google.common.io.InputSupplier;
import com.system.common.ChannelType;
import com.system.distribute.file.monitor.FileDataMessage;




/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-28 上午11:26:58 
 * @function:
 */
public class ClientLauncher {

    
	private Socket client=null;
	
	public void send(FileDataMessage fm) throws Exception{
		if ((client == null)||(client.isClosed())){
			client = new Socket("localhost", 465);
		}
		
		DataOutputStream os = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
		ObjectOutputStream out = new ObjectOutputStream(os);
		out.writeObject(fm);	
		out.flush();
		out.writeInt((int) (27+fm.getLenth()+fm.getPath().length()+6));//Send the length that we are sending including the integer and 2 byte header.
		out.flush();
	}

	public void receive() throws Exception{
		ObjectInputStream objectInput = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
		String receivedMessage = objectInput.readUTF();
        System.out.println("ClientLauncher.receive()"+receivedMessage);
		
		client.close();
		
		
	}
	
	
	
	public static void main(String[] args) throws Exception {
	   
       Channel channel=TCPClient.getChannel(ChannelType.NIO, new InetSocketAddress("localhost", 8888));
       File file=new File("d:/a2cc7cd98d1001e98bd6e993b90e7bec55e79740.jpg");//d:/Dota2Superhelper2.7.8.rar
       FileDataMessage fm=new FileDataMessage();
		try{
		final InputStream input=new FileInputStream(file);
		fm.setBytes(ByteStreams.toByteArray(input));
		fm.setSessionId(System.identityHashCode(file.getAbsolutePath()));
		fm.setHead(1314);
		fm.setCommand(1);
		fm.setLenth(file.length());
		fm.setPath(file.getAbsolutePath());
		fm.setStart(0);
		fm.setEnd(file.length());
		fm.setHash(ByteStreams.getChecksum(new InputSupplier<InputStream>() {

			@Override
			public InputStream getInput() throws IOException {
				
				return input;
			}
		}, new Adler32()));//crc32最强验证 adl32最弱验证
		}catch(Exception e){
			
		}
		//new ClientLauncher().send(fm);
       channel.writeAndFlush(fm);	
		
       
    }
}
