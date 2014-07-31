package com.system.distribute.client;

import java.net.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.DatagramChannel;
import com.system.common.ChannelType;
import com.system.common.Resources;


/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-28 上午11:43:35 
 * @function:UDPchannel 工厂 
 */
final class ClientUDPChannelFactory {

   
    public static Bootstrap initConnectorBootstrap(ChannelType channelType,
            final ClientUDPHandler clientHandler){
				return BootstrapFactory.createUDPBootstrap(channelType).handler(new ClientUDPChannelInitializer(clientHandler));
    }
    
   
    
    protected static Channel createConnectorChannel(
            ChannelType channelType,
            final ClientUDPHandler clientHandler,
            InetSocketAddress remoteAddress, int type
    ) throws Exception {
    	
    	Channel channel =null;
        final Bootstrap bootstrap = BootstrapFactory.createUDPBootstrap(channelType);
        switch (type) {
		case 0:
			//单播
			bootstrap.option(ChannelOption.SO_BROADCAST, Boolean.FALSE);
			 bootstrap.handler(new ClientUDPChannelInitializer(clientHandler));
			//channel=bootstrap.connect(remoteAddress).sync().channel();
			 channel=bootstrap.bind(remoteAddress.getPort()).sync().channel();
			break;
		case 1:
			//多播
			 bootstrap.option(ChannelOption.SO_BROADCAST, Boolean.TRUE);
			 bootstrap.handler(new ClientUDPChannelInitializer(clientHandler));
			 channel=bootstrap.bind(new InetSocketAddress(0)).sync().channel();
			break;
			
		case 2:
			//组播
			//TODO 组播 管理 暂时未做
			
			break;
		default:
			break;
		}
        
        
        return channel;
    }
    
    
    
    
    
    @SuppressWarnings("unused")
	private static class ClientUDPChannelInitializer extends ChannelInitializer<DatagramChannel> {

        private ChannelHandler clientHandler;

        private ClientUDPChannelInitializer(ChannelHandler clientHandler) {
            this.clientHandler = clientHandler;
        }

       
		public void initStringChannel(final DatagramChannel ch) {
			
           // ch.pipeline().addLast(Resources.LOGGING_HANDLER_NAME, new LoggingHandler());
            ch.pipeline().addLast(Resources.CLIENT_HANDLER_NAME, clientHandler);
		}


		@Override
		protected void initChannel(DatagramChannel ch) throws Exception {
			
			initStringChannel(ch);
		}
    }

   
}
