package com.java.rabbitmq.producer;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * <p>rabbitMQ 不用配置文件
 * <p>@author DRAGON
 * <p>@date 2015年3月9日
 * <p>@version 1.0
 */
public class RabbitMQService {

	/**
	 *  发送消息
	 * @param connectionFactory 连接器
	 * @param queueName 队列名
	 * @param exchangeName  转换器 
	 * @param routingKey 路由key
	 * @param msg
	 */
	public static void sendMsg(String msg,String queueName,String exchangeName,String routingKey,ConnectionFactory connectionFactory){
		
		RabbitTemplate template = new RabbitTemplate();
		
		RabbitAdmin admin = null;
		//admin
		if (null == admin) {
			admin = new RabbitAdmin(connectionFactory);
		}
		
		//转换器
		TopicExchange exchange = new TopicExchange(exchangeName);
		admin.declareExchange(exchange);
		
		//queues队列
		Queue queue = new Queue(queueName);
		admin.declareQueue(queue);
		//路由key
		admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));


		//群发送
		template.setConnectionFactory(connectionFactory);
		template.setExchange(exchangeName);
		template.setRoutingKey(routingKey);
		
		//发送
		template.convertAndSend(msg);
	}
	
}
