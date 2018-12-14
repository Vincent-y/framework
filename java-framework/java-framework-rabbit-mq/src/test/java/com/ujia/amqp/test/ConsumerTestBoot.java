package com.ujia.amqp.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * <p>测试消费者
 * <p>@author DRAGON
 * <p>@date 2015年6月3日
 * <p>@version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "classpath:spring/rabbitmq-consumer-config-test.xml"})
@ActiveProfiles(profiles={"development"})
public class ConsumerTestBoot{

	@Test
	public void testboot() throws IOException {
		System.out.println("start...");
		System.in.read();
	}
}
