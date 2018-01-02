package com.black.blackrpc.code.cache;

import com.black.blackrpc.lb.strategy.ClusterStrategy;
import com.black.blackrpc.lb.strategy.ipmt.HashStrategy;
import com.black.blackrpc.lb.strategy.ipmt.PollingStrategy;
import com.black.blackrpc.lb.strategy.ipmt.RandomStrategy;
import com.black.blackrpc.lb.strategy.ipmt.WeightingPollingStrategy;
import com.black.blackrpc.lb.strategy.ipmt.WeightingRandomStrategy;
import com.black.blackrpc.zk.ZooKeeperOperation;

/**
 * 对象缓存
 * @author v_wangshiyu
 *
 */
public class ObjectCache {
	//zookeeper连接
	public static volatile ZooKeeperOperation zooKeeperOperation;
	public static volatile ClusterStrategy pollingStrategy =new PollingStrategy();
	public static volatile ClusterStrategy hashStrategy =new HashStrategy();
	public static volatile ClusterStrategy randomStrategy =new RandomStrategy();
	public static volatile ClusterStrategy weightingPollingStrategy =new WeightingPollingStrategy();
	public static volatile ClusterStrategy weightingRandomStrategy =new WeightingRandomStrategy();
}

