package com.java.hb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PageFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * hbase查询条件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HQuery {

    private String tableName;
    private String family;
    private String qualifier;
    private String value;
    private String rowKey;

    private String startRow;
    private String stopRow;

    private Filter filter;
    private PageFilter pageFilter;
    private Scan scan;

    private List<HBaseColumn> columns = new ArrayList<HBaseColumn>();



}
