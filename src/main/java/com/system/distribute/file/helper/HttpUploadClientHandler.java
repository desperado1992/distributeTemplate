package com.system.distribute.file.helper;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import com.google.common.collect.Queues;
import com.system.distribute.sqlparser.Query;


public class HttpUploadClientHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger logger = Logger.getLogger(HttpUploadClientHandler.class.getName());

    private boolean readingChunks;
    
    private final Queue results=Queues.newLinkedBlockingQueue();
    
    public Object getResult(){
    	
    	return null;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;

            if (response.getStatus().code() == 200 && HttpHeaders.isTransferEncodingChunked(response)) {
                readingChunks = true;
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent chunk = (HttpContent) msg;
            logger.info(chunk.content().toString(CharsetUtil.UTF_8));

            if (chunk instanceof LastHttpContent) {
               
                readingChunks = false;
            } else {
                chunk.content();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
