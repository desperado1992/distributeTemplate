package com.system.distribute.client;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;




/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-28 上午11:18:56 
 * @function:客服端回调 udp使用
 */

public final class ClientUDPHandler extends ChannelInboundHandlerAdapter {

   
    

    @Override
    public void channelActive(ChannelHandlerContext ctx){
      System.out.println("ClientUDPHandler.channelActive()");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) {
    	
                                          
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) {
        //FileUtils.write(Resources.CLIENT_OUTPUT, String.format("Received %d messages", noMessagesReceived));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        
    }
}
