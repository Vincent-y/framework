package com.java.hb;

import com.java.hb.model.HBaseColumn;
import com.java.hb.model.HQuery;
import com.java.hb.model.HQueryRow;
import com.java.hb.model.HbaseTable;
import com.java.validate.Validate;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * hbase util
 * 2018-05-20
 */
public class HbaseUtil {

    protected final static Logger logger = LoggerFactory.getLogger(HbaseConnectionFactory.class);

    private Connection connection;

    private HbaseConnectionFactory connectionFactory;

    public HbaseUtil(HbaseConnectionFactory hbaseConnectionFactory) {
        this.connection = hbaseConnectionFactory.getConnection();
        this.connectionFactory = hbaseConnectionFactory;
    }


    private void close() {
        connectionFactory.close();
    }

    /**
     * 创建表
     * @param tableStr
     * @param familyNames
     * @return
     */
    public boolean createTable(String tableStr, String[] familyNames) {
        try {
            Admin admin = this.connection.getAdmin();
            TableName tableName = TableName.valueOf(tableStr);

            if (admin.tableExists(tableName)) {// 如果存在要创建的表，那么先删除，再创建
                logger.debug("{} is exist now",tableName);
                return false;
            }

            HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
            // 添加表列信息
            if (familyNames != null && familyNames.length > 0) {
                for (String familyName : familyNames) {
                    tableDescriptor.addFamily(new HColumnDescriptor(familyName));
                }
            }
            admin.createTable(tableDescriptor);
        } catch (IOException e) {
            logger.error("create table error", e);
            return false;
        }
        logger.debug("create table success");
        return true;
    }

    /**
     * 删除表
     * @param tableName0
     * @return
     */
    public boolean deleteTable(String tableName0) {
        try {
            Admin admin = this.connection.getAdmin();
            TableName tableName = TableName.valueOf(tableName0);
            if (admin.tableExists(tableName)) {
                admin.disableTable(tableName);
                admin.deleteTable(tableName);
            }
        } catch (IOException e) {
            logger.error("detele table error", e);
            return false;
        }
        return true;
    }


    /**
     * 禁止表
     * @param tableName0
     * @return
     */
    public boolean disableTable(String tableName0) {
        try {
            Admin admin = this.connection.getAdmin();
            TableName tableName = TableName.valueOf(tableName0);
            if (admin.tableExists(tableName)) {
                admin.disableTable(tableName);
            }
        } catch (IOException e) {
            logger.error("disable table error", e);
            return false;
        }
        return true;
    }


    /**
     * 插入数据
     * @param hbaseTable
     * @return
     * @throws Exception
     */
    public boolean insertData(HbaseTable hbaseTable) throws Exception {
        logger.debug("start insert data");
        Table table = this.connection.getTable(TableName.valueOf(hbaseTable.getTableName()));
        Put put = new Put(hbaseTable.getRowKey().getBytes());// 一个PUT代表一行数据，再NEW一个PUT表示第二行数据,每行一个唯一的ROWKEY，此处rowkey为put构造方法中传入的值
        put.addColumn(hbaseTable.getHBaseColumn().getFamily().getBytes(),
                        hbaseTable.getHBaseColumn().getQualifier().getBytes(),
                        hbaseTable.getHBaseColumn().getValue().getBytes());// 本行数据的第一列
        try {
            table.put(put);
        } catch (IOException e) {
            logger.error("insert table error", e);
            return false;
        }
        logger.debug("end insert data");
        return true;
    }

    /**
     * 删除行
     * @param tableName
     * @param rowKey
     * @return
     */
    public boolean deleteRowByRowKey(String tableName,String rowKey) {
        try {
            Table table = this.connection.getTable(TableName.valueOf(tableName));
            Delete delete = new Delete(rowKey.getBytes());
            table.delete(delete);//d1.addColumn(family, qualifier);d1.addFamily(family);
        } catch (IOException e) {
            logger.error("delete row error", e);
            return false;
        }
        return true;
    }


    /**
     * 查询全部，一般不用
     * @param tableName
     * @throws Exception
     */
    public List<HQueryRow> queryAll(String tableName) throws Exception {
        List<HQueryRow> hQueryRowList = null;
        ResultScanner rs = null;
        try {
            Table table = this.connection.getTable(TableName.valueOf(tableName));
            rs = table.getScanner(new Scan());
            hQueryRowList = getResult(rs);
        } catch (IOException e) {
            logger.error("delete row error", e);
        }finally {
            rs.close();
        }
        return hQueryRowList;
    }


    /**
     * 获取 result by roleKey
     * @param tableName
     * @param rowKey
     * @return
     * @throws Exception
     */
    public HQueryRow queryByRowId(String tableName, String rowKey) throws Exception {
        HQueryRow hQueryRow = null;
        try {
            Table table = this.connection.getTable(TableName.valueOf(tableName));
            Get scan = new Get(rowKey.getBytes());// 根据rowkey查询
            Result result = table.get(scan);


            hQueryRow = new HQueryRow();
            hQueryRow.setRowKey(rowKey);

            List<HBaseColumn> hBaseColumnList = new ArrayList<HBaseColumn>();
            HBaseColumn hBaseColumn = null;
            for (Cell keyValue : result.rawCells()) {
                String family    = new String(CellUtil.cloneFamily(keyValue));
                String qualifier = new String(CellUtil.cloneQualifier(keyValue));
                String value     = new String(CellUtil.cloneValue(keyValue));

                hBaseColumn = new HBaseColumn();
                hBaseColumn.setFamily(family);
                hBaseColumn.setQualifier(qualifier);
                hBaseColumn.setValue(value);
                hBaseColumnList.add(hBaseColumn);

                hQueryRow.setHBaseColumn(hBaseColumnList);

                logger.warn("family：" + family
                        + "qualifier："  + qualifier
                        + "value："+ value);
            }
            hQueryRow.setHBaseColumn(hBaseColumnList);

        } catch (IOException e) {
            logger.error("delete row error", e);
            return null;
        }
        return hQueryRow;
    }

    /**
     * 根据条件查询表
     * @param hQuery
     */
    public List<HQueryRow> queryByCondition(HQuery hQuery) {
        List<HQueryRow> hQueryRowList = null;
        ResultScanner rs = null;
        try {
            if(hQuery == null){
                return null;
            }

            Table table = this.connection.getTable(TableName.valueOf(hQuery.getTableName()));
            Filter filter = hQuery.getFilter();
            Scan scan = hQuery.getScan();
            if(scan == null){
                scan = new Scan();
                if(Validate.isEmpty(hQuery.getStartRow()) && Validate.isEmpty(hQuery.getStopRow()) ){
                    scan.setStartRow(Bytes.toBytes(hQuery.getStartRow()));
                    scan.setStopRow(Bytes.toBytes(hQuery.getStopRow()));
                }
            }
            if(filter != null){
                scan.setFilter(filter);
            }
            rs = table.getScanner(scan);
            hQueryRowList = getResult(rs);

        } catch (Exception e) {
            logger.error("query row error", e);
        }finally {
            rs.close();
        }
        return hQueryRowList;
    }

    /**
     * 多条件查询 一般不使用
     * @param tableName
     * @param hQueryList
     */
    public List<HQueryRow> queryByConditions(String tableName, List<HQuery> hQueryList) {
        ResultScanner rs = null;
        List<HQueryRow> hQueryRowList = null;
        try {
            Table table = this.connection.getTable(TableName.valueOf(tableName));
            List<Filter> filters = new ArrayList<Filter>();

            if (hQueryList != null && !hQueryList.isEmpty()) {
                for (HQuery hQuery : hQueryList) {
                    Filter filter = new SingleColumnValueFilter(Bytes.toBytes(hQuery.getFamily()),
                                                                Bytes.toBytes(hQuery.getQualifier()),
                                                                CompareFilter.CompareOp.EQUAL,
                                                                Bytes.toBytes(hQuery.getValue()));

                    filters.add(filter);
                }
            }

            FilterList filterList = new FilterList(filters);
            Scan scan = new Scan();
            scan.setFilter(filterList);
            rs = table.getScanner(scan);

            hQueryRowList = getResult(rs);

        } catch (Exception e) {
            logger.error("query row error", e);
        }finally {
            rs.close();
        }
        return hQueryRowList;
    }


    private List<HQueryRow> getResult(ResultScanner rs){
        List<HQueryRow>  hQueryRowList = new ArrayList<HQueryRow>();
        HQueryRow hQueryRow = null;
        for (Result result : rs) {
            String rowKey = new String(result.getRow());
            logger.warn("get rowkey:" + rowKey);

            hQueryRow = new HQueryRow();
            hQueryRow.setRowKey(rowKey);

            List<HBaseColumn> hBaseColumnList = new ArrayList<HBaseColumn>();
            HBaseColumn hBaseColumn = null;
            for (Cell keyValue : result.rawCells()) {
                String family    = new String(CellUtil.cloneFamily(keyValue));
                String qualifier = new String(CellUtil.cloneQualifier(keyValue));
                String value     = new String(CellUtil.cloneValue(keyValue));

                logger.warn("family：" + family
                        + "qualifier："  + qualifier
                        + "value："+ value);

                hBaseColumn = new HBaseColumn();
                hBaseColumn.setFamily(family);
                hBaseColumn.setQualifier(qualifier);
                hBaseColumn.setValue(value);
                hBaseColumnList.add(hBaseColumn);

                hQueryRow.setHBaseColumn(hBaseColumnList);
            }
            hQueryRow.setHBaseColumn(hBaseColumnList);
            hQueryRowList.add(hQueryRow);
        }
        return hQueryRowList;
    }

}
