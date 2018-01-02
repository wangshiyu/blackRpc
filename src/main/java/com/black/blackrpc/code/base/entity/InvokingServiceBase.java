package com.black.blackrpc.code.base.entity;

import com.black.blackrpc.code.enums.LoadBalanceStrategyEnum;
import com.black.blackrpc.code.enums.SerializationTypeEnum;

/**
 * 调用服务实体
 * @author v_wangshiyu
 *
 */
public class InvokingServiceBase {
	/**
	 * 服务名称
	 * 例如：invokingService-Json   
	 * invokingService为服务名称
	 * Json 为序列化方式
	 */
	private String serviceName;
	/**
	 * 序列化类型
	 */
	private SerializationTypeEnum serializationType;
	/**
	 * 加载调用服务bean
	 */
	private Object bean;
	/**
	 * 类型
	 */
	private Class<?> cls;
	/**
	 * 属性名称
	 */
	private String fieldName;
	/**
	 * 超时时间
	 */
	private long timeOut;
	/**
	 * 负载均衡策略
	 */
	private LoadBalanceStrategyEnum loadBalanceStrategy;
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public SerializationTypeEnum getSerializationType() {
		return serializationType;
	}
	public void setSerializationType(SerializationTypeEnum serializationType) {
		this.serializationType = serializationType;
	}
	public Object getBean() {
		return bean;
	}
	public void setBean(Object bean) {
		this.bean = bean;
	}
	public Class<?> getCls() {
		return cls;
	}
	public void setCls(Class<?> cls) {
		this.cls = cls;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public long getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}
	public LoadBalanceStrategyEnum getLoadBalanceStrategy() {
		return loadBalanceStrategy;
	}
	public void setLoadBalanceStrategy(LoadBalanceStrategyEnum loadBalanceStrategy) {
		this.loadBalanceStrategy = loadBalanceStrategy;
	}
	@Override
	public String toString() {
		return "InvokingServiceBase [serviceName=" + serviceName + ", serializationType=" + serializationType
				+ ", bean=" + bean + ", cls=" + cls + ", fieldName=" + fieldName + ", timeOut=" + timeOut
				+ ", loadBalanceStrategy=" + loadBalanceStrategy + "]";
	}
}
