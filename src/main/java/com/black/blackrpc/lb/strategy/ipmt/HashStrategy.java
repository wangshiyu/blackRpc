package com.black.blackrpc.lb.strategy.ipmt;

import java.util.List;

import com.black.blackrpc.code.base.entity.RemoteServiceBase;
import com.black.blackrpc.common.util.IPHelper;
import com.black.blackrpc.lb.strategy.ClusterStrategy;
/**
 * 一致性哈希
 * @author Administrator
 *
 */
public class HashStrategy implements ClusterStrategy {

	@Override
	public RemoteServiceBase select(List<RemoteServiceBase> list) {
		//获取调用方ip
        String localIP = IPHelper.localIp();
        //获取源地址对应的hashcode
        int hashCode = localIP.hashCode();
        //获取服务列表大小
        int size = list.size();

        return list.get(hashCode % size);
	}

}
