package com.java.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * rocket mq 生產者
 */
@Slf4j
public class RocketMQProducer {

    private String defaultTopic;
    private DefaultMQProducer defaultMQProducer;

    public void setDefaultMQProducer(DefaultMQProducer defaultMQProducer) {
        this.defaultMQProducer = defaultMQProducer;
    }

    public void setDefaultTopic(String defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

    public boolean send(String body,String tags,String keys,String topic){
        if(StringUtils.isEmpty(body)){
            log.error("body is null");
            return false;
        }

        if(StringUtils.isEmpty(topic) && StringUtils.isEmpty(defaultTopic)){
            log.error("no available topic");
            return false;
        }

        if(StringUtils.isEmpty(topic)){
            topic = defaultTopic;
        }
        Message message = new Message(topic,tags,keys,body.getBytes());
        return send(message);
    }


    public boolean send(Message message){
        if(message == null){
            log.error("message is null");
            return false;
        }
        try {
            SendResult result = defaultMQProducer.send(message);
            log.info("send result:{}",result.toString());
        } catch (MQClientException|RemotingException|MQBrokerException|InterruptedException e) {
            log.error("rocketmq send error",e);
            return false;
        }
        return true;
    }





}
