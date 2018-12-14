package com.ujia.amqp.test;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * <p>测试消费者
 * <p>@author DRAGON
 * <p>@date 2015年6月3日
 * <p>@version 1.0
 */
public class ConsumerTest implements MessageListener{

	@Override
	public void onMessage(Message message) {
		
	}
}
