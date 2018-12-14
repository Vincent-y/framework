package com.java.zk.monitor;

import com.java.zk.AbsZkClient;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 连接观察者
 */
public class ConnectionWatcher implements Watcher {

    private Logger logger = LoggerFactory.getLogger(ConnectionWatcher.class);

    private AbsZkClient zkClient;

    public ConnectionWatcher(AbsZkClient zkMonitor) {
        this.zkClient = zkClient;
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Watcher.Event.EventType.None &&
                event.getState() == Watcher.Event.KeeperState.SyncConnected) {
            logger.warn("connectionWatcher Event Received:{}", event.toString());
        }
    }
}
