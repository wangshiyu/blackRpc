package com.black.blackrpc.code.enums;
/**
 * 序列化方式
 * @author v_wangshiyu
 *
 */
public enum SerializationTypeEnum {
	/**
	 * 标准json方式
	 */
	Json(1),
	/**
	 * Msgpack方式
	 */
	Msgpack(2),
	/**
	 * Protostuff方式
	 */
	Protostuff(3);
	
	private int value;
	private SerializationTypeEnum(int value){
		this.value=value;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	public static SerializationTypeEnum getSerializationTypeEnum(int value){
		for(SerializationTypeEnum serializationType:SerializationTypeEnum.values()){
			if(serializationType.value==value){
				return serializationType;
			}
		}
		return null;
	}
}
