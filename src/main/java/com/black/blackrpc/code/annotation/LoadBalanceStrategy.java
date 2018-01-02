package com.black.blackrpc.code.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.black.blackrpc.code.enums.LoadBalanceStrategyEnum;
/**
 * 负载均衡策略
 * 默认轮训策略
 * @author Administrator
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface LoadBalanceStrategy {
	LoadBalanceStrategyEnum  value() default LoadBalanceStrategyEnum.Polling;
}
