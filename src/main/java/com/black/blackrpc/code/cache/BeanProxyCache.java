package com.black.blackrpc.code.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.black.blackrpc.code.proxy.JdkMethodProxy;

/**
 * bean代理缓存
 * @author v_wangshiyu
 *
 */
public class BeanProxyCache {
	/**
	 * 发现的服务缓存
	 */
	public static volatile Map<String,Object> beanProxyMap =null;

	
	/**
	 * 初始化beanProxyMap
	 * @return
	 */
	public static boolean beanProxyMapInit(){
		if(beanProxyMap==null){
			beanProxyMap =new ConcurrentHashMap<String,Object>();
		}
		return true;
	}
}
