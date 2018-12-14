package com.java.zk.test;


import com.java.zk.register.ZkRegister;
import org.I0Itec.zkclient.NetworkUtil;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZkRegisterTest {

    public static void main(String[] args) throws InterruptedException {
        String name    = ManagementFactory.getRuntimeMXBean().getName();
        int index      = name.indexOf('@');
        String processId =  NetworkUtil.getLocalhostName() + "_" + Long.parseLong(name.substring(0, index));


        String host = "192.168.1.166:3181,192.168.1.167:3181,192.168.1.168:3181";
        String parentNodeName ="TestServer0";
        ZkRegister zkRegister = new ZkRegister(host,parentNodeName+"/"+processId);

        ExecutorService es = Executors.newFixedThreadPool(1);
        es.submit(zkRegister);

        System.out.println("*******************");

    }

}
