package com.java.hb;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2018-05-20
 */
public class HbaseConnectionFactory {

    protected final static Logger logger = LoggerFactory.getLogger(HbaseConnectionFactory.class);

    private Configuration configuration;
    private Connection connection;
    private int poolSize = 10;


    public HbaseConnectionFactory(Integer poolSize,String zkHost) {
        this.connection = connection;
        if(poolSize != null){
            this.poolSize = poolSize;
        }
        ExecutorService pool = Executors.newFixedThreadPool(this.poolSize);//建立一个数量为10的线程池
        this.configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum",zkHost);
        try {
            connection = ConnectionFactory.createConnection(configuration,pool);
        } catch (IOException e) {
            logger.error("hbase init connection error",e);
            System.exit(0);
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close(){
        try {
            this.connection.close();
        } catch (IOException e) {
            logger.error("hbase close connection error",e);
            System.exit(0);
        }
    }

}
