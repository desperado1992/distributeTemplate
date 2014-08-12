package com.system.distribute.file.helper;

import com.system.distribute.core.Node;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * A HTTP server showing how to use the HTTP multipart package for file uploads and decoding post data.
 */
public class HttpUploadServer implements Runnable{


    private final int port;
   
    private Node node;

  
    
    
    public HttpUploadServer(int port, Node node) {
		super();
		this.port = port;
		this.node = node;
	}

	@Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new HttpUploadServerInitializer(node));

            Channel ch = b.bind(port).sync().channel();
          

            ch.closeFuture().sync();
        }catch(Exception e){
           throw new RuntimeException("文件同步服务器无法启动请检查端口是否占用!");
        	
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

   
}
