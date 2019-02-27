package com.black.blackrpc.test.ipmt;

import java.util.List;

import org.springframework.stereotype.Service;

import com.black.blackrpc.code.annotation.RegisterService;
import com.black.blackrpc.code.annotation.SerializationType;
import com.black.blackrpc.code.enums.SerializationTypeEnum;
import com.black.blackrpc.test.HelloWord;
@RegisterService
@SerializationType(SerializationTypeEnum.Protostuff)
@Service("helloWordIpmt")
public class HelloWordIpmt implements HelloWord{

	public String one() {
		return "HelloWord one";
	}
	
	public void two() {
		System.err.println("HelloWord two");
	}

	public String one(String a,int b,List<String> c) {
		// TODO Auto-generated method stub
		return a+b+c.size();
	}

	public String one(String a,double b,List<String> c) {
		// TODO Auto-generated method stub
		return a+b+c.size();
	}


}
