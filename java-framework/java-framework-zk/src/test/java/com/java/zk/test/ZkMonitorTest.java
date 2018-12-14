package com.java.zk.test;

import com.java.zk.monitor.ZkMonitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZkMonitorTest {

    public static void main(String[] args) {

        String host = "192.168.1.133:2181,192.168.1.133:2182,192.168.1.133:2183";
        String parentNodeName ="TestServer";
        ZkMonitor zkMonitor = new ZkMonitor(host,parentNodeName);
        zkMonitor.dowatch();
        ExecutorService es = Executors.newFixedThreadPool(1);
        es.submit(zkMonitor);
        System.out.println("------------------------------");
    }


}
