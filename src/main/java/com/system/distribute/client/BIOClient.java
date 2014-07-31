package com.system.distribute.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


import com.system.distribute.file.monitor.FileDataMessage;

public class BIOClient {

    private Socket client=null;
	
    
    
    
	public BIOClient(InetSocketAddress addr) {
		super();
		try {
			this.client = new Socket(addr.getHostName(), addr.getPort());
		}catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void send(FileDataMessage fm,InetSocketAddress addr) throws Exception{
		if ((client == null)||(client.isClosed())){
			client = new Socket(addr.getHostName(), addr.getPort());
		}
		
		DataOutputStream os = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
		ObjectOutputStream out = new ObjectOutputStream(os);
		out.writeObject(fm);	
		out.flush();
		//out.writeInt((int) (27+fm.getLenth()+fm.getPath().length()+6));//Send the length that we are sending including the integer and 2 byte header.
		//out.flush();
		out.close();
		client.close();
	}

	private void sendFile() {   
        Socket socket = null;   
        DataInputStream in = null;   
        DataOutputStream out = null;   
           
        try {   
            socket = new Socket("localhost",465);   
        } catch (Exception e) {   
            
            return;   
        }   
           
        try {   
            socket.setSoTimeout(60000);   
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));   
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));   
        } catch(Exception e) {   
              
            if(in != null) {   
                try {   
                    in.close();   
                    in = null;   
                } catch(Exception e1) { }   
            }   
            if(out != null) {   
                try {   
                    out.close();   
                    out = null;   
                } catch(Exception e2) { }   
            }   
            try {   
                socket.close();   
                socket = null;   
            } catch(Exception e3) { }   
            return;   
        }
        //开始读 开始写
        
        
        
	}
	public void receive() throws Exception{
		ObjectInputStream objectInput = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
		String receivedMessage = objectInput.readUTF();
        System.out.println("ClientLauncher.receive()"+receivedMessage);
		client.close();
	}
	
	
}
