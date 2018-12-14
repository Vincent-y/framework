package com.java.hdfs.test;

import com.java.hdfs.HdfsUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
@ActiveProfiles(profiles = {"UAT"})
public class HdoopTest {

    //日志
    private final static Logger logger = LoggerFactory.getLogger(HdoopTest.class);

    @Autowired
    private HdfsUtil hdfsUtil;

    private String dir = "test";


    @Test
    public void testUpload() throws IOException {
        boolean result = hdfsUtil.makeDirectory(dir);
        logger.warn("result:{}",result);
    }




    @Test
    public void testUpload1() throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://hzq:9000");
        FileSystem fs = FileSystem.get(conf);
        fs.copyFromLocalFile(new Path("/soft/files/a.png"),new Path("/demo"));
    }





}
