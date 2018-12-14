package com.java.hdfs.test;

import com.java.framework.boot.ApplicationBoot;
import com.java.framework.boot.ServerCallBack;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SpringHadoopHDFSApp extends ApplicationBoot {

    private ApplicationContext ctx;
    private FileSystem fileSystem;

    public SpringHadoopHDFSApp(String configName, String applicationName) {
        super(configName, applicationName);
    }


    public static void main(String[] args) {
        SpringHadoopHDFSApp springHadoopHDFSApp = new SpringHadoopHDFSApp("classpath:spring/spring-hdfs-conf.xml","HDFS");
        springHadoopHDFSApp.setServerCallBack(new ServerCallBack(){

            @Override
            public void start(ApplicationContext context) {
                FileSystem fileSystem = (FileSystem) context.getBean("fileSystem");
                try {
                    fileSystem.mkdirs(new Path("/springhdfs"));
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        fileSystem.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void stop(ApplicationContext context) {
            }
        });

        springHadoopHDFSApp.startServer();

    }


}
