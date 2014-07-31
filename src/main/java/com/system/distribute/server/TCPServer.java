package com.system.distribute.server;

import io.netty.channel.Channel;
import java.net.InetSocketAddress;
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

public class TCPServer{

    private final ChannelType channelType;
    private final InetSocketAddress localAddress;
    private Channel acceptorChannel;//接收来自客服端的请求

    //private ChannelHandler handler=null;
    
    
    
  
    
    public Channel getAcceptorChannel() {
		return acceptorChannel;
	}
	public TCPServer(final ChannelType channelType,
                  final InetSocketAddress localAddress
    ) {
        this.channelType = channelType;
        this.localAddress = localAddress;
    }
	public TCPServer(ChannelType channelType,
            final int port
) {
  this.channelType = channelType;
  this.localAddress = new InetSocketAddress(port);                          
}
	
	

    public void start() {
    	System.out.println("TCPServer.start()"+localAddress);
        acceptorChannel = ServerChannelFactory.createAcceptorChannel(
                channelType, localAddress,new ServerHandler());
    }
	
    
    public void stop() {
    	if(acceptorChannel!=null)
        acceptorChannel.close().awaitUninterruptibly();
    }

	
	public void restart() {
		stop();
    	start();
		
	}
	
	public static void main(String[] args) {
		TCPServer server=new TCPServer(ChannelType.NIO, new InetSocketAddress("localhost", 8888));
		server.start();
	}
	
}
