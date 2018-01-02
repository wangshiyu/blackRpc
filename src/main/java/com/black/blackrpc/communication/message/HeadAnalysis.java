package com.black.blackrpc.communication.message;

import com.black.blackrpc.common.constant.SysterConstant;
import com.black.blackrpc.common.util.MumberByteUtil;

/**
 * 报文头解析
 * 长度为8个字节，
 * 前4个字节存放长度
 * 第5个字节存储 存放版本号 11，12，21
 * 第6个字节保留备用
 * 第7个字节存放加密方式
 * 第8个字节存放序列化方式
 * @author Administrator
 *
 */
public class HeadAnalysis {
	/**
	 * 长度
	 */
	private int length;
	
	/**
	 * 版本
	 */
	
	private int version;
	
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
	
	public HeadAnalysis(byte[] head){
		byte[] length_=new byte[4];
		System.arraycopy(head,0,length_,0,length_.length);
		this.length=MumberByteUtil.byteArrayToInt(length_);
		this.version=MumberByteUtil.byteToInt(head[4]);
		this.reserve=MumberByteUtil.byteToInt(head[5]);
		this.encryptionType=MumberByteUtil.byteToInt(head[6]);
		this.SerializationType=MumberByteUtil.byteToInt(head[7]);
	}

	public int getLength() {
		return length;
	}

	public int getVersion() {
		return version;
	}

	public int getReserve() {
		return reserve;
	}

	public int getEncryptionType() {
		return encryptionType;
	}

	public int getSerializationType() {
		return SerializationType;
	}

	@Override
	public String toString() {
		return "HeadAnalysis [length=" + length + ", version=" + version + ", reserve=" + reserve + ", encryptionType="
				+ encryptionType + ", SerializationType=" + SerializationType + "]";
	}
	
}
