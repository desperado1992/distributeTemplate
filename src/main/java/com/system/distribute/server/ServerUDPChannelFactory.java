package com.system.distribute.server;

import java.net.*;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.DatagramChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;



import com.system.common.ChannelType;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-28 上午11:14:57 
 * @function:阻塞模式 与 非阻塞模式 的创建工厂
 */
final class ServerUDPChannelFactory {

    

    protected static Channel createAcceptorChannel(
            final ChannelType channelType,
            final InetSocketAddress localAddress,
            final ServerUDPHandler serverHandler
    ) {
        final Bootstrap serverBootstrap = ServerUDPBootstrapFactory.createServerBootstrap(channelType);

        serverBootstrap
                .option(ChannelOption.SO_REUSEADDR, false)
             
                
    		    .handler(new ChannelInitializer<DatagramChannel>() {
                    @Override
                    protected void initChannel(final DatagramChannel ch) throws Exception {
                        final ChannelPipeline pipeline = ch.pipeline();
                       //pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(60));
                        pipeline.addLast("messageDecoder", serverHandler);
                        //pipeline.addLast("handler", serverHandler);
                    }
                });
      
        try {
        	
            ChannelFuture channelFuture = serverBootstrap.bind(
                    new InetSocketAddress(localAddress.getPort())).sync();
            
            //channelFuture.channel().closeFuture().awaitUninterruptibly();//.awaitUninterruptibly();
            channelFuture.awaitUninterruptibly();
            if (channelFuture.isSuccess()) {
                return channelFuture.channel();

            } else {
                
            }
        } catch (InterruptedException e) {
            
        }
		return null;
       
    }

    protected static Channel createAcceptorChannel(
            final ChannelType channelType,
            final InetSocketAddress localAddress,
            final MessageHandler handler
    ) {
        final Bootstrap serverBootstrap = ServerUDPBootstrapFactory.createServerBootstrap(channelType);

        serverBootstrap
                .option(ChannelOption.SO_REUSEADDR, false)
             
                
    		    .handler(new ChannelInitializer<DatagramChannel>() {
                    @Override
                    protected void initChannel(final DatagramChannel ch) throws Exception {
                        final ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("messageDecoder", handler);
                        //pipeline.addLast("handler", serverHandler);
                    }
                });
        
        try {
        	
            ChannelFuture channelFuture = serverBootstrap.bind(
                    new InetSocketAddress(localAddress.getPort())).sync();
            
            //channelFuture.channel().closeFuture().awaitUninterruptibly();//.awaitUninterruptibly();
            channelFuture.awaitUninterruptibly();
            if (channelFuture.isSuccess()) {
                return channelFuture.channel();

            } else {
               
            }
        } catch (InterruptedException e) {
           
            //eventLoopGroup.shutdownGracefully();
            //System.exit(-1);
        }
		return null;
       
    }

}
