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

public class UDPClient {

    private final ChannelType channelType;
    private final InetSocketAddress remoteAddress;
    private int type=0;//类型 
    private Channel channel=null;//
    public UDPClient(ChannelType channelType, InetSocketAddress remoteAddress,int type) {
        this.channelType = channelType;
        this.remoteAddress = remoteAddress;
        this.type=type;
    }
    
    
    public UDPClient(ChannelType channelType, InetSocketAddress remoteAddress) {
		super();
		this.channelType = channelType;
		this.remoteAddress = remoteAddress;
	}


	/**
     * 
     * @param type type=0为 普通的  type=1 为广播  还有type=2为 组播 （待做）
     * @return
     * 添加（修改）人：zhuyuping
     * @throws Exception 
     */
    public  Channel start(){
    	try{
    	channel=ClientUDPChannelFactory.createConnectorChannel(
                channelType, new ClientUDPHandler(), remoteAddress,type);
    	channel.closeFuture().awaitUninterruptibly();
        return channel;
    	}catch(Exception e){
    		return null;
    	}
    }
    /**
     * 
     *   发送广播
     *     DatagramPacket udpPacket = new DatagramPacket(data,
                    new InetSocketAddress("255.255.255.255", ENV.NETTY_PORT));
            channel.writeAndFlush(udpPacket).sync();
     * 
     * 添加（修改）人：zhuyuping
     */

	public void stop() {
		if(channel!=null){ 
			channel.close().awaitUninterruptibly();
		    
		}
		
	}
   
    
    
}
