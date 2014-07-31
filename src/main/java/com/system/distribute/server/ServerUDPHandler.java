package com.system.distribute.server;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-30 下午4:18:58 
 * @function:接收UDP SimpleChannelInboundHandler<DatagramPacket> 的handler处理类 
 */
@Sharable
public class ServerUDPHandler extends ChannelInboundHandlerAdapter{
	

	
	
	
	


	
	


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
	

	}
	
	
	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		
		
	}

	

	
	
	
	
	
}
