package com.system.distribute.client;



import com.google.common.base.Strings;
import com.system.distribute.file.monitor.FileDataMessage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-15 下午7:00:49 
 * @function:将文件转为字节码传输
 */
public class FileEncoder extends MessageToByteEncoder<FileDataMessage>{


	@Override
	protected void encode(ChannelHandlerContext ctx, FileDataMessage msg,
			ByteBuf out) throws Exception {
		//协议 
		//---head开始
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
	   
		msg.toBuffer(out);
	
		//out.writeBytes(msg.toBuffer());
	}

}
