package com.black.blackrpc.lb;

import java.util.Date;

import com.black.blackrpc.code.base.entity.DiscoveryServiceBase;
import com.black.blackrpc.code.base.entity.NettyTcpConnectBase;
import com.black.blackrpc.code.base.entity.RemoteServiceBase;
import com.black.blackrpc.code.cache.InvokingServiceCache;
import com.black.blackrpc.code.cache.NettyConnectCache;
import com.black.blackrpc.code.cache.ObjectCache;
import com.black.blackrpc.code.enums.LoadBalanceStrategyEnum;
import com.black.blackrpc.code.exception.NullServerException;
import com.black.blackrpc.common.util.ListUtil;
import com.black.blackrpc.communication.netty.tcp.client.NettyTcpClient;
import com.black.blackrpc.lb.strategy.ClusterStrategy;
/**
 * 负载均衡
 * @author Administrator
 *
 */
public class LoadBalance {
	
	/**
	 * 获取tcp连接
	 * @param serverName
	 * @return
	 * @throws Exception 
	 */
	public static NettyTcpClient getTcpConnect(String serverName,LoadBalanceStrategyEnum loadBalanceStrategy) throws Exception{
		/***********执行对应策略************/
		ClusterStrategy clusterStrategy=null;
		switch (loadBalanceStrategy) {
		case Hash:
			clusterStrategy=ObjectCache.hashStrategy;
			break;
		case Polling:
			clusterStrategy=ObjectCache.pollingStrategy;
			break;
		case Random:
			clusterStrategy=ObjectCache.randomStrategy;
			break;
		case WeightingPolling:
			clusterStrategy=ObjectCache.weightingPollingStrategy;
			break;
		case WeightingRandom:
			clusterStrategy=ObjectCache.weightingRandomStrategy;
			break;	
		default:
			break;
		}
		DiscoveryServiceBase discoveryServiceBase=InvokingServiceCache.discoveryServiceMap.get(serverName);
		System.err.println(serverName);
		if(null==discoveryServiceBase||ListUtil.isEmpty(discoveryServiceBase.getAddressList())){
			throw new NullServerException("The service has no invocation address!!! =============>serverName:"+serverName);
		}
		RemoteServiceBase remoteServiceBase= clusterStrategy.select(discoveryServiceBase.getAddressList());
		/***********执行对应策略************/
		String address=remoteServiceBase.getAddress();
		/**
		 * 选择连接
		 */
		NettyTcpConnectBase nettyTcpConnectBase=NettyConnectCache.tcpConnectMap.get(remoteServiceBase.getAddress());
		
		if (null!=nettyTcpConnectBase) {
			nettyTcpConnectBase.setUpdateDate(new Date());
			nettyTcpConnectBase.setInvokingMum(nettyTcpConnectBase.getInvokingMum()+1);
			return nettyTcpConnectBase.getNettyTcpClient();	
		}else{
			//String[] address_=address.split(":");
			String host=remoteServiceBase.getHost();
			int port =remoteServiceBase.getPort();
			NettyTcpConnectBase newNettyTcpConnectBase =new NettyTcpConnectBase();
			newNettyTcpConnectBase.setHost(host);
			newNettyTcpConnectBase.setPort(port);
			Date date =new Date();
			newNettyTcpConnectBase.setCreateDate(date);
			newNettyTcpConnectBase.setUpdateDate(date);
			newNettyTcpConnectBase.setInvokingMum(1);
			NettyTcpClient nettyTcpClient=new NettyTcpClient(host,port);
			newNettyTcpConnectBase.setNettyTcpClient(nettyTcpClient);
			NettyConnectCache.tcpConnectMap.put(address, newNettyTcpConnectBase);
			return nettyTcpClient;
		}
	}
}
