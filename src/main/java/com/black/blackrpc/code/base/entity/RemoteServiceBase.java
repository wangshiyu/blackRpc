package com.black.blackrpc.code.base.entity;

import com.black.blackrpc.code.enums.SerializationTypeEnum;

/**
 * 远程服务基础
 * @author v_wangshiyu
 *
 */
public class RemoteServiceBase {
	/**
	 * 地址
	 */
	private String host;
	/**
	 * 端口
	 */
	private int port;
	/**
	 * 序列化方式
	 */
	private SerializationTypeEnum serializationType;
	/**
	 * 权值
	 * @return
	 */
	private double weight;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public SerializationTypeEnum getSerializationType() {
		return serializationType;
	}
	public void setSerializationType(SerializationTypeEnum serializationType) {
		this.serializationType = serializationType;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * 返回地址
	 * @return
	 */
	public String getAddress(){
		return host+":"+port;
	}
	@Override
	public String toString() {
		return "RemoteServiceBase [host=" + host + ", port=" + port + ", serializationType=" + serializationType
				+ ", weight=" + weight + "]";
	}
	
}
