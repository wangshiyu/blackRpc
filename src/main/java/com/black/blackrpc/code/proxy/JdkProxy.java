package com.black.blackrpc.code.proxy;


import java.lang.reflect.Proxy;

import com.black.blackrpc.code.enums.LoadBalanceStrategyEnum;
import com.black.blackrpc.code.enums.SerializationTypeEnum;
/**
 * jdk创建代理
 * java自带的动态代理方法
 * @author wangshiyu
 */
public class JdkProxy {
	
    public static Object getInstance(Class<?> cls,String serviceName,SerializationTypeEnum serializationType,long timeOut,LoadBalanceStrategyEnum LoadBalanceStrategy){       
    	JdkMethodProxy invocationHandler = new JdkMethodProxy();
    	invocationHandler.setServiceName(serviceName);
    	invocationHandler.setSerializationType(serializationType);
    	invocationHandler.setTimeOut(timeOut);
    	invocationHandler.setLoadBalanceStrategy(LoadBalanceStrategy);
        Object newProxyInstance = Proxy.newProxyInstance(  
                cls.getClassLoader(),  
                new Class[] { cls }, 
                invocationHandler); 
        return (Object)newProxyInstance;
    }
}