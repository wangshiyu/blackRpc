package com.black.blackrpc.code.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.black.blackrpc.code.base.entity.ProvideServiceBase;

/**
 * 提供的服务缓存(服务端)
 * @author v_wangshiyu
 *
 */
public class ProvideServiceCache {
	public static volatile Map<String,ProvideServiceBase> provideServiceMap =null;
	/**
	 * 初始化provideServiceMap
	 * @return
	 */
	public static boolean provideServiceMapInit(){
		if(provideServiceMap==null){
			provideServiceMap =new ConcurrentHashMap<String, ProvideServiceBase>();
		}
		return true;
	}
}
