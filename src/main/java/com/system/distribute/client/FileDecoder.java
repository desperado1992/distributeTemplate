package com.system.distribute.client;

import java.util.List;

import com.google.common.io.ByteStreams;
import com.system.distribute.file.monitor.FileDataMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class FileDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		//协议 
				//---head开始
		        //0 总长度                           4
				// 1.head 2位 short int  2
				//1.sesionid 4位 int     4
				//2.hash  4为 int        4
				//3.pathlenth 为 多少位？ 4
				//4.blenth 为多少位 ?     4
				//8. start 4字节                4
				//9. end 4字节                   4
				//----body开始
				//5.command 1个字节          1
				//6. path byte[]        ？
				//7. byte[]             ?
		System.out.println("FileDecoder.decode() "+in.readableBytes());
//		if(in.readableBytes()<4) return;
//		 int size=in.readInt();
//		System.out.println("size is "+size);
//		 if(in.readableBytes()<size) return;
		int head=in.readShort();
		if(head!=1314){
			ctx.channel().disconnect();
			return;
		}
		int sessionId=in.readInt();
		int hash=in.readInt();
		int pathl=in.readInt();
		int blenth=in.readInt();
		int start=in.readInt();
		int end=in.readInt();
		int command=in.readByte();
		byte[] p=new byte[pathl]; 
		if(in.readableBytes()<=pathl) return;
		in.readBytes(p);
		String path=new String(p);
		byte[] b=new byte[blenth];
		if(in.readableBytes()<blenth) return;
		in.readBytes(b);
		FileDataMessage fm=new FileDataMessage();
		fm.setBytes(b);
		fm.setPath(path);
		fm.setHead(head);
		fm.setEnd(end);
		fm.setHash(hash);
		fm.setLenth(blenth);
		fm.setSessionId(sessionId);
		fm.setCommand(command);
		fm.setStart(start);
		System.out.println("FileDecoder.decode result "+fm);
		out.add(fm);
	}

}
