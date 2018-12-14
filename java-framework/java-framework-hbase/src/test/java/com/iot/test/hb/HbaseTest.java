package com.iot.test.hb;

import com.java.hb.HbaseUtil;
import com.java.hb.model.HBaseColumn;
import com.java.hb.model.HQueryRow;
import com.java.hb.model.HbaseTable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
@ActiveProfiles(profiles = {"UAT"})
public class HbaseTest {

    //日志
    private final static Logger logger = LoggerFactory.getLogger(HbaseTest.class);

    @Autowired
    private HbaseUtil hbaseUtil;

    private String tableName = "TestTable";

    @Test
    public void testCreateTable() {
        boolean result = hbaseUtil.createTable(tableName, new String[]{"family1", "family2", "family3", "family4"});
        logger.warn("logger:{}", result);
    }


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


    @Test
    public void testGetTable() {
        try {
            HQueryRow hQueryRow = hbaseUtil.queryByRowId(tableName, "row-0001");
            logger.warn("hQueryRow:{}", hQueryRow.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAllTable() {
        try {
            List<HQueryRow> hQueryRowList = hbaseUtil.queryAll(tableName);
            logger.warn("hQueryRowList:{}", hQueryRowList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelRow() {
        try {
            boolean result = hbaseUtil.deleteRowByRowKey(tableName, "row-0001");
            logger.warn("logger:{}", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelTable() {
        try {
            boolean result = hbaseUtil.deleteTable(tableName);
            logger.warn("logger:{}", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
