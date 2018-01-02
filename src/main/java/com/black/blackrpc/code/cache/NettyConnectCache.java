package com.black.blackrpc.code.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.black.blackrpc.code.base.entity.NettyTcpConnectBase;

/**
 * Netty连接缓存
 * @author v_wangshiyu
 *
 */
public class NettyConnectCache {
	/**
	 * 一个服务名称可能存在多个提供地址
	 */
	public static volatile Map<String,NettyTcpConnectBase> tcpConnectMap =null;
	public static volatile Map<String,Object> udpConnectMap =null;
	
	/**
	 * 初始化tcpConnectMap
	 * @return
	 */
	public static boolean tcpConnectCacheInit(){
		if(tcpConnectMap==null){
			tcpConnectMap =new ConcurrentHashMap<String, NettyTcpConnectBase>();
		}
		return true;
	}
	/**
	 * 初始化udpConnectMap
	 * @return
	 */
	public static boolean udpConnectMapInit(){
		if(udpConnectMap==null){
			udpConnectMap =new ConcurrentHashMap<String, Object>();
		}
		return true;
	} 
}

