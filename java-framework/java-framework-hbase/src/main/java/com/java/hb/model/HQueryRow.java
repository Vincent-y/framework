package com.java.hb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * hbase查询条件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HQueryRow {
    private String rowKey;
    private List<HBaseColumn> hBaseColumn;

}
