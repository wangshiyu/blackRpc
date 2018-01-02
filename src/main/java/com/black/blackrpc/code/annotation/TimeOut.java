package com.black.blackrpc.code.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.black.blackrpc.common.constant.SyncFutureConstant;

/**
 * 请求超时时间 (毫秒)
 * @author Administrator
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface TimeOut {
	long value() default SyncFutureConstant.TimeOut;//超时时间
}
