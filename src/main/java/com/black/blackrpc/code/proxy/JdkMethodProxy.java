package com.black.blackrpc.code.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.black.blackrpc.code.enums.LoadBalanceStrategyEnum;
import com.black.blackrpc.code.enums.SerializationTypeEnum;
import com.black.blackrpc.code.invoking.RemoteInvoking;

public class JdkMethodProxy implements InvocationHandler {
	/**
	 * 服务名称
	 */
	private String serviceName;
	/**
	 * 序列化方式
	 */
	private SerializationTypeEnum serializationType;
	/**
	 * 超时时间
	 */
	private long timeOut;
	/**
	 * 负载均衡策略
	 */
	private LoadBalanceStrategyEnum loadBalanceStrategy;
	
    @Override
    public Object invoke(Object proxy, Method method, Object[] parameters)  throws Throwable {        
        //如果传进来是一个已实现的具体类
        if (Object.class.equals(method.getDeclaringClass())) {  
            try {  
                return method.invoke(this, parameters);  
            } catch (Throwable t) {  
                t.printStackTrace();  
            }  
        //如果传进来的是一个接口
        } else {  
        	//实现接口的核心方法 
            return RemoteInvoking.invoking(serviceName, serializationType, timeOut,loadBalanceStrategy,method, parameters);
        }  
        return null;
    }
   
	public long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

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

	public LoadBalanceStrategyEnum getLoadBalanceStrategy() {
		return loadBalanceStrategy;
	}

	public void setLoadBalanceStrategy(LoadBalanceStrategyEnum loadBalanceStrategy) {
		this.loadBalanceStrategy = loadBalanceStrategy;
	}


}