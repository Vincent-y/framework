package com.java.tbhelper;

import com.alibaba.fastjson.JSONObject;
import com.java.spring.ApplicationHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>分表插件
 * <p>@author dragon
 * <p>@date 2018年5月11日
 * <p>@version 1.0
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class })})
public class TableSplitPlugin implements Interceptor {

    protected static final Logger logger = LoggerFactory.getLogger(TableSplitPlugin.class);

    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler   = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);

        BoundSql boundSql      = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        logger.info("excute SQL：{}",boundSql.getSql());

        Object parameterObject = metaStatementHandler.getValue("delegate.boundSql.parameterObject");
        doSplitTable(metaStatementHandler,parameterObject);

        // 传递给下一个拦截器处理
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        //
    }


    private void doSplitTable(MetaObject metaStatementHandler,Object param) throws Exception {
        String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        if (originalSql != null && !originalSql.equals("")) {
            logger.info("before excute SQL：{}" , originalSql);
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            String id = mappedStatement.getId();
            String className  = id.substring(0, id.lastIndexOf("."));
            String methodName = id.substring(id.lastIndexOf(".") + 1);
            Class<?> clazz = Class.forName(className);

            ParameterMap paramMap = mappedStatement.getParameterMap();
            Method method = findMethod(clazz.getDeclaredMethods(), methodName);

            // 根据配置自动生成分表SQL

            TableSplit tableSplit = null;
            if (method != null) {
                tableSplit = method.getAnnotation(TableSplit.class);
            }
            if (tableSplit == null) {
                tableSplit = clazz.getAnnotation(TableSplit.class);
            }
            logger.info("paramMap：{}",JSONObject.toJSONString(paramMap));

            if (tableSplit != null && tableSplit.split() && StringUtils.isNotBlank(tableSplit.strategy())) {

                if(!tableSplit.split()){//不分表
                    return;
                }

                StrategyManager strategyManager = ApplicationHelper.getBean(StrategyManager.class);
                String convertedSql = null;
                String[] strategies = tableSplit.strategy().split(",");

                //获取分表策略来处理分表
                Map<String,Object> params = null;
                for (String stra : strategies) {
                    Strategy strategy  = strategyManager.getStrategy(stra);
                    params = new HashMap<String,Object>();
                    params.put(Strategy.TABLE_NAME,  tableSplit.tablename());
                    params.put(Strategy.SPLIT_FIELD, tableSplit.field());
                    params.put(Strategy.EXECUTE_PARAM_DECLARE, paramMap);
                    params.put(Strategy.EXECUTE_PARAM_VALUES,  param);

                    convertedSql = originalSql.replaceAll(tableSplit.tablename(),strategy.convert(params));
                }
                if(convertedSql == null || convertedSql.equals("")){
                    convertedSql = originalSql;//使用原来的sql
                }
                metaStatementHandler.setValue("delegate.boundSql.sql",convertedSql);
                logger.info("after excute SQL：{}",convertedSql);
            }

        }

    }

    private Method findMethod(Method[] methods, String methodName) {
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

}