package com.black.blackrpc.code.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.black.blackrpc.code.base.entity.ProvideServiceBase;
import com.black.blackrpc.code.cache.ObjectCache;
import com.black.blackrpc.code.cache.ProvideServiceCache;
import com.black.blackrpc.common.configure.BreakRpcConfigure;
import com.black.blackrpc.common.util.MapUtil;
import com.black.blackrpc.common.util.StringUtil;
import com.black.blackrpc.communication.netty.tcp.service.NettyTcpService;
import com.black.blackrpc.zk.ZooKeeperOperation;
/**
 * 服务端核心初始化
 * @author v_wangshiyu
 *
 */
@Component
@Order(2)//使其在上下文监听之后加载
public class ServiceCodeInit  implements ApplicationListener<ContextRefreshedEvent>  {
    private static final Logger log = LoggerFactory.getLogger(ServiceCodeInit.class);
    @Autowired
    private BreakRpcConfigure breakRpcConfigure;
    /***
     * 初始化操作涉及：
     * 启动 netty，
     * 连接 zk，
     * 注册本地服务。
     * 执行时间：上下文监听之后加载
     * @throws Exception 
     */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("Service Code Init....");
    	//有需要发布的服务时开启netty服务端 和服务注册
    	if(MapUtil.isNotEmpty(ProvideServiceCache.provideServiceMap)){
			try {
				NettyTcpService nettyTcpService = new NettyTcpService("localhost",8888);
				nettyTcpService.start();
			} catch (Exception e) {
				e.printStackTrace();
			}	
    	}
    	
    	//服务端开启并且有需要发布的服务时开启服务注册
    	if(MapUtil.isNotEmpty(ProvideServiceCache.provideServiceMap)&&breakRpcConfigure.getServerOpen()!=null&&breakRpcConfigure.getServerOpen()){
    		String zkAddress= breakRpcConfigure.getZkAddress();
    		if(StringUtil.isNotEmpty(zkAddress)){
    			ZooKeeperOperation zo =new ZooKeeperOperation(zkAddress);
				if(zo.connectServer()){//连接
					zo.AddRootNode();
				}
				ObjectCache.zooKeeperOperation =zo;
				//zo.syncNodes();//测试使用
			}else{
    			throw new RuntimeException("zookeeper address is null!");
    		}
    		Map<String,ProvideServiceBase> provideServiceMap=ProvideServiceCache.provideServiceMap;
    		for(String key:provideServiceMap.keySet()){
    			ProvideServiceBase provideServiceBase= provideServiceMap.get(key);
    			//发布的时候加上权值
    			ObjectCache.zooKeeperOperation.createNode(key, breakRpcConfigure.getServerTcpAddress()+provideServiceBase.getZkDate());
    		}
    	}
    	log.info("Service Code Init Success!");
	}  
}