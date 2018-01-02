package com.black.blackrpc.lb.strategy.ipmt;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.black.blackrpc.code.base.entity.RemoteServiceBase;
import com.black.blackrpc.lb.strategy.ClusterStrategy;
/**
 * 轮询
 * @author Administrator
 *
 */
public class PollingStrategy implements ClusterStrategy {

	//计数器
    private int index = 0;
    private Lock lock = new ReentrantLock();
    
	@Override
	public RemoteServiceBase select(List<RemoteServiceBase> list) {
		RemoteServiceBase service = null;
	        try {
	            lock.tryLock(10, TimeUnit.MILLISECONDS);
	            //若计数大于服务提供者个数,将计数器归0
	            if (index >= list.size()) {
	                index = 0;
	            }
	            service = list.get(index);
	            index++;

	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } finally {
	            lock.unlock();
	        }

	      //兜底,保证程序健壮性,若未取到服务,则直接取第一个
	        if (service == null) {
	            service = list.get(0);
	        }		
        return service;
	}

}
