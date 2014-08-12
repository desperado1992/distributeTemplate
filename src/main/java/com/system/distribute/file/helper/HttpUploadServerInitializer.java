package com.system.distribute.file.helper;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

import com.system.distribute.core.Node;

public class HttpUploadServerInitializer extends ChannelInitializer<SocketChannel> {
	
	private Node node;
	
	
	
    public HttpUploadServerInitializer(Node node) {
		super();
		this.node = node;
	}



	@Override
    public void initChannel(SocketChannel ch) throws Exception {
        // Create a default pipeline implementation.
        ChannelPipeline pipeline = ch.pipeline();

        
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("encoder", new HttpResponseEncoder());

        // Remove the following line if you don't want automatic content
        // compression.
        pipeline.addLast("deflater", new HttpContentCompressor());

        pipeline.addLast("handler", new HttpUploadServerHandler(node));
    }
}
