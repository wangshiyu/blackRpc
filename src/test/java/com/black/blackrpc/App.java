package com.black.blackrpc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.springframework.context.support.ClassPathXmlApplicationContext;  
  
@SuppressWarnings("resource")  
public class App {  
      
    public static void main(String[] args) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(  
                new String[] { "classpath:applicationContext.xml" });  
        context.start();  
        System.out.println("启动成功!");  
       //System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟  
      // Object o=  SpringContextComponent.getBean(HelloWordIpmt.class);
       //HelloWord helloWord =(HelloWord)o;
//	   	 System.err.println(helloWord.han());
      // System.err.println(o);
    }  
      
}