package com.black.blackrpc.test.run;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.black.blackrpc.test.invoking.HelloWordInvoking;
@Component
@Order(4)
public class InvokingHelloWord implements ApplicationListener<ContextRefreshedEvent>  {
	@Autowired
	private HelloWordInvoking helloWordInvoking;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		helloWordInvoking.run();
	}
}
