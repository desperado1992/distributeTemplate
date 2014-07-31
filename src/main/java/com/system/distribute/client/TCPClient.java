package com.system.distribute.client;
import io.netty.channel.Channel;


import java.net.InetSocketAddress;

import com.system.common.ChannelType;
/*
 * *
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-28 上午11:19:47 
 * @function:客服端 测试的接口 
 */

public class TCPClient {
	


    
	public static Channel getChannel(ChannelType channelType,InetSocketAddress remoteAddress) {
       
		return ClientChannelFactory.createConnectorChannel(
                channelType,new ClientHandler() , remoteAddress);
      
    }
    
    
}
