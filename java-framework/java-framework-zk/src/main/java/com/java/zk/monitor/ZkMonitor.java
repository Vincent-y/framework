package com.java.zk.monitor;

import com.java.zk.AbsZkClient;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

/**
 * zk monitor
 */
public class ZkMonitor extends AbsZkClient implements Runnable {

    private List<String> children     = null;
    private Watcher childrenWatcher   = null;
    private Watcher connectionWatcher = null;

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (alive) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            logger.error("zookeeper run error:", e);
            Thread.currentThread().interrupt();
        } finally {
            this.close();
        }
    }

    public ZkMonitor(String hostPort, String parentNodeName) {
        super();
        this.rootNode = parentNodeName;
        try {
            init(hostPort,parentNodeName);
        } catch (IOException e) {
            logger.error("zookeeper init error:", e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void init(String hostPort, String parentNodeName) throws IOException {
        this.connectionWatcher = new ConnectionWatcher(this);
        this.childrenWatcher   = new ChildrenWatcher(this);
        this.zk = new ZooKeeper(hostPort, this.timeOut, this.connectionWatcher);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.createNode(parentNodeName);
    }

    @Override
    public void dowatch() {
        try {
            this.children = this.zk.getChildren(getRootNode() , this.childrenWatcher);
            logger.warn("init and get children:{}",this.children);
        } catch (KeeperException | InterruptedException e) {
            logger.error("zookeeper watch error:", e);
        }
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public String getRootNode() {
        return  "/" + rootNode;
    }
}
