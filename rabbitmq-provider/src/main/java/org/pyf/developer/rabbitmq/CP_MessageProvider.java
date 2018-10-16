package org.pyf.developer.rabbitmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        sendMessage(object,CP_QueueConstants.MESSAGE_EXCHANGE, CP_QueueConstants.MESSAGE_ROUTE_KEY);
    }

    @Override
    public void sendMessage(Object object, String exchange, String routerKey) {
        log.info("写入消息队列内容：{}", JSON.toJSONString(object));
        amqpTemplate.convertAndSend(exchange, routerKey, object);
    }

    @Override
    public void sendMessage(Object object, String exchange, String routerKey, long delayTimes) {
        if (!StringUtils.isEmpty(exchange)) {
            log.info("延迟：{}毫秒写入消息队列：{}，消息内容：{}", delayTimes, routerKey, JSON.toJSONString(object));
            // 执行发送消息到指定队列
            amqpTemplate.convertAndSend(exchange, routerKey, object, message -> {
                // 设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            });
        } else {
            log.error("未找到队列消息：{}，所属的交换机", exchange);
        }
    }

    @Override
    public void sendMessage(Object object, long delayTimes) {
        sendMessage(object,CP_QueueConstants.MESSAGE_EXCHANGE_TTL,CP_QueueConstants.MESSAGE_ROUTE_KEY_TTL,delayTimes);
    }
}
