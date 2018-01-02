package com.black.blackrpc.common.util;

import java.util.Map;

/**
 * Map工具类
 * @author wangshiyu
 *
 */
public class MapUtil {
	/**
	 * 判断map是否为空
	 * @param <K>
	 * @param <V>
	 * @param list
	 * @return
	 */
	public static <K, V> boolean isEmpty(Map<K,V> map){
		return null == map ? true : map.isEmpty();
	}
	/**
	 * 判断map不为空
	 * @param list
	 * @return
	 */
	public static <K, V> boolean isNotEmpty(Map<K,V> map){
		return null != map ? !map.isEmpty() : false;
	}
}
