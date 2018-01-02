package com.black.blackrpc.code.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.black.blackrpc.code.base.entity.RpcResponse;
import com.black.blackrpc.code.cache.maintain.SyncFutureCatchMaintainThread;
import com.black.blackrpc.code.synchronization.SyncFuture;
/**
 * 同步结果缓存
 * @author Administrator
 *
 */
public class SyncFutureCatch {
	private static volatile Long uptateTime=System.currentTimeMillis();//上次更新时间戳
	private static volatile SyncFutureCatchMaintainThread syncFutureCatchMaintainThread;//维护线程
	
	public static volatile Map<String,SyncFuture<RpcResponse>> syncFutureMap =null;
	
	/**
	 * 初始化syncFutureMap
	 * @return
	 */
	public static boolean syncFutureMapInit(){
		if(syncFutureMap==null){
			syncFutureMap =new ConcurrentHashMap<String, SyncFuture<RpcResponse>>();
		}
		syncFutureCatchMaintainThread =new SyncFutureCatchMaintainThread();
		syncFutureCatchMaintainThread.start();
		return true;
	}
	
	/**
	 * put
	 * @return
	 */
	public static SyncFuture<RpcResponse> syncFutureMapPut(String key,SyncFuture<RpcResponse> value){
		if(System.currentTimeMillis()>(uptateTime+60*60*1000)&&syncFutureMap.size()>5000){//1小时
			syncFutureCatchMaintainThread.notify();
		}
		return syncFutureMap.put(key, value);
	}
}
