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
public class HelloWordIpmt implements HelloWord {

	@Override
	public String han() {
		return "HelloWord HelloWord HelloWord HelloWord";
	}
	
	@Override
	public void zijihan() {
		System.err.println("自己喊 HelloWord HelloWord HelloWord HelloWord");
	}

	@Override
	public String chang(String a,int b,List<String> c) {
		// TODO Auto-generated method stub
		return a+b+c.size();
	}
	
	@Override
	public String chang(String a,double b,List<String> c) {
		// TODO Auto-generated method stub
		return a+b+c.size();
	}


}
