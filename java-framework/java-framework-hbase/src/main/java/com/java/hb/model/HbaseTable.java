package com.java.hb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HbaseTable {
    private String tableName;
    private String rowKey;
    private HBaseColumn hBaseColumn;
}
