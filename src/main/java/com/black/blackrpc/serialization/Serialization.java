package com.black.blackrpc.serialization;

import java.util.List;

/**
 * 序列化接口
 * @author v_wangshiyu
 *
 */
public interface Serialization {

	/**
	 * 序列化
	 * @param obj
	 * @return
	 */
	public <T> byte[] serialize(T obj);
	
	/**
	 * 反序列化
	 * @param data
	 * @param cls
	 * @return
	 */
	public <T> T deserialize(byte[] bytes, Class<T> cls);
	
	/**
	 * 反序列化为list集合
	 * @param bytes
	 * @param clazz
	 * @return
	 */
	public <T> List<T> toList(byte[] bytes, Class<T> clazz);
	   
}
