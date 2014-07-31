package com.system.distribute.client;


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Date;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
/*
 * *
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-3-28 上午11:19:47 
 * @function:客服端 测试的接口 
 */

import com.system.common.ChannelType;

public class UDPMulticastClient {

    private final ChannelType channelType;
    private final InetSocketAddress address;
 
   
    
    
    public UDPMulticastClient(ChannelType channelType, InetSocketAddress address) {
        this.channelType = channelType;
        this.address = address;
    }

    
    private void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {

           
            InetAddress hostAddr = InetAddress.getLocalHost();
            NetworkInterface mcIf = NetworkInterface.getByInetAddress(hostAddr);

            InetAddress groupSocket = InetAddress.getByName("224.0.0.1");
            //InetSocketAddress gangliaSocket = new InetSocketAddress(GROUP, PORT);
               
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                .group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.IP_MULTICAST_IF,mcIf)

                .localAddress(address)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    public void initChannel(Channel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new SimpleChannelInboundHandler<DatagramPacket>(){

							@Override
							protected void channelRead0(
									ChannelHandlerContext ctx,
									DatagramPacket msg) throws Exception {
							    
								
							}
                        	
                        });
                    }
                })
            ;

            ChannelFuture gangliaFuture = bootstrap.bind().sync();
            // DatagramChannel cc = cc.leaveGroup(groupAddress, NetUtil.LOOPBACK_IF).sync();
            //logger.info("Netty listening on udp " + gangliaFuture.channel().localAddress());
            DatagramChannel channel = (DatagramChannel) gangliaFuture.channel();

            channel.joinGroup(new InetSocketAddress(groupSocket, address.getPort()), mcIf).sync().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                       // logger.error("Join failed: " + future.cause());
                    }
                    else {
                        //logger.info("Joined the group");
                    }
                }
            });
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
		UDPMulticastClient client=new UDPMulticastClient(ChannelType.NIO,new InetSocketAddress("192.168.16.145", 8888));
	    client.run();
    }
    public static void test() throws Exception{  
        InetAddress group = InetAddress.getByName("224.0.0.2");//组播地址  
        int port = 8888;  
        MulticastSocket msr = null;//创建组播套接字  
        try {  
            msr = new MulticastSocket(port);  
            msr.joinGroup(group);//加入连接  
            byte[] buffer = new byte[8192];  
            System.out.println("接收数据包启动！(启动时间: "+new Date()+")");  
            while(true){  
                //建立一个指定缓冲区大小的数据包  
            	java.net.DatagramPacket dp = new java.net.DatagramPacket(buffer, buffer.length);  
                msr.receive(dp);  
                String s = new String(dp.getData(),0,dp.getLength());  
                //解码组播数据包  
                System.out.println(s);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            if(msr!=null){  
                try {  
                    msr.leaveGroup(group);  
                    msr.close();  
                } catch (Exception e2) {  
                    // TODO: handle exception  
                }  
            }  
        }  
    }  
    
    public static void server() throws Exception{  
        InetAddress group = InetAddress.getByName("224.0.0.2");//组播地址  
        int port = 8888;  
        MulticastSocket mss = null;  
        try {  
            mss = new MulticastSocket(port);  
            mss.joinGroup(group);  
            System.out.println("发送数据包启动！（启动时间"+new Date()+")");  
              
            while(true){  
                String message = "Hello "+new Date();  
                byte[] buffer = message.getBytes();  
                java.net.DatagramPacket dp = new java.net.DatagramPacket(buffer, buffer.length,group,port);  
                mss.send(dp);  
                System.out.println("发送数据包给 "+group+":"+port);  
                Thread.sleep(1000);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                if(mss!=null){  
                    mss.leaveGroup(group);  
                    mss.close();  
                }  
            } catch (Exception e2) {  
                // TODO: handle exception  
            }  
        }  
    }  
}
