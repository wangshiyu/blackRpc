package com.black.blackrpc.code.spring;

import org.springframework.beans.BeansException;  
import org.springframework.beans.factory.BeanFactory;  
import org.springframework.beans.factory.BeanFactoryAware;  
import org.springframework.stereotype.Component;  
 /**
  * 从spring上下文当中获取bean
  * @author wangshiyu
  *
  */
@Component  
public class SpringContextComponent implements BeanFactoryAware{  
    private static BeanFactory beanFactory;  
      
    @Override  
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {  
    	SpringContextComponent.beanFactory =beanFactory;  
    }  
  
  
    @SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {    
        if (null != beanFactory) {    
            return (T) beanFactory.getBean(beanName);    
        }    
        return null;    
    }
    
    @SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> cls) {    
        if (null != beanFactory) {    
            return (T) beanFactory.getBean(cls);    
        }    
        return null;    
    } 
}