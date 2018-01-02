package com.black.blackrpc.test.invoking;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.black.blackrpc.code.annotation.InvokingService;
import com.black.blackrpc.code.annotation.SerializationType;
import com.black.blackrpc.code.annotation.TimeOut;
import com.black.blackrpc.code.enums.SerializationTypeEnum;
import com.black.blackrpc.test.HelloWord;

@Component
@Scope("prototype")
public class HelloWordInvoking {
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
	
	public void run(){
		helloWord.zijihan();
		System.err.println("helloWord1:"+helloWord1.han());
		//System.err.println("helloWord2:"+helloWord2.han());
	}

}
