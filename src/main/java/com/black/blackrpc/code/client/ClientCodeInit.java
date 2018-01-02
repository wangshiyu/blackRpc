package com.black.blackrpc.code.client;

import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.black.blackrpc.code.base.entity.InvokingServiceBase;
import com.black.blackrpc.code.cache.InvokingServiceCache;
import com.black.blackrpc.code.cache.NettyConnectCache;
import com.black.blackrpc.code.cache.ObjectCache;
import com.black.blackrpc.code.cache.SyncFutureCatch;
import com.black.blackrpc.code.proxy.JdkProxy;
import com.black.blackrpc.common.configure.BreakRpcConfigure;
import com.black.blackrpc.common.util.StringUtil;
import com.black.blackrpc.zk.ZooKeeperOperation;
/**
 * 客户端端核心初始化
 * @author v_wangshiyu
 *
 */
@Component
@Order(3)//使其在上下文监听之和服务注册之后加载
public class ClientCodeInit  implements ApplicationListener<ContextRefreshedEvent>  {
    private static final Logger log = LoggerFactory.getLogger(ClientCodeInit.class);
    @Autowired
    private BreakRpcConfigure breakRpcConfigure;
    /***
     * 初始化操作涉及：
     * 连接 zk，
     * 发现远程服务。
     * 创建代理
     * 执行时间：上下文监听之后加载
     * @throws Exception 
     */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("Client Code Init...");
		/************连接zookeepre 并同步记录**************/
		String zkAddress= breakRpcConfigure.getZkAddress();
		if(StringUtil.isNotEmpty(zkAddress)){
			if(ObjectCache.zooKeeperOperation==null){
				ZooKeeperOperation zo =new ZooKeeperOperation(zkAddress);
				zo.connectServer();
				ObjectCache.zooKeeperOperation =zo;
			}
			ObjectCache.zooKeeperOperation.syncNodes();//同步Nodes
		}else{
			throw new RuntimeException("zookeeper address is null!");
		}
		/************连接zookeepre 并同步记录**************/
		
		
		/************创建调用服务代理**************/
		List<InvokingServiceBase> invokingServiceList =InvokingServiceCache.invokingServiceList;
		for(InvokingServiceBase invokingServiceBase:invokingServiceList){
			Class<?> cls =invokingServiceBase.getCls();
			Object obj=invokingServiceBase.getBean();
			try {
				Field field = obj.getClass().getDeclaredField(invokingServiceBase.getFieldName());
				field.setAccessible(true);
				field.set(obj,JdkProxy.getInstance(cls,invokingServiceBase.getServiceName(),invokingServiceBase.getSerializationType(),invokingServiceBase.getTimeOut(),invokingServiceBase.getLoadBalanceStrategy()));//注入动态代理
				field.setAccessible(false);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		/************创建调用服务代理**************/
		
		/************初始化netty连接缓存**************/
		NettyConnectCache.tcpConnectCacheInit();
		/************初始化netty连接缓存**************/
		
		/************初始化同步结果缓存**************/
		SyncFutureCatch.syncFutureMapInit();
		/************初始化同步结果缓存**************/
		
		
    	log.info("Client Code Init Success!");
	}  
}