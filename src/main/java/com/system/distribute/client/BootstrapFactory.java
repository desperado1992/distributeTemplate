package com.system.distribute.client;


import com.system.common.ChannelType;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioDatagramChannel;
import io.netty.channel.socket.oio.OioSocketChannel;


/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-28 上午11:30:45 
 * @function:客服端的bootstrap工厂 
 */
public class BootstrapFactory {

	//private static EventLoopGroup group=null;
	
    private BootstrapFactory() {
    }

    public static Bootstrap createBootstrap(final ChannelType channelType) throws UnsupportedOperationException {
        Bootstrap bootstrap = new Bootstrap();

        switch (channelType) {
            case NIO:
            
                bootstrap.group(new NioEventLoopGroup());
                bootstrap.channel(NioSocketChannel.class);
                return bootstrap;

            case OIO:
     
                bootstrap.group(new OioEventLoopGroup());
                bootstrap.channel(OioSocketChannel.class);
                return bootstrap;

            default:
                throw new UnsupportedOperationException("Failed to create Bootstrap,  " + channelType + " not supported!");
        }
    }
    
    public static Bootstrap createUDPBootstrap(final ChannelType channelType) throws UnsupportedOperationException {
        Bootstrap bootstrap = new Bootstrap();

        switch (channelType) {
            case NIO:
            	
                bootstrap.group(new NioEventLoopGroup());
                bootstrap.channel(NioDatagramChannel.class);
                return bootstrap;

            case OIO:
            	
                bootstrap.group(new OioEventLoopGroup());
                bootstrap.channel(OioDatagramChannel.class);
                return bootstrap;

            default:
                throw new UnsupportedOperationException("Failed to create Bootstrap,  " + channelType + " not supported!");
        }
    }
    
   
}
