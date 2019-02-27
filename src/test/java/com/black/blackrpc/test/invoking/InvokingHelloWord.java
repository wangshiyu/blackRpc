package com.black.blackrpc.test.invoking;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.black.blackrpc.code.annotation.InvokingService;
import com.black.blackrpc.code.annotation.SerializationType;
import com.black.blackrpc.code.annotation.TimeOut;
import com.black.blackrpc.code.enums.SerializationTypeEnum;
import com.black.blackrpc.test.HelloWord;
@Component
@Order(4)
public class InvokingHelloWord implements ApplicationListener<ContextRefreshedEvent>  {

	@InvokingService
	@TimeOut(60000)
	@SerializationType(SerializationTypeEnum.Protostuff)
	private HelloWord helloWord;
	
	@InvokingService
	@TimeOut(60000)
	@SerializationType(SerializationTypeEnum.Protostuff)
	private HelloWord helloWord1;
	
	//@InvokingService("helloWord2")
	@TimeOut(60000)
	private HelloWord helloWord2;
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
		helloWord.one();
		System.err.println("helloWord1:"+helloWord1.one());
		//System.err.println("helloWord2:"+helloWord2.han());
		
	}
}
