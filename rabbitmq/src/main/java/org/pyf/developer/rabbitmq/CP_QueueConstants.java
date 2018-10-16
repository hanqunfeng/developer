package org.pyf.developer.rabbitmq;

/**
 * 队列常量配置
 * Created by hanqf on 2018/10/16 14:22.
 */


public interface CP_QueueConstants {

    /**
     * 消息交换
     */
    String MESSAGE_EXCHANGE = "message.direct.exchange";
    /**
     * 消息队列名称
     */
    String MESSAGE_QUEUE_NAME = "message.queue";
    /**
     * 消息路由键
     */
    String MESSAGE_ROUTE_KEY = "message.send";


    /**
     * 延迟消息交换
     */
    String MESSAGE_EXCHANGE_TTL = "message.direct.exchange_ttl";
    /**
     * 延迟消息队列名称
     */
    String MESSAGE_QUEUE_NAME_TTL = "message.queue_ttl";
    /**
     * 延迟消息路由键
     */
    String MESSAGE_ROUTE_KEY_TTL = "message.send_ttl";
}
