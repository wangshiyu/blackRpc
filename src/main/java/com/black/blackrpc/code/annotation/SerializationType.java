package com.black.blackrpc.code.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.black.blackrpc.code.enums.SerializationTypeEnum;

/**
 * 序列化方式 
 * 默认Msgpack
 * @author v_wangshiyu
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
@Documented

public @interface SerializationType {
	SerializationTypeEnum value() default SerializationTypeEnum.Protostuff;
}
