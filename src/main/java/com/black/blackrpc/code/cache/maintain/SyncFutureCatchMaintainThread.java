package com.black.blackrpc.code.cache.maintain;

import java.util.Iterator;
import java.util.Map;

import com.black.blackrpc.code.base.entity.RpcResponse;
import com.black.blackrpc.code.cache.SyncFutureCatch;
import com.black.blackrpc.code.synchronization.SyncFuture;
import com.black.blackrpc.common.constant.SyncFutureConstant;

/**
 * 同步结果缓存维护线程
 * 
 * @author Administrator
 *
 */
public class SyncFutureCatchMaintainThread extends Thread {
	private String control = "";// 只是任意的实例化一个对象而已

	@Override
	public void run() {
		while (true) {
			synchronized (control) {
				try {
					control.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Map<String, SyncFuture<RpcResponse>> syncFutureMap = SyncFutureCatch.syncFutureMap;
			long time = System.currentTimeMillis() - SyncFutureConstant.maxTimeOut + (3000);// 超过最大超时时间
			Iterator<Map.Entry<String, SyncFuture<RpcResponse>>> it = syncFutureMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, SyncFuture<RpcResponse>> entry = it.next();
				SyncFuture<RpcResponse> syncFuture = entry.getValue();
				if (syncFuture.getBeginTime() > time) {
					it.remove();
				}
			}
		}
	}

	
	public void notify(boolean suspend) {
		synchronized (control) {
			control.notify();
		}
	}
}
