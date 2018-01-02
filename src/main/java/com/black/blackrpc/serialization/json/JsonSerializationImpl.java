package com.black.blackrpc.serialization.json;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.black.blackrpc.code.base.entity.RpcRequest;
import com.black.blackrpc.serialization.Serialization;
/**
 * json 序列化
 * @author Administrator
 *
 */
public class JsonSerializationImpl implements Serialization {

	/**
	 * 序列化
	 */
	public <T> byte[] serialize(T obj) {
		return JSON.toJSONString(obj).getBytes();
	}

	/**
	 * 反序列化
	 */
	public <T> T deserialize(byte[] data, Class<T> cls) {
		// TODO Auto-generated method stub
		return JSON.parseObject(new String(data),cls); 
	}

	public <T> List<T> toList(byte[] bytes, Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static void main(String[] args) {
		JsonSerializationImpl jsonSerializationImpl =new JsonSerializationImpl(); 
	System.err.println(jsonSerializationImpl.deserialize(jsonSerializationImpl.serialize(new RpcRequest()), RpcRequest.class));	;
	}
}
