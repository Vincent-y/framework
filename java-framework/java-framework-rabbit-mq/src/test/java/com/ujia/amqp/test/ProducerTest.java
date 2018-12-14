package com.ujia.amqp.test;

import com.java.rabbitmq.producer.RabbitMQProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * <p>生产者
 * <p>@author DRAGON
 * <p>@date 2015年6月3日
 * <p>@version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "classpath:spring/rabbitmq-producer-config-test.xml"})
@ActiveProfiles(profiles={"development"})
public class ProducerTest {

	private final static Logger logger = LoggerFactory.getLogger(ProducerTest.class);
	
	@Autowired
	private RabbitMQProducer producer;
	
	@Test
	public void springDItest() throws IOException{
		logger.debug(producer.toString());
	}

}
