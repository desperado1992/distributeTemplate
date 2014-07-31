package com.system.distribute.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.channel.ChannelHandler.Sharable;


/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-28 上午11:18:56 
 * @function:客服端回调 TCp使用
 */
@Sharable
public final class ClientHandler extends ChannelInboundHandlerAdapter {

    
    

    @Override
    public void channelActive(io.netty.channel.ChannelHandlerContext ctx){
       
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) {
        
        System.out.println("接收到来自 服务器段的返回数据 ============ " +msg);
             
    }

    
    
   

	@Override
    public void channelInactive(final ChannelHandlerContext ctx) {
        //FileUtils.write(Resources.CLIENT_OUTPUT, String.format("Received %d messages", noMessagesReceived));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
       
    }
}
