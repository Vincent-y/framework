package com.java.hb.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * hbase列
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HBaseColumn {

    private String family;//列蔟
    private String qualifier; //key
    private String value;//value

}

