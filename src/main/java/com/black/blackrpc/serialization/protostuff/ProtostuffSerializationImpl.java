package com.black.blackrpc.serialization.protostuff;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.black.blackrpc.code.base.entity.RpcRequest;
import com.black.blackrpc.code.base.entity.RpcResponse;
import com.black.blackrpc.serialization.Serialization;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
/**
 * Protostuff 序列化
 * @author v_wangshiyu
 *
 */
public class ProtostuffSerializationImpl implements Serialization {

	/**
	 * 序列化
	 */
	@SuppressWarnings("unchecked")
	public <T> byte[] serialize(T obj) {
		    Class<T> cls = (Class<T>) obj.getClass();
		    LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		    try {
		        Schema<T> schema = getSchema(cls);
		        return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
		    } catch (Exception e) {
		        throw new IllegalStateException(e.getMessage(), e);
		    } finally {
		        buffer.clear();
		    }
	}

	/**
	 * 反序列化
	 */
	public <T> T deserialize(byte[] bytes, Class<T> cls) {
		try {
	        T message = cls.newInstance();
	        Schema<T> schema = getSchema(cls);
	        ProtostuffIOUtil.mergeFrom(bytes, message, schema);
	        return message;
	    } catch (Exception e) {
	        throw new IllegalStateException(e.getMessage(), e);
	    }
	}
	
	/**
	 * 序列化为list
	 */
	public <T> List<T> toList(byte[] bytes, Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

	private static <T> Schema<T> getSchema(Class<T> cls) {
	    Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
	    if (schema == null) {
	        schema = RuntimeSchema.createFrom(cls);
	        if (schema != null) {
	            cachedSchema.put(cls, schema);
	        }
	    }
	    return schema;
	}
	
	public static void main(String[] args) {
		RpcRequest rpcRequest=new RpcRequest();
		RpcResponse rpcResponse =new RpcResponse();
		
		List<String> a= new ArrayList<String>();
		a.add("1111");
		a.add("2222");
		a.add("3333");
		
		Serialization serialization =new ProtostuffSerializationImpl();
		System.err.println(serialization.deserialize(serialization.serialize(rpcResponse), RpcResponse.class));
		
		System.err.println(serialization.deserialize(serialization.serialize(rpcRequest), RpcRequest.class));
	}
}
