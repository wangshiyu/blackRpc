package com.black.blackrpc.code.spring.listener;

import java.lang.reflect.Field;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.black.blackrpc.code.annotation.InvokingService;
import com.black.blackrpc.code.annotation.LoadBalanceStrategy;
import com.black.blackrpc.code.annotation.RegisterService;
import com.black.blackrpc.code.annotation.RegisterServiceInContext;
import com.black.blackrpc.code.annotation.SerializationType;
import com.black.blackrpc.code.annotation.TimeOut;
import com.black.blackrpc.code.annotation.Weight;
import com.black.blackrpc.code.base.entity.InvokingServiceBase;
import com.black.blackrpc.code.base.entity.ProvideServiceBase;
import com.black.blackrpc.code.cache.InvokingServiceCache;
import com.black.blackrpc.code.cache.ProvideServiceCache;
import com.black.blackrpc.code.enums.LoadBalanceStrategyEnum;
import com.black.blackrpc.code.enums.SerializationTypeEnum;
import com.black.blackrpc.common.constant.SyncFutureConstant;
import com.black.blackrpc.common.constant.ZkConstant;
import com.black.blackrpc.common.util.ListUtil;
import com.black.blackrpc.common.util.math.Arith;
import com.black.blackrpc.communication.netty.tcp.service.NettyTcpService;

/**
 * 上下文监听
 * 
 * @author v_wangshiyu
 *
 */
@Component
@Order(1)//使其在服务端核心初始化之前加载
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger log = LoggerFactory.getLogger(NettyTcpService.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event){
		log.info("Context Refreshed Listener Is Run!");
		// 根容器为Spring容器
		if (event.getApplicationContext().getParent() == null) {
			/**监听被标记@RegisterService 注解的类,同时将其加入缓存**/
			Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(RegisterService.class);
			beans.putAll(event.getApplicationContext().getBeansWithAnnotation(RegisterServiceInContext.class));
			ProvideServiceCache.provideServiceMapInit();// 初始化服务缓存
			for (Object bean : beans.values()) {
				RegisterService registerService = bean.getClass().getAnnotation(RegisterService.class);
				SerializationType serializationType = bean.getClass().getAnnotation(SerializationType.class);
				Weight weight = bean.getClass().getAnnotation(Weight.class);
				if (!"".equals(registerService.value())) {//是否存在别名
					ProvideServiceBase provideServiceBase = new ProvideServiceBase();
					provideServiceBase.setServiceName(registerService.value());
					provideServiceBase.setServiceClass(bean.getClass());
					provideServiceBase.setBean(bean);
					if(serializationType==null){
						provideServiceBase.setSerializationType(SerializationTypeEnum.Protostuff);
					}else{
						provideServiceBase.setSerializationType(serializationType.value());
					}
					if(weight==null){
						provideServiceBase.setWeight(1.0);
					}else{
						try {
							provideServiceBase.setWeight(Arith.round(weight.value(), 1));
						} catch (Exception e) {
							provideServiceBase.setWeight(1.0);
							e.printStackTrace();
						}
					}
					//发布的名称加上序列化方式
					ProvideServiceCache.provideServiceMap.put(registerService.value()+ZkConstant.DELIMITED_MARKER+serializationType.value(), provideServiceBase);
				} else {
					Class<?>[] classs = bean.getClass().getInterfaces();
					if (ListUtil.isNotEmpty(classs)) {
						for (Class<?> class_ : classs) {
							ProvideServiceBase provideServiceBase = new ProvideServiceBase();
							provideServiceBase.setServiceName(class_.getName());
							provideServiceBase.setServiceClass(bean.getClass());
							if(serializationType==null){
								provideServiceBase.setSerializationType(SerializationTypeEnum.Protostuff);
							}else{
								provideServiceBase.setSerializationType(serializationType.value());
							}
							if(weight==null){
								provideServiceBase.setWeight(1.0);
							}else{
								try {
									provideServiceBase.setWeight(Arith.round(weight.value(), 1));
								} catch (Exception e) {
									provideServiceBase.setWeight(1.0);
									e.printStackTrace();
								}
							}
							provideServiceBase.setBean(bean);
							//发布的名称加上序列化方式
							ProvideServiceCache.provideServiceMap.put(class_.getName(), provideServiceBase);
						}
					}
				}
			}
			/**监听被标记@RegisterService 注解的类,同时将其加入缓存**/
			
			/**监听被标记@InvokingService 属性注解的类,同时将其加入缓存**/
			InvokingServiceCache.invokingServiceListInit();
			Map<String, Object> componentBeans_ = event.getApplicationContext().getBeansWithAnnotation(Component.class);
			for (Object bean : componentBeans_.values()) {
				Field[] fields= bean.getClass().getDeclaredFields();//获取所有属性
				if(ListUtil.isNotEmpty(fields)){
					for(Field field:fields){
						InvokingService invokingService=field.getAnnotation(InvokingService.class);
						if(invokingService!=null){
							SerializationType serializationType = field.getAnnotation(SerializationType.class);
							TimeOut timeOut = field.getAnnotation(TimeOut.class);
							LoadBalanceStrategy loadBalanceStrategy = field.getAnnotation(LoadBalanceStrategy.class);
							InvokingServiceBase invokingServiceBase =new InvokingServiceBase();
							invokingServiceBase.setBean(bean);
							invokingServiceBase.setCls(field.getType());
							invokingServiceBase.setFieldName(field.getName());
							if (!"".equals(invokingService.value())) {//是否存在别名
								invokingServiceBase.setServiceName(invokingService.value());
							}else{
								invokingServiceBase.setServiceName(field.getType().getName());
							}
							if(serializationType==null){//设置默认初始化方式
								invokingServiceBase.setSerializationType(SerializationTypeEnum.Protostuff);
							}else{
								invokingServiceBase.setSerializationType(serializationType.value());
							}
							if(timeOut==null){//设置默认初始化方式
								invokingServiceBase.setTimeOut(SyncFutureConstant.TimeOut);
							}else{
								invokingServiceBase.setTimeOut(timeOut.value());
								if(timeOut.value()>SyncFutureConstant.maxTimeOut){//修改系统内最大的超时时间
									SyncFutureConstant.maxTimeOut=timeOut.value();
								}
							}
							if(loadBalanceStrategy==null){//负载均衡策略
								invokingServiceBase.setLoadBalanceStrategy(LoadBalanceStrategyEnum.Polling);
							}else{
								invokingServiceBase.setLoadBalanceStrategy(loadBalanceStrategy.value());
							}
							InvokingServiceCache.invokingServiceList.add(invokingServiceBase);
						}
					}
				}
			}
			/**监听被标记@RegisterService 属性注解的类,同时将其加入缓存**/
		}
	}
}