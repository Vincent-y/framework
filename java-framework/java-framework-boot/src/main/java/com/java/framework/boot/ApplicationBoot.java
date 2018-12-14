package com.java.framework.boot;

import com.google.common.util.concurrent.AbstractIdleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>通用启动类
 * <p>@author dragon
 * <p>@date 2015年10月10日
 * <p>@version 1.0
 */
public class ApplicationBoot extends AbstractIdleService {

    /**
     * 日志
     **/
    private static Logger logger = LoggerFactory.getLogger(ApplicationBoot.class);

    /**
     * spring配置名
     **/
    protected String configName = ""; // 如 "classpath:spring/dubbo-service-root.xml"

    /**
     * applicationName项目名
     **/
    protected String applicationName = "";

    protected ClassPathXmlApplicationContext context;

    protected ServerCallBack serverCallBack;

    public ApplicationBoot(String configName, String applicationName) {
        this.configName = configName;
        this.applicationName = applicationName;
    }

    public void setServerCallBack(ServerCallBack serverCallBack){
        this.serverCallBack = serverCallBack;
    }


    /**
     * 启动dubbo
     */
    public void startServer() {
        this.startAsync();
        try {
            Object lock = new Object();
            synchronized (lock) {
                while (true) {
                    lock.wait();
                }
            }
        } catch (InterruptedException ex) {
            logger.error("ignore interruption", ex);
        }
    }

    /**
     * Start the service.
     */
    @Override
    protected void startUp() throws Exception {
        logger.info("[{}服务启动中…]", applicationName);
        context = new ClassPathXmlApplicationContext(new String[]{configName});
        context.start();
        context.registerShutdownHook();

        if (this.serverCallBack != null){
            this.serverCallBack.start(context);
        }


        logger.info("[{}服务启动成功]", applicationName);
    }

    /**
     * Stop the service.
     */
    @Override
    protected void shutDown() throws Exception {
        if (this.serverCallBack != null){
            this.serverCallBack.stop(context);
        }
        context.stop();
        logger.info("[{} 服务关闭成功]", applicationName);
    }

}

