package com.java.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * rocket mq 消費者
 */
@Slf4j
public class RocketMQConsumerListener implements MessageListenerConcurrently{

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

        for (MessageExt messageExt : msgs) {
            log.debug(messageExt.toString());
            log.debug( new String(messageExt.getBody()));
        }

        log.debug("getDelayLevelWhenNextConsume={},getMessageQueue={},getDelayLevelWhenNextConsume={}",
                context.getDelayLevelWhenNextConsume(),
                context.getMessageQueue().toString(),
                context.getDelayLevelWhenNextConsume());


        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
