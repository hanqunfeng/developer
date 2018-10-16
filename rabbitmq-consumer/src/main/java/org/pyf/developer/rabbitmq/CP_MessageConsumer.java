package org.pyf.developer.rabbitmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 消息队列 - 消息消费者
 * Created by hanqf on 2018/10/16 14:37.
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix="spring.rabbitmq",name = "enabled",havingValue = "true")
@RabbitListener(queues = CP_QueueConstants.MESSAGE_QUEUE_NAME) //监听消息队列
public class CP_MessageConsumer {

    @RabbitHandler//具体执行动作
    public void handler(@Payload CP_MessageEntity messageEntity) {
        log.info("消费内容：{}", JSON.toJSONString(messageEntity));
    }
}
