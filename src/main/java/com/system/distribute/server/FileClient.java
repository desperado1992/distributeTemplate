/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.system.distribute.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import io.netty.channel.ChannelOption;

import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;

import com.system.common.ChannelType;
import com.system.distribute.client.BootstrapFactory;


/**
 * Server that accept the path of a file an echo back its content.
 */
public class FileClient {

    private final int port;
    private final String host;

    public FileClient(String host,int port) {
        this.host=host;
    	this.port = port;
    }

    public ChannelFuture run() throws Exception {
        // Configure the server.
    	  final Bootstrap bootstrap = BootstrapFactory.createBootstrap(ChannelType.NIO);

          bootstrap.handler(new FileClientHandler());
          bootstrap.option(ChannelOption.TCP_NODELAY, true);
          bootstrap.option(ChannelOption.SO_KEEPALIVE, true);  
          bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);

          try {
              final ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(host,port))
                      .sync();
              channelFuture.awaitUninterruptibly();
             
          } catch (InterruptedException e) {
             
          }

            // Start the server.
            ChannelFuture f = bootstrap.bind(port).sync();
            
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
            return f;
        
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        ChannelFuture fu=new FileClient("localhost",port).run();
        //
    }
    @Sharable
    private static final class FileClientHandler extends SimpleChannelInboundHandler<String> {
       

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, String msg)
				throws Exception {
			 
			System.out.println("FileClient.FileHandler.channelRead0() "+msg);
		}
    }
}
