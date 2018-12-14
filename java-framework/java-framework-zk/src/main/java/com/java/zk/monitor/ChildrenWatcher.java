package com.java.zk.monitor;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ChildrenWatcher implements Watcher {

    private Logger logger = LoggerFactory.getLogger(ChildrenWatcher.class);

    private ZkMonitor zkMonitor;

    public ChildrenWatcher(ZkMonitor zkMonitor) {
        this.zkMonitor = zkMonitor;
    }

    @Override
    public void process(WatchedEvent event) {
        logger.warn("childrenWatcher Event Received:{}",event.toString());
        if(event.getType()==Event.EventType.NodeChildrenChanged){
            try{
                List<String> children = null;
                children = this.zkMonitor.getZk().getChildren(this.zkMonitor.getRootNode(), this);
                logger.warn("node change:{}",children);
                this.zkMonitor.setChildren(children);
            }catch(KeeperException | InterruptedException e){
                logger.error("zookeeper run error:",e);
                Thread.currentThread().interrupt();
                this.zkMonitor.setAlive(false);
                throw new RuntimeException(e);

            }
        }
    }

}
