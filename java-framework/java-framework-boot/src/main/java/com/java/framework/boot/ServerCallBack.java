package com.java.framework.boot;

import org.springframework.context.ApplicationContext;

/**
 * Created by dragon-yeah on 17-5-27.
 */
public interface ServerCallBack {

    public void start(ApplicationContext context);

    public void stop(ApplicationContext context);
}
