package com.black.blackrpc.code.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注册服务注解
 * @author Administrator
 * 如果没有服务别名默认已服务接口名称发布
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@SerializationType//序列化
@Weight//加权
public @interface RegisterService {
	String value() default "";//别名
}
