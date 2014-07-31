package com.system.distribute.server;

import java.net.InetSocketAddress;



import com.system.common.ChannelType;


import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioDatagramChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-28 上午11:14:33 
 * @function:工厂模式  UDP服务器 bootstrap创建
 */
public class ServerUDPBootstrapFactory {

    private ServerUDPBootstrapFactory() {
    }

    public static Bootstrap createServerBootstrap(final ChannelType channelType) throws UnsupportedOperationException {
        
    	Bootstrap serverBootstrap = new Bootstrap();

        switch (channelType) {
            case NIO:
                serverBootstrap.group(new NioEventLoopGroup(Runtime.getRuntime().availableProcessors()));
                serverBootstrap.channel(NioDatagramChannel.class);
               // serverBootstrap.localAddress(new InetSocketAddress(port))
               // .handler(packetHandler);
              
                return serverBootstrap;

            case OIO:
            	 serverBootstrap.group(new OioEventLoopGroup(Runtime.getRuntime().availableProcessors()));
                 serverBootstrap.channel(OioDatagramChannel.class);
              
                
                return serverBootstrap;

            default:
                throw new UnsupportedOperationException("Failed to create ServerBootstrap,  " + channelType + " not supported!");
        }
    }
}
