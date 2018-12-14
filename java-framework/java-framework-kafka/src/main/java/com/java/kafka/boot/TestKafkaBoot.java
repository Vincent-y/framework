package com.java.kafka.boot;

import com.java.framework.boot.ApplicationBoot;
import com.java.framework.boot.ServerCallBack;
import com.java.kafka.KafKaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

public class TestKafkaBoot {

    private static Logger logger = LoggerFactory.getLogger(TestKafkaBoot.class);

    private static String configName      = "classpath:spring/spring-context.xml";
    private static String applicationName = "TestKafka";


    public static void main(String[] args) {
        ApplicationBoot boot = new ApplicationBoot(configName, applicationName);
        boot.setServerCallBack(new ServerCallBack() {

            @Override
            public void start(ApplicationContext context) {
                KafKaProducer kafKaProducer = context.getBean(KafKaProducer.class);

                Scanner scanner = new Scanner(System.in);
                String str = null;
                try {
                    while (true) {
                        str = scanner.nextLine();
                        if (str.equals("q")) {
                            logger.debug("退出客户端...");
                            break;
                        }else{
                            if(null != str && !str.equals("")){
                                kafKaProducer.send("test_topic",str);
                            }
                        }
                    }
                    /** 停止 **/
                    stop(context);
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    scanner.close();
                }

            }

            @Override
            public void stop(ApplicationContext context) {
                // TODO Auto-generated method stub

            }
        });
        boot.startServer();
    }

}