package com.java.tbhelper.strategy;

import com.java.tbhelper.Strategy;
import org.apache.commons.collections4.MapUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 按年分表（报表）
 */
public class YYYYStrategy implements Strategy {

    @Override
    public String convert(Map<String, Object> params) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
        String tableName = MapUtils.getString(params,Strategy.TABLE_NAME);
        tableName += "_" + sdf.format(new Date());
        return tableName;
    }

}
