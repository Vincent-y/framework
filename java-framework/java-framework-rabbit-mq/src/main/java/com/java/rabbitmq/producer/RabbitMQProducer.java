package com.java.rabbitmq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;

/**
 * <p>rabbitMQ生产者
 * <p>@author DRAGON
 * <p>@date 2014年12月10日
 * <p>@version 1.0
 */
public class RabbitMQProducer {
	private String routeKey;
	
	private RabbitTemplate template;
	
	public RabbitMQProducer() {
		super();
	}

	public RabbitTemplate getTemplate() {
		return template;
	}

	public void setTemplate(RabbitTemplate template) {
		this.template = template;
	}

	public RabbitMQProducer(ApplicationContext ctx) {
		super();
		template = ctx.getBean(RabbitTemplate.class);
	}
	
	public String getRouteKey() {
		return routeKey;
	}

	public void setRouteKey(String routeKey) {
		this.routeKey = routeKey;
	}

	/**
	 * 生产者 生产消息
	 * @param object
	 * @throws InterruptedException
	 */
	public void sendMessage(Object object) throws InterruptedException {
		template.convertAndSend(routeKey,object);
	}
	

}
