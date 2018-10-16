package org.pyf.developer.rabbitmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.pyf.developer.service.rabbitmq.CP_IMessageSevice;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 消息队列 - 消息提供者
 * Created by hanqf on 2018/10/16 14:31.
 */

@Slf4j
@Component
@ConditionalOnProperty(prefix="spring.rabbitmq",name = "enabled",havingValue = "true")
public class CP_MessageProvider implements CP_IMessageSevice {

    /**
     * 消息队列模板
     */
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendMessage(Object object) {
        log.info("写入消息队列内容：{}", JSON.toJSONString(object));
        amqpTemplate.convertAndSend(CP_QueueConstants.MESSAGE_EXCHANGE, CP_QueueConstants.MESSAGE_ROUTE_KEY, object);
    }
}
