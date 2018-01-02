package com.black.blackrpc.communication.message;

import com.black.blackrpc.common.constant.SysterConstant;
import com.black.blackrpc.common.util.MumberByteUtil;

/**
 * 报文头
 * 长度为8个字节，
 * 前4个字节存放长度
 * 第5个字节存储 存放版本号 11，12，21
 * 第6个字节保留备用
 * 第7个字节存放加密方式
 * 第8个字节存放序列化方式
 * @author Administrator
 *
 */
public class Head {
	/**
	 * 长度
	 */
	private int length;
	
	/**
	 * 版本
	 */
	
	private static byte version=MumberByteUtil.intToByte(SysterConstant.VERSION);
	
	/**
	 * 保留备用
	 */
	private int reserve;
	
	/**
	 * 加密方式
	 */
	private int encryptionType;
	/**
	 * 序列化方式
	 */
	private int SerializationType;
	
	public Head(int length,int reserve,int encryptionType,int SerializationType){
		this.length=length;
		this.reserve=reserve;
		this.encryptionType=encryptionType;
		this.SerializationType=SerializationType;
	}
	
	/**
	 * 返回报文头
	 * @return
	 */
	public byte[] getHead() {
		byte[] head =new byte[8];
		byte[] length =MumberByteUtil.intToByteArray(this.length);
		System.arraycopy(length,0,head,0,length.length);
		head[4]=version;
		head[5]=MumberByteUtil.intToByte(reserve);
		head[6]=MumberByteUtil.intToByte(encryptionType);
		head[7]=MumberByteUtil.intToByte(SerializationType);
		return head;
	}
	/**
	 * 返回报文同时将报文体长度初始化好
	 * @return
	 */
	public byte[] getHeadData() {
		byte[] head_data =new byte[8+this.length];
		byte[] length =MumberByteUtil.intToByteArray(this.length);
		System.arraycopy(length,0,head_data,0,length.length);
		head_data[4]=version;
		head_data[5]=MumberByteUtil.intToByte(reserve);
		head_data[6]=MumberByteUtil.intToByte(encryptionType);
		head_data[7]=MumberByteUtil.intToByte(SerializationType);
		return head_data;
	}

	@Override
	public String toString() {
		return "Head [length=" + length +", version=" + version+ ", reserve=" + reserve + ", encryptionType=" + encryptionType
				+ ", SerializationType=" + SerializationType + "]";
	}
	
}
