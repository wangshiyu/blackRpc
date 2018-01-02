package com.black.blackrpc.code.base.entity;

import com.black.blackrpc.code.enums.SerializationTypeEnum;
import com.black.blackrpc.common.constant.ZkConstant;

/**
 * 提供的服务实体
 * @author v_wangshiyu
 *
 */
public class ProvideServiceBase {
	/**
	 * 服务名称
	 */
	private String serviceName;
	/**
	 * spring 中加载的bean
	 */
	private Object bean;
	/**
	 * 服务Class
	 */
	private Class<?> serviceClass;
	/**
	 * 序列化类型
	 */
	private SerializationTypeEnum serializationType;
	/**
	 * 权值
	 */
	private double weight;
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Object getBean() {
		return bean;
	}
	public void setBean(Object bean) {
		this.bean = bean;
	}
	public Class<?> getServiceClass() {
		return serviceClass;
	}
	public void setServiceClass(Class<?> serviceClass) {
		this.serviceClass = serviceClass;
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
	@Override
	public String toString() {
		return "ProvideServiceBase [serviceName=" + serviceName + ", bean=" + bean + ", serviceClass=" + serviceClass
				+ ", serializationType=" + serializationType + ", weight=" + weight + "]";
	}
	
	/**
	 * 返回zookeeper节点数据
	 * 除地址以外
	 * 数据结构为:  _序列化方式_权值
	 * @return
	 */
	public String getZkDate(){
		return ZkConstant.DELIMITED_MARKER+serializationType.toString()+ZkConstant.DELIMITED_MARKER+weight;
	}
}
