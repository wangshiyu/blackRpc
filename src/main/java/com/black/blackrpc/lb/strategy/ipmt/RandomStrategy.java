package com.black.blackrpc.lb.strategy.ipmt;

import java.util.List;

import com.black.blackrpc.code.base.entity.RemoteServiceBase;
import com.black.blackrpc.common.util.RandomUtil;
import com.black.blackrpc.lb.strategy.ClusterStrategy;
/**
 * 随机
 * @author Administrator
 *
 */
public class RandomStrategy implements ClusterStrategy {

	@Override
	public RemoteServiceBase select(List<RemoteServiceBase> list) {
		int MAX_LEN = list.size();
        int index = RandomUtil.nextInt(MAX_LEN);
        return list.get(index);
	}
}
