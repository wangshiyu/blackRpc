package com.black.blackrpc.code.invoking;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Bean 调用
 * @author wangshiyu
 *
 */
public class BeanInvoking {

	/**
	 * 调用方法 
	 * 通过反射的方法调用bean的方法
	 * @param cls 对象
	 * @param instance 对象实例
	 * @param methodName 方法名称
	 * @param parameterTypes 参数列表
	 * @param parameters 参数集合
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static Object invoking(Object instance,String methodName,Object[] parameterTypes,Object[] parameters) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method[] methods= instance.getClass().getMethods();
	       for(Method method:methods){
	    	   Class<?>[] class_ = method.getParameterTypes();
	    	   if(methodName.equals(method.getName())&&equals(parameterTypes,class_)){
	    		   return method.invoke(instance,parameters);
	    	   }
	       }
		
		return null;
	}
	
	/**
	 * 比较2个参数列表是否相同
	 * @param parameterTypes
	 * @param classs
	 * @return
	 */
	private static boolean equals(Object[] parameterTypes,Class<?>[] classs){
		if(parameterTypes==null&&classs==null){
			return true;
		}else if((parameterTypes==null&&classs!=null)||(parameterTypes!=null&&classs==null)){
			return false;
		}else if(parameterTypes.length!=classs.length){
			return false;
		}else if(parameterTypes.length==classs.length){
			for(int i=0;i<parameterTypes.length;i++){
				if(!parameterTypes[i].getClass().getName().equals(classs[i].getName())){
					return false;
				}
			}
			return true;
		}
		return true;
	}
}
