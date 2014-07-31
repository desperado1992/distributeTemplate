package com.system.distribute.server;

import java.net.*;
import java.util.concurrent.TimeUnit;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;



import com.system.common.ChannelType;
import com.system.common.Resources;
import com.system.distribute.client.FileDecoder;
import com.system.distribute.client.FileEncoder;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-28 上午11:14:57 
 * @function:阻塞模式 与 非阻塞模式 的创建工厂
 */
final class ServerChannelFactory {

  

    protected static Channel createAcceptorChannel(
            final ChannelType channelType,
            final InetSocketAddress localAddress,
            final ServerHandler serverHandler
    ) {
    	
        final ServerBootstrap serverBootstrap = ServerBootstrapFactory.createServerBootstrap(channelType,false);

        serverBootstrap
                .childHandler(new ServerChannelInitializer(serverHandler))
                .option(ChannelOption.SO_BACKLOG, Resources.SO_BACKLOG)
                .childOption(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_KEEPALIVE,true);
        
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(
                    new InetSocketAddress(localAddress.getPort())).sync();
            channelFuture.awaitUninterruptibly();
            if (channelFuture.isSuccess()) {
                return channelFuture.channel();

            } else {
              
            }
        } catch (InterruptedException e) {
           
        }
        return null;
    }

    private static class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

        private ChannelHandler serverHandler;

        private ServerChannelInitializer(ChannelHandler serverHandler) {
            this.serverHandler = serverHandler;
        }

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            // Encoders
        	ChannelPipeline pipeline = ch.pipeline();
        	pipeline.addLast("ping",new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS));
            //pipeline.addLast(new HeartbeatHandler());
            initStringChannel(ch);
        }

		public void initStringChannel(SocketChannel ch) {
//			ch.pipeline().addLast(Resources.STRING_ENCODER_NAME, new StringEncoder(CharsetUtil.UTF_8));
//            ch.pipeline().addBefore(Resources.STRING_ENCODER_NAME, Resources.FRAME_ENCODER_NAME,
//                    new LengthFieldPrepender(Resources.FRAME_LENGTH_FIELD_SIZE));
//
//            // Decoders
			//ch.pipeline().addLast(new HttpServerCodec());
			//ch.pipeline().addLast(new HttpObjectAggregator(65536));

			//ch.pipeline().addLast(new FileEncoder());
			ch.pipeline().addLast("framedecoder",new LengthFieldBasedFrameDecoder(1024*1024*100, 0, 4,0,4));
			
			ch.pipeline().addLast(new FileDecoder());
          
            ch.pipeline().addLast(Resources.LOGGING_HANDLER_NAME, new LoggingHandler());
			  // pipleline.addLast("encode", new ObjectEncoder());  
              // pipleline.addLast("decode", new ObjectDecoder());  
//            ch.pipeline().addLast(Resources.STRING_DECODER_NAME, new StringDecoder(CharsetUtil.UTF_8));
//            ch.pipeline().addBefore(Resources.STRING_DECODER_NAME, Resources.FRAME_DECODER_NAME,
//                    new LengthFieldBasedFrameDecoder(Resources.MAX_FRAME_LENGTH,
//                            Resources.FRAME_LENGTH_FIELD_OFFSET, Resources.FRAME_LENGTH_FIELD_SIZE,
//                            Resources.FRAME_LENGTH_ADJUSTMENT, Resources.FRAME_LENGTH_FIELD_SIZE));

            // Handlers
            //ch.pipeline().addLast(Resources.LOGGING_HANDLER_NAME, new LoggingHandler(LogLevel.INFO));
            ch.pipeline().addLast(Resources.SERVER_HANDLER_NAME, serverHandler);
		}
		
		public static final class HeartbeatHandler extends ChannelInboundHandlerAdapter {
	        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(
	                Unpooled.copiedBuffer("ok", CharsetUtil.UTF_8));

	        @Override
	        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
	            if (evt instanceof IdleStateEvent) {
	                 ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
	                         .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
	            } else {
	                super.userEventTriggered(ctx, evt);
	            }
	        }
	    }
    }
}
