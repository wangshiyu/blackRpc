package com.black.blackrpc.serialization.msgpack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.msgpack.jackson.dataformat.MessagePackFactory;

import com.black.blackrpc.serialization.Serialization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Msgpack 序列化实现
 * @author v_wangshiyu
 *
 */
public class MsgpackSerializationImpl implements Serialization {
	private static ObjectMapper mapper = new ObjectMapper(new MessagePackFactory());
	
	/**
	 * 序列化
	 */
	public <T> byte[] serialize(T obj) {
		try {
            return mapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
		return null;
	}

	/**
	 * 反序列化
	 */
	public <T> T deserialize(byte[] bytes, Class<T> cls) {
	  T value = null;
        try {
            value = mapper.readValue(bytes, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
	}

 
	/**
     * @Title: toList
     * @Description: byte转list集合
     * @author Jecced
     * @param bytes
     * @param clazz
     * @return
     */
    public <T> List<T> toList(byte[] bytes, Class<T> clazz) {
        List<T> list = null;
        try {
            list = mapper.readValue(bytes, mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz));
        } catch (IOException e) {
            list = new ArrayList<T>();
            e.printStackTrace();
        }
        return list;
    }
    
    public static void main(String[] args) {
		List<String> a= new ArrayList<String>();
		a.add("1111");
		a.add("2222");
		a.add("3333");
		
		Serialization serialization =new MsgpackSerializationImpl();
		System.err.println(serialization.toList(serialization.serialize(a), String.class));
	}
}
