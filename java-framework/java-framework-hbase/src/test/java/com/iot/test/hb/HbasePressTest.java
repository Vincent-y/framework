package com.iot.test.hb;

import com.java.hb.HbaseUtil;
import com.java.hb.model.HBaseColumn;
import com.java.hb.model.HbaseTable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Callable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
@ActiveProfiles(profiles = {"UAT"})
public class HbasePressTest {

    //日志
    private final static Logger logger = LoggerFactory.getLogger(HbasePressTest.class);

    @Autowired
    private HbaseUtil hbaseUtil;

    private String tableName = "TestTable";


    String test= "测试测试测试测试测试测试测试测试测试测试测试测试" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测" +
            "试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试";

    @Test
    public void testInsertable() {
        HbaseTable hbaseTable = new HbaseTable();
        boolean result = false;
        try {
            hbaseTable.setTableName(tableName);
            hbaseTable.setRowKey("row-0001");
            HBaseColumn hBaseColumn = new HBaseColumn();
            hBaseColumn.setFamily("family2");
            hBaseColumn.setQualifier("hello1");
            hBaseColumn.setValue("world1");
            hbaseTable.setHBaseColumn(hBaseColumn);
            result = hbaseUtil.insertData(hbaseTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.warn("logger:{}", result);
    }



    private class Task implements Callable<Long> {
        private int num = 0;
        public Task(int num) {
            this.num = num;
        }
        @Override
        public Long call() {
            long sTime = System.currentTimeMillis();

            HbaseTable hbaseTable = new HbaseTable();
            boolean result = false;
            try {
                hbaseTable.setTableName(tableName);
                hbaseTable.setRowKey("row-0001");
                HBaseColumn hBaseColumn = new HBaseColumn();
                hBaseColumn.setFamily("family2");
                hBaseColumn.setQualifier("hello1");
                hBaseColumn.setValue(test);
                hbaseTable.setHBaseColumn(hBaseColumn);
                result = hbaseUtil.insertData(hbaseTable);
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.warn("logger:{}", result);

            long eTime = System.currentTimeMillis();
            logger.debug("threadId:{},第:{}条耗时:{}ms",Thread.currentThread().getId(),(num+1),(eTime-sTime));
            return (eTime-sTime);
        }
    }


}
