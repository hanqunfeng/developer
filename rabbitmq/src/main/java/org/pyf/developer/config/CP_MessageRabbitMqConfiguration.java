package org.pyf.developer.config;

import org.pyf.developer.rabbitmq.CP_QueueConstants;
import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置类
 * Created by hanqf on 2018/10/16 14:28.
 */

@Configuration
@ConditionalOnProperty(prefix="spring.rabbitmq",name = "enabled",havingValue = "true")
public class CP_MessageRabbitMqConfiguration {
    /**
     * 交换配置
     *
     * @return
     */
    @Bean
    public DirectExchange messageDirectExchange() {
        return (DirectExchange) ExchangeBuilder.directExchange(CP_QueueConstants.MESSAGE_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * 消息队列声明
     *
     * @return
     */
    @Bean
    public Queue messageQueue() {
        return QueueBuilder.durable(CP_QueueConstants.MESSAGE_QUEUE_NAME)
                .build();
    }

    /**
     * 消息绑定
     *
     * @return
     */
    @Bean
    public Binding messageBinding() {
        return BindingBuilder.bind(messageQueue())
                .to(messageDirectExchange())
                .with(CP_QueueConstants.MESSAGE_ROUTE_KEY);
    }
}
