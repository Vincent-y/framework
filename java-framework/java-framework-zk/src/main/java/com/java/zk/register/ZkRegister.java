package com.java.zk.register;

import com.java.zk.AbsZkClient;
import com.java.zk.monitor.ConnectionWatcher;
import org.apache.zookeeper.*;

import java.io.IOException;

public class ZkRegister extends AbsZkClient implements Runnable{


    private Watcher connectionWatcher = null;

    public ZkRegister() {
        super();
    }

    public ZkRegister(String hostPort, String parentNodeName) {
        super();
        try {
            init(hostPort,parentNodeName);
        } catch (IOException e) {
            logger.error("zookeeper init error:", e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (true) {
                    wait();
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            this.close();
        }
    }


    @Override
    public void init(String hostPort, String parentNodeName) throws IOException {
        this.connectionWatcher = new ConnectionWatcher(this);
        zk = new ZooKeeper(hostPort, this.timeOut, this.connectionWatcher);
        createNode(parentNodeName);
    }

    @Override
    public String createNode(String nodeName) {
        String node = null;
        String createNode = "/" + nodeName;
        if (zk != null) {
            try {
                node = zk.create(createNode, createNode.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            } catch (KeeperException | InterruptedException e) {
                logger.error("zookeeper create Node error:", e);
            }
        }
        return node;
    }

    @Override
    public void dowatch() {
        //doNothing
    }

    public String getRootNode() {
        return  "/" + rootNode;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
