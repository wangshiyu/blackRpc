package com.black.blackrpc.communication.netty.tcp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.black.blackrpc.code.base.entity.RpcResponse;
import com.black.blackrpc.code.cache.SyncFutureCatch;
import com.black.blackrpc.code.enums.SerializationTypeEnum;
import com.black.blackrpc.code.synchronization.SyncFuture;
import com.black.blackrpc.communication.message.HeadAnalysis;
import com.black.blackrpc.serialization.SerializationIntegrate;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端处理器
 */
public class TcpClientHandler extends SimpleChannelInboundHandler<Object>{
	private static final Logger log = LoggerFactory.getLogger(TcpClientHandler.class);
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		byte[] head_data=(byte[])msg;
		HeadAnalysis headAnalysis =new HeadAnalysis(head_data);
		if(head_data.length!=headAnalysis.getLength()+8){
			throw new Exception("TcpClien Receive Data Length is not Agreement!!!");
		}
		byte[] data=new byte[headAnalysis.getLength()];
		System.arraycopy(head_data,8,data,0,data.length);
		RpcResponse rpcResponse=null;
		try {
			rpcResponse= SerializationIntegrate.deserialize(data, RpcResponse.class, SerializationTypeEnum.getSerializationTypeEnum(headAnalysis.getSerializationType()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("Tcp Client receive head："+headAnalysis+"Tcp Client receive data：" +rpcResponse);
		SyncFuture<RpcResponse> syncFuture= SyncFutureCatch.syncFutureMap.get(rpcResponse.getRequestId());
		if(syncFuture!=null){
			syncFuture.setResponse(rpcResponse);
		}
	}

}
