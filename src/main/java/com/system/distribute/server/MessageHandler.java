package com.system.distribute.server;


import java.util.List;





import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;


public class MessageHandler extends  MessageToMessageDecoder<DatagramPacket>{
	
	
   
	
	@Override
	protected void decode(ChannelHandlerContext ctx, DatagramPacket msg,
			List<Object> out) throws Exception {
		
		 	
   }

	
	







	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		
		
		ctx.channel().close();
		
	}
	
	
	
	
}	
	
