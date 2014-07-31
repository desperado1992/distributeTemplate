package com.system.distribute.server;
import java.util.Date;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;



import com.system.distribute.file.monitor.FileDataMessage;
/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-28 上午11:16:13 
 * @function:服务器处理回调
 */
import io.netty.channel.ChannelHandler.Sharable;
@Sharable
final class ServerHandler extends ChannelInboundHandlerAdapter {

    


    @Override
    public void channelActive(ChannelHandlerContext ctx){
      
      
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
       
        System.out.println(msg);
      
        
    }

    @Override
   	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
   			throws Exception {
       	if (evt instanceof IdleStateEvent) {
               IdleStateEvent event = (IdleStateEvent) evt;
               if (event.state().equals(IdleState.READER_IDLE)) {
                   System.out.println("READER_IDLE");
                   // 超时关闭channel
                   ctx.close();
               } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                   System.out.println("WRITER_IDLE");
               } else if (event.state().equals(IdleState.ALL_IDLE)) {
                   System.out.println("ALL_IDLE");
                   // 发送心跳
                   ctx.channel().writeAndFlush(Unpooled.wrappedBuffer("ok".getBytes()));
               }
           }
           super.userEventTriggered(ctx, evt); 
   		
   	}

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
       
        cause.printStackTrace();
    }
}
