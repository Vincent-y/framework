package com.java.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbsZkClient implements ZkClient {

    protected Logger logger = LoggerFactory.getLogger(AbsZkClient.class);

    protected ZooKeeper zk;
    protected String rootNode;
    protected int timeOut   = 2000;
    protected boolean alive = true;

    /**
     * 创建节点
     *
     * @param nodeName
     * @return
     */
    public String createNode(String nodeName) {
        String node = null;
        String createNode = "/" + nodeName;
        try {
            if (zk.exists(createNode, false) == null) {
                node = zk.create(createNode, createNode.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException | InterruptedException e) {
            logger.error("zookeeper create Node error:", e);
        }
        return node;
    }

    public synchronized void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            logger.error("zookeeper close error:", e);
        }
    }


}
