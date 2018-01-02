package com.black.blackrpc.communication.netty.tcp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.black.blackrpc.code.base.entity.ProvideServiceBase;
import com.black.blackrpc.code.base.entity.RpcRequest;
import com.black.blackrpc.code.base.entity.RpcResponse;
import com.black.blackrpc.code.cache.ProvideServiceCache;
import com.black.blackrpc.code.enums.SerializationTypeEnum;
import com.black.blackrpc.code.invoking.BeanInvoking;
import com.black.blackrpc.common.constant.ErrorConstant;
import com.black.blackrpc.communication.message.Head;
import com.black.blackrpc.communication.message.HeadAnalysis;
import com.black.blackrpc.serialization.SerializationIntegrate;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TcpServerHandler extends SimpleChannelInboundHandler<Object>{
	private static final Logger log = LoggerFactory.getLogger(TcpServerHandler.class);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		RpcResponse rpcResponse =new RpcResponse();
		byte[] head_data=(byte[])msg;
		HeadAnalysis headAnalysis =new HeadAnalysis(head_data);
		if(head_data.length!=headAnalysis.getLength()+8){
			throw new Exception("TcpServer Receive Data Length is not Agreement!!!");
		}
		byte[] data=new byte[headAnalysis.getLength()];
		System.arraycopy(head_data,8,data,0,data.length);
		SerializationTypeEnum serializationType= SerializationTypeEnum.getSerializationTypeEnum(headAnalysis.getSerializationType());
		RpcRequest rpcRequest= SerializationIntegrate.deserialize(data, RpcRequest.class,serializationType);
		//如果参数数组为null,添加一个空的数组，有些序列化不识别空的数组
		rpcRequest.setParameterTypes(rpcRequest.getParameterTypes()==null?new Class<?>[0]:rpcRequest.getParameterTypes());
		
		log.debug("Tcp Server receive head："+headAnalysis+"Tcp Server receive data：" +rpcRequest);
        
		ProvideServiceBase provideServiceBase= ProvideServiceCache.provideServiceMap.get(rpcRequest.getServiceName());
		rpcResponse.setRequestId(rpcRequest.getRequestId());
		if(provideServiceBase!=null){
			rpcResponse.setResult(BeanInvoking.invoking(provideServiceBase.getBean(), rpcRequest.getMethodName(), rpcRequest.getParameterTypes(), rpcRequest.getParameters()));
		}else{
			rpcResponse.setError(ErrorConstant.Servicer222);
		}
		
		/********定义报文头，组装数据*******/
		byte[] response_data=SerializationIntegrate.serialize(rpcResponse, serializationType);
		
		Head response_head =new Head(response_data.length,0,0,serializationType.getValue());
		byte[] response_head_data=response_head.getHeadData();
		System.arraycopy(response_data,0,response_head_data,8,response_data.length);
		/********定义报文头，组装数据*******/
		
		ctx.channel().writeAndFlush(response_head_data);
		log.debug("Tcp Server send head："+response_head+"Tcp Server send data：" +rpcResponse);
	}

}
