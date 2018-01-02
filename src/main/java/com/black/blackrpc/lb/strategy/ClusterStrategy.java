package com.black.blackrpc.lb.strategy;

import java.util.List;

import com.black.blackrpc.code.base.entity.RemoteServiceBase;
/**
 * 集群策略
 * @author Administrator
 *
 */
public interface ClusterStrategy {
	/**
     * 负载策略算法
     *
     * @param serverName
     * @return
     */
    public RemoteServiceBase select(List<RemoteServiceBase> list);
}
