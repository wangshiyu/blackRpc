package com.black.blackrpc.zk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.black.blackrpc.code.cache.InvokingServiceCache;
import com.black.blackrpc.common.constant.ZkConstant;
import com.black.blackrpc.common.util.ListUtil;
import com.black.blackrpc.common.util.MapUtil;
/**
 * ZooKeeper 操作
 * wangshiyu
 */
public class ZooKeeperOperation {

    private static final Logger log = LoggerFactory.getLogger(ZooKeeperOperation.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private ZooKeeper zk;
    
    private String zkAddress;

    public ZooKeeperOperation(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    /**
     * 连接服务器
     * @return
     */
    public boolean connectServer() {
        try {
            zk = new ZooKeeper(zkAddress, ZkConstant.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException e) {
        	log.error(e.toString());
            return false;
        }catch (InterruptedException ex){
        	log.error(ex.toString());
            return false;
        }
        return true;
    }

    /**
     * 添加root节点
     * @return
     */
    public boolean AddRootNode(){
        try {
            Stat s = zk.exists(ZkConstant.ZK_RPC_DATA_PATH, false);
            if (s == null) {
            	//同步创建临时持久节点
                zk.create(ZkConstant.ZK_RPC_DATA_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
        	log.error(e.toString());
            return false;
        } catch (InterruptedException e) {
        	log.error(e.toString());
            return false;
        }
        return true;
    }

    /**
     * 创建node节点
     * @param zk
     * @param data
     */
    public boolean createNode(String node, String data) {
        try {
            byte[] bytes = data.getBytes();
            //同步创建临时顺序节点
            String path = zk.create(ZkConstant.ZK_RPC_DATA_PATH+"/"+node+"-", bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            log.info("create zookeeper node ({} => {})", path, data);
        }catch (KeeperException e) {
        	log.error("", e);
            return false;
        }catch (InterruptedException ex){
        	log.error("", ex);
            return false;
        }
        return true;
    }
    
    /**
     * 同步节点
     * 这是一个通知模式
     * syncNodes会通过级联方式，在每次watcher被触发后，就会再挂一次watcher。完成了一个类似链式触发的功能
     * @param zk
     */
    @SuppressWarnings("unchecked")
	public boolean syncNodes() {
        try {
            List<String> nodeList = zk.getChildren(ZkConstant.ZK_RPC_DATA_PATH, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.NodeChildrenChanged) {
                    	syncNodes();
                    }
                }
            });
            
            Map<String,List<String>> map =new HashMap<String,List<String>>();
            
            for (String node : nodeList) {
                byte[] bytes = zk.getData(ZkConstant.ZK_RPC_DATA_PATH + "/" + node, false, null);
                String key =node.substring(0, node.lastIndexOf(ZkConstant.DELIMITED_MARKER));
                String value=new String(bytes);
                Object object =map.get(key);
                if(object!=null){
                	((List<String>)object).add(value);
                }else {
                	List<String> dataList = new ArrayList<String>();
                	dataList.add(value);
                	map.put(key,dataList);
                }
                log.info("node:"+node+"    data:"+new String(bytes));     
            }
           
            if(MapUtil.isNotEmpty(map)){/**修改连接的地址缓存*/
            	 log.debug("invoking service cache updateing....");
            	InvokingServiceCache.updataInvokingServiceMap(map);
            }
            return true;
        } catch (KeeperException | InterruptedException e) {
        	log.error(e.toString());
            return false;
        }
    }
    
    /**
     * 停止服务
     * @return
     */
    public boolean zkStop(){
        if(zk!=null){
            try {
            	zk.close();
            	 return true;
            } catch (InterruptedException e) {
            	log.error(e.toString());
                return false;
            }
        }
        return true;
    }
}
