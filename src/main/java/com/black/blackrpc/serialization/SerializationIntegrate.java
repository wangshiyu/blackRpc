package com.black.blackrpc.serialization;

import java.util.List;

import com.black.blackrpc.code.enums.SerializationTypeEnum;
import com.black.blackrpc.serialization.json.JsonSerializationImpl;
import com.black.blackrpc.serialization.msgpack.MsgpackSerializationImpl;
import com.black.blackrpc.serialization.protostuff.ProtostuffSerializationImpl;
/**
 * 序列化集成
 * @author Administrator
 *
 */
public class SerializationIntegrate {

	private static Serialization serializationMsgpack =new MsgpackSerializationImpl();

	private static Serialization serializationProtostuff =new ProtostuffSerializationImpl();

	private static Serialization serializationJson = new JsonSerializationImpl();


	/**
	 * 序列化
	 * @param obj
	 * @param serializationType
	 * @return
	 */
	public static <T> byte[] serialize(T obj,SerializationTypeEnum serializationType) {
		switch(serializationType){
		case Msgpack:
			return serializationMsgpack.serialize(obj);
		case Protostuff:
			return serializationProtostuff.serialize(obj);
		case Json:
			return serializationJson.serialize(obj);
		}
		return null;
	}

	/**
	 * 反序列化
	 * @param obj
	 * @param serializationType
	 * @return
	 */
	public static <T> T deserialize(byte[] bytes, Class<T> cls,SerializationTypeEnum serializationType) {
		switch(serializationType){
		case Msgpack:
			return serializationMsgpack.deserialize(bytes,cls);
		case Protostuff:
			return serializationProtostuff.deserialize(bytes,cls);
		case Json:
			return serializationJson.deserialize(bytes,cls);
		}
		return null;
	}

	/**
	 * 反序列化为list集合
	 * @param bytes
	 * @param clazz
	 * @param serializationType
	 * @return
	 */
	public static <T> List<T> toList(byte[] bytes, Class<T> clazz,SerializationTypeEnum serializationType) {
		switch(serializationType){
		case Msgpack:
			return serializationMsgpack.toList(bytes,clazz);
		case Protostuff:
			return serializationProtostuff.toList(bytes,clazz);
		case Json:
			return serializationJson.toList(bytes,clazz);
		}
		return null;
	}
	
	
}
