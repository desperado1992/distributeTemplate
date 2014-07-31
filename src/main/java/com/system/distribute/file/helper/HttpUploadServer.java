package com.system.distribute.file.helper;

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
    public static boolean isSSL;

    public HttpUploadServer(int port) {
        this.port = port;
    }
    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new HttpUploadServerInitializer());

            Channel ch = b.bind(port).sync().channel();
          

            ch.closeFuture().sync();
        }catch(Exception e){
           throw new RuntimeException("文件同步服务器无法启动请检查端口是否占用!");
        	
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        if (args.length > 1) {
            isSSL = true;
        }
        new HttpUploadServer(port).run();
    }
}
