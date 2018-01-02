package com.black.blackrpc.code.invoking;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.black.blackrpc.code.base.entity.RpcRequest;
import com.black.blackrpc.code.base.entity.RpcResponse;
import com.black.blackrpc.code.cache.SyncFutureCatch;
import com.black.blackrpc.code.enums.LoadBalanceStrategyEnum;
import com.black.blackrpc.code.enums.SerializationTypeEnum;
import com.black.blackrpc.code.synchronization.SyncFuture;
import com.black.blackrpc.common.util.ListUtil;
import com.black.blackrpc.common.util.UUIDGenerator;
import com.black.blackrpc.communication.message.Head;
import com.black.blackrpc.communication.netty.tcp.client.NettyTcpClient;
import com.black.blackrpc.communication.netty.tcp.service.TcpServerHandler;
import com.black.blackrpc.lb.LoadBalance;
import com.black.blackrpc.serialization.SerializationIntegrate;

/**
 * 远程调用
 * @author v_wangshiyu
 *
 */
public class RemoteInvoking {
	private static final Logger log = LoggerFactory.getLogger(TcpServerHandler.class);

	/**
	 * 调用
	 * @param serviceName
	 * @param serializationType
	 * @param method
	 * @param args
	 * @return
	 * @throws Exception 
	 */
	public static Object invoking(String serviceName,SerializationTypeEnum serializationType,long timeOut,LoadBalanceStrategyEnum loadBalanceStrategy,Method method,Object[] parameters) throws Exception{
		RpcRequest rpcRequest =new RpcRequest();
		rpcRequest.setServiceName(serviceName);
		rpcRequest.setMethodName(method.getName());
		rpcRequest.setParameterTypes(ListUtil.isEmpty(method.getParameterTypes())?null:method.getParameterTypes());//如果参数数组为空,添加一个null，有些序列化不识别空的数组
		rpcRequest.setParameters(parameters);
		rpcRequest.setRequestId(UUIDGenerator.generate());
		/********定义报文头，组装数据*******/
		byte[] data=SerializationIntegrate.serialize(rpcRequest, serializationType);
		Head head =new Head(data.length,0,0,serializationType.getValue());
		byte[] head_data=head.getHeadData();
		System.arraycopy(data,0,head_data,8,data.length);
		/********定义报文头，组装数据*******/
		
		/*******LB*******/
		NettyTcpClient nettyTcpClient= LoadBalance.getTcpConnect(serviceName,loadBalanceStrategy);
		nettyTcpClient.sendMsg(head_data);
		log.debug("Tcp Client send head："+head+"Tcp Client send data：" +rpcRequest);
		/*******LB*******/
		
		SyncFuture<RpcResponse> syncFuture =new SyncFuture<RpcResponse>();
		SyncFutureCatch.syncFutureMap.put(rpcRequest.getRequestId(), syncFuture);
		RpcResponse rpcResponse= syncFuture.get(timeOut,TimeUnit.MILLISECONDS);
		SyncFutureCatch.syncFutureMap.remove(rpcRequest.getRequestId());
		return rpcResponse.getResult();
	}
}
