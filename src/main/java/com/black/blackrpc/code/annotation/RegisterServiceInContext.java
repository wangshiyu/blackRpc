package com.black.blackrpc.code.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;

/**
 * 注册服务注解加载到spring上下文
 * @author Administrator
 * 如果没有服务别名默认已服务接口名称发布
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@SerializationType
@Service
public @interface RegisterServiceInContext {
	String value() default "";//别名
}
