package com.java.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

public class KafKaProducer {

    Logger logger = LoggerFactory.getLogger(KafKaProducer.class);

    private KafkaTemplate<Integer, String> kafkaTemplate;

    public void setKafkaTemplate(KafkaTemplate<Integer, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String message){
        ListenableFuture<SendResult<Integer, String>> res = kafkaTemplate.sendDefault(message);
        logger.warn("-------------------------",res.toString());
    }

    public void send(String topic,String message){
        ListenableFuture<SendResult<Integer, String>> res = kafkaTemplate.send(topic,message);
        logger.warn("-------------------------",res.toString());
    }

}