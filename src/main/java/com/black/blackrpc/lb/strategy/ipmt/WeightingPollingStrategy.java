package com.black.blackrpc.lb.strategy.ipmt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.black.blackrpc.code.base.entity.RemoteServiceBase;
import com.black.blackrpc.lb.strategy.ClusterStrategy;
/**
 * 加权轮询
 * 权值保留一位小数所以扩大10倍
 * @author Administrator
 *
 */
public class WeightingPollingStrategy implements ClusterStrategy {

	//计数器
    private int index = 0;
    //计数器锁
    private Lock lock = new ReentrantLock();

    @Override
    public RemoteServiceBase select(List<RemoteServiceBase> list) {

    	RemoteServiceBase service = null;
        try {
            lock.tryLock(10, TimeUnit.MILLISECONDS);
            //存放加权后的服务提供者列表
            List<RemoteServiceBase> weightingList = new ArrayList<RemoteServiceBase>();
            for (RemoteServiceBase remoteServiceBase : list) {
                int weight = (int) (remoteServiceBase.getWeight()*10);//扩大10倍
                for (int i = 0; i < weight; i++) {
                	weightingList.add(remoteServiceBase);
                }
            }
            //若计数大于服务提供者个数,将计数器归0
            if (index >= weightingList.size()) {
                index = 0;
            }
            service = weightingList.get(index);
            index++;
            return service;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        //兜底,保证程序健壮性,若未取到服务,则直接取第一个
        return list.get(0);
    }

}
