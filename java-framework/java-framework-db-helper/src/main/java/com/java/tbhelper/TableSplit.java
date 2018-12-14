package com.java.tbhelper;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE , ElementType.METHOD })
public @interface TableSplit {

    //是否分表
    public boolean split() default false;//默认部分表

    //表名
    public String tablename();


    public String field() default "";

    //获取分表策略
    public String strategy();

}
