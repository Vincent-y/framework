package com.java.kafka;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;

public class KafKaConsumer  implements MessageListener<Integer, String> {

    Logger logger = LoggerFactory.getLogger(KafKaConsumer.class);

    @Override
    public void onMessage(ConsumerRecord<Integer, String> record) {
        logger.info("=============kafkaConsumer开始消费=============");
        String topic  = record.topic();
        Integer key   = record.key();
        String value  = record.value();
        long offset   = record.offset();
        int partition = record.partition();

        logger.info("-------------topic:"+topic);
        logger.info("-------------value:"+value);
        logger.info("-------------key:"+key);
        logger.info("-------------offset:"+offset);
        logger.info("-------------partition:"+partition);

        logger.info("~~~~~~~~~~~~~kafkaConsumer end~~~~~~~~~~~~~");
    }
}