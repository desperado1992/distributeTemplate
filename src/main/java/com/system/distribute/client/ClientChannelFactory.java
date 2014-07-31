package com.system.distribute.client;

import java.net.*;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;


import com.system.common.ChannelType;
import com.system.common.Resources;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-28 上午11:43:35 
 * @function:
 */
final class ClientChannelFactory {

   

    protected static Channel createConnectorChannel(
            ChannelType channelType,
            final ClientHandler clientHandler,
            InetSocketAddress remoteAddress
    ) {
    	
        final Bootstrap bootstrap = BootstrapFactory.createBootstrap(channelType);

        bootstrap.handler(new ClientChannelInitializer(clientHandler));
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);  
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);

        try {
            final ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(remoteAddress.getAddress(), remoteAddress.getPort()))
                    .sync();
            channelFuture.awaitUninterruptibly();
            if (channelFuture.isSuccess()) {
                return channelFuture.channel();

            } else {
                
                
            }
        } catch (InterruptedException e) {
            
        }
        return null;
    }

    private static class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

        private ChannelHandler clientHandler;

        private ClientChannelInitializer(ChannelHandler clientHandler) {
            this.clientHandler = clientHandler;
        }

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            initStringChannel(ch);
        }

		public void initStringChannel(SocketChannel ch) {
			// Encoders

			ch.pipeline().addLast(new FileEncoder());
			//ch.pipeline().addLast("framedecoder",new LengthFieldBasedFrameDecoder(1024*1024*100, 0, 4,0,4));
			//ch.pipeline().addLast(new FileDecoder());
            //ch.pipeline().addLast("fileencoder",new FileEncoder());
            // Decoders
           
            // Handlers
            ch.pipeline().addLast(Resources.LOGGING_HANDLER_NAME, new LoggingHandler());
            ch.pipeline().addLast(Resources.CLIENT_HANDLER_NAME, clientHandler);
		}
    }
}
