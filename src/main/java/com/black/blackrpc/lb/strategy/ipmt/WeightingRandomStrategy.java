package com.black.blackrpc.lb.strategy.ipmt;

import java.util.ArrayList;
import java.util.List;

import com.black.blackrpc.code.base.entity.RemoteServiceBase;
import com.black.blackrpc.common.util.RandomUtil;
import com.black.blackrpc.lb.strategy.ClusterStrategy;
/**
 * 加权随机
 * 权值保留一位小数所以扩大10倍
 * @author Administrator
 *
 */
public class WeightingRandomStrategy implements ClusterStrategy {

	@Override
	public RemoteServiceBase select(List<RemoteServiceBase> list) {
		 //存放加权后的服务提供者列表
        List<RemoteServiceBase> weightingList = new ArrayList<RemoteServiceBase>();
        for (RemoteServiceBase remoteServiceBase : list) {
            int weight = (int) (remoteServiceBase.getWeight()*10);//扩大10倍
            for (int i = 0; i < weight; i++) {
            	weightingList.add(remoteServiceBase);
            }
        }
        int MAX_LEN = weightingList.size();
        int index = RandomUtil.nextInt(MAX_LEN);
        return weightingList.get(index);
	}

}
