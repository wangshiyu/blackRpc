package com.black.blackrpc.common.util;

import java.util.List;

/**
 * List工具类
 * @author v_wangshiyu
 *
 */
public class ListUtil {
	/**
	 * 判断list是否为空
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<?> list){
		return null == list ? true : list.isEmpty();
	}
	/**
	 * 判断list不为空
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty(List<?> list){
		return null != list ? !list.isEmpty() : false;
	}
	/**
	 * 判断arr是否为空
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(Object[] arr){
		return null == arr ? true : arr.length==0;
	}
	/**
	 * 判断arr不为空
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty(Object[] arr){
		return null == arr ? false : arr.length!=0;
	}
}
