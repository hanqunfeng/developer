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
}
