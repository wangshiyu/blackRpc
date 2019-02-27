package com.black.blackrpc.test;

import java.util.List;

public interface HelloWord {

	String one();
	
	void two();
	
	String one(String a,int b,List<String> c);
	
	String one(String a,double b,List<String> c);
}
