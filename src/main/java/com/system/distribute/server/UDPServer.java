package com.system.distribute.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

import java.net.InetSocketAddress;

import javax.annotation.Resource;

import com.system.common.ChannelType;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-28 上午11:13:25 
 * @function:服务器 
 */
public class UDPServer{

    private final ChannelType channelType;
    private final InetSocketAddress localAddress;
    private Channel acceptorChannel=null;//
    
   
   
    public UDPServer(ChannelType channelType, int port
			) {
		super();
		this.channelType = channelType;
		this.localAddress = new InetSocketAddress(port);
		
	}

	
    public synchronized void start() {
        acceptorChannel = ServerUDPChannelFactory.createAcceptorChannel(
                channelType, localAddress, new MessageHandler());
        
    }
	
	
	
	

    public synchronized void stop() {
    	if(acceptorChannel!=null){
        acceptorChannel.close().addListener(ChannelFutureListener.CLOSE);
    	
    	}
    }

    public void restart(){
    	stop();
    	start();
    	
    }

	
}
