package com.java.dbhelper;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
 * <p>动态数据源实现读写分离
 * <p>@author dragon
 * <p>@date 2018年5月11日
 * <p>@version 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource{


    @Override
    protected Object determineCurrentLookupKey() {
        DynamicDataSourceGlobal dynamicDataSourceGlobal = DynamicDataSourceHolder.getDataSource();
        if( dynamicDataSourceGlobal == null || dynamicDataSourceGlobal == DynamicDataSourceGlobal.WRITE){
            return DynamicDataSourceGlobal.WRITE.name();
        }
        return DynamicDataSourceGlobal.READ.name();
    }


}

