package com.black.blackrpc.communication.netty.tcp.coder;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 自定义解码器
 * @author v_wangshiyu
 *
 */
@Deprecated
public class Decoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		byte[] redByte = new byte[in.readableBytes()];
		in.getBytes(0, redByte);
		String str = new String(redByte);
		System.err.println(str);
		in.readBytes(redByte.length);
		out.add(str);
	}

}
