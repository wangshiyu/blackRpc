package com.black.blackrpc.code.enums;
/**
 * 负载均衡策略
 * @author Administrator
 *
 */
public enum LoadBalanceStrategyEnum {
	/**
	 * 轮询
	 */
	Polling,
	/**
	 * 随机
	 */
	Random,
	/**
	 * 一致性哈希
	 */
	Hash,
	/**
	 * 加权轮询
	 */
	WeightingPolling,
	/**
	 * 加权随机
	 */
	WeightingRandom
	
}
