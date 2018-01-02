package com.black.blackrpc.code.spring.beanStructure;

import java.lang.reflect.Field;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.black.blackrpc.code.annotation.InvokingService;
import com.black.blackrpc.code.annotation.LoadBalanceStrategy;
import com.black.blackrpc.code.annotation.SerializationType;
import com.black.blackrpc.code.annotation.TimeOut;
import com.black.blackrpc.code.cache.BeanProxyCache;
import com.black.blackrpc.code.enums.LoadBalanceStrategyEnum;
import com.black.blackrpc.code.enums.SerializationTypeEnum;
import com.black.blackrpc.code.proxy.JdkProxy;
import com.black.blackrpc.code.service.ServiceCodeInit;
import com.black.blackrpc.common.constant.SyncFutureConstant;
import com.black.blackrpc.common.constant.ZkConstant;
import com.black.blackrpc.common.util.ListUtil;
import com.black.blackrpc.common.util.MapUtil;
/**
 * BeanPostProcessor 实现
 * @author v_wangshiyu
 *
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
	private static final Logger log = LoggerFactory.getLogger(ServiceCodeInit.class);
    public MyBeanPostProcessor() {
       super();
       /************初始化代理缓存**************/
       if(MapUtil.isEmpty(BeanProxyCache.beanProxyMap)){
    		BeanProxyCache.beanProxyMapInit();
       }
	   /************初始化代理缓存**************/
       log.info("BeanPostProcessor实现类构造器初始化完成！");         
   }

    // Bean 实例化之前进行的处理
    public Object postProcessBeforeInitialization(Object bean, String beanName)
           throws BeansException {
    	
       return bean;
    }
 
    // Bean 实例化之后进行的处理
    public Object postProcessAfterInitialization(Object bean, String beanName)
           throws BeansException {
    	Map<String,Object> beanProxyMap= BeanProxyCache.beanProxyMap;
    	
    	Field[] fields= bean.getClass().getDeclaredFields();//获取所有属性
		if(ListUtil.isNotEmpty(fields)){
			for(Field field:fields){
				InvokingService invokingService=field.getAnnotation(InvokingService.class);
				if(invokingService!=null){
					Object jdkMethodProxy= beanProxyMap.get(beanName+ZkConstant.DELIMITED_MARKER+field.getName());
					if(jdkMethodProxy!=null){
						try {
							field.setAccessible(true);
							field.set(bean,jdkMethodProxy);//注入动态代理
							field.setAccessible(false);
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}else{
						SerializationType serializationType = field.getAnnotation(SerializationType.class);
						TimeOut timeOut = field.getAnnotation(TimeOut.class);
						LoadBalanceStrategy loadBalanceStrategy = field.getAnnotation(LoadBalanceStrategy.class);
						String serviceName ="".equals(invokingService.value())?field.getType().getName():invokingService.value();
						SerializationTypeEnum serializationType_=serializationType==null?SerializationTypeEnum.Protostuff:serializationType.value();
						long timeOut_=timeOut==null?SyncFutureConstant.TimeOut:timeOut.value();
						if(timeOut!=null){
							if(timeOut.value()>SyncFutureConstant.maxTimeOut){//修改系统内最大的超时时间
								SyncFutureConstant.maxTimeOut=timeOut.value();
							}
						}
						LoadBalanceStrategyEnum loadBalanceStrategy_=loadBalanceStrategy==null?LoadBalanceStrategyEnum.Polling:loadBalanceStrategy.value();
						
						Object jdkMethodProxy_= JdkProxy.getInstance(field.getType(),serviceName,serializationType_,timeOut_,loadBalanceStrategy_);
						try {
							field.setAccessible(true);
							field.set(bean,jdkMethodProxy_);//注入动态代理
							field.setAccessible(false);
						} catch (Exception e) {
							e.printStackTrace();
						} 
						beanProxyMap.put(beanName+ZkConstant.DELIMITED_MARKER+field.getName(), jdkMethodProxy_);
					}
				}
			}
		}
    	
       return bean;
    }
}