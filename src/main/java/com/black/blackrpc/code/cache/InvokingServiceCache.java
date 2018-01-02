package com.black.blackrpc.code.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.black.blackrpc.code.base.entity.DiscoveryServiceBase;
import com.black.blackrpc.code.base.entity.InvokingServiceBase;
import com.black.blackrpc.code.base.entity.RemoteServiceBase;
import com.black.blackrpc.code.enums.SerializationTypeEnum;
import com.black.blackrpc.common.constant.ZkConstant;

/**
 * 调用的服务缓存(客户端)
 * @author v_wangshiyu
 *
 */
public class InvokingServiceCache {
	private static final Logger log = LoggerFactory.getLogger(InvokingServiceCache.class);
	/**
	 * 发现的服务缓存
	 */
	public static volatile Map<String,DiscoveryServiceBase> discoveryServiceMap =null;
	/**
	 * 调用的服务缓存
	 */
	public static volatile List<InvokingServiceBase> invokingServiceList =null;
	/**
	 * 初始化discoveryServiceMap
	 * @return
	 */
	public static boolean discoveryServiceMapInit(){
		if(discoveryServiceMap==null){
			discoveryServiceMap =new ConcurrentHashMap<String,DiscoveryServiceBase>();
		}
		return true;
	}
	/**
	 * 初始化invokingServiceList
	 * @return
	 */
	public static boolean invokingServiceListInit(){
		if(invokingServiceList==null){
			invokingServiceList= Collections.synchronizedList(new ArrayList<InvokingServiceBase>());
		}
		return true;
	}
	/**
	 * 更新invoking缓存
	 * @param map
	 * @return
	 */
	public static void updataInvokingServiceMap(Map<String,List<String>> map){
		Map<String,DiscoveryServiceBase> discoveryServiceMapTemp= new ConcurrentHashMap<String,DiscoveryServiceBase>();
		for(String key: map.keySet()){
			DiscoveryServiceBase discoveryServiceBase =new DiscoveryServiceBase();
			discoveryServiceBase.setServiceName(key);
			List<String> listDate =(List<String>) map.get(key);
			List<RemoteServiceBase> addressList=new ArrayList<RemoteServiceBase>();
			for(String zkDta:listDate){
				String[] zkDtaArr=zkDta.split(ZkConstant.DELIMITED_MARKER_Str);
				String[] host_port=zkDtaArr[0].split(":");
				RemoteServiceBase remoteServiceBase =new RemoteServiceBase();
				remoteServiceBase.setHost(host_port[0]);
				remoteServiceBase.setPort(Integer.valueOf(host_port[1]));
				remoteServiceBase.setSerializationType(SerializationTypeEnum.valueOf(zkDtaArr[1]));
				remoteServiceBase.setWeight(Double.valueOf(zkDtaArr[2]));
				addressList.add(remoteServiceBase);
			}
			discoveryServiceBase.setAddressList(addressList);
			discoveryServiceMapTemp.put(key, discoveryServiceBase);
			log.debug("invoking service cache update date:"+discoveryServiceBase);
		}
		discoveryServiceMap=discoveryServiceMapTemp;
		log.debug("invoking service cache update finish!");
	}	
}
