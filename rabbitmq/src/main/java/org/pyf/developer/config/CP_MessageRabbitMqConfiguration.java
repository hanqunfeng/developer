package org.pyf.developer.config;

import org.pyf.developer.rabbitmq.CP_QueueConstants;
import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置类
 * 这里只提供了一个普通队列和一个延迟队列的配置，可以根据需要自行在项目中增加配置
 * Created by hanqf on 2018/10/16 14:28.
 */

@Configuration
@ConditionalOnProperty(prefix="spring.rabbitmq",name = "enabled",havingValue = "true")
public class CP_MessageRabbitMqConfiguration {
    /**
     * 交换配置，rabbitmq重用到的有三种交换类型，实际上支持五种，可以查看AbstractExchange的实现类
     * DirectExchange：路由键方式转发消息，会将消息中的RoutingKey与该Exchange关联的所有Binding中的BindingKey进行比较，如果相等，则发送到该Binding对应的Queue中。有一个需要注意的地方：如果找不到指定的exchange，就会报错。但routing key找不到的话，不会报错，这条消息会直接丢失，所以此处要小心。
     * TopicExchange： 主题匹配方式转发消息 ，一对部分
     * FanoutExchange：广播方式转发消息，Fanout 扇出，顾名思义，就是像风扇吹面粉一样，吹得到处都是。如果使用fanout类型的exchange，那么routing key就不重要了。因为凡是绑定到这个exchange的queue，都会受到消息。
     *
     * 创建交换类型时要为其起个名称
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
     * 消息队列声明，可以声明多个队列bean，定义多个队列名称即可
     *
     * @return
     */
    @Bean
    public Queue messageQueue() {
        return QueueBuilder.durable(CP_QueueConstants.MESSAGE_QUEUE_NAME)
                .build();
    }

    /**
     * 消息绑定，可以声明多个绑定bean，将不同的消息队列绑定到交换类型中
     * 因为这里注册的是DirectExchange，所以消息队列绑定时route_key就有用了，因为消息发布时也是要指定route_key的，只有绑定到这个route_key对应的queue才会接收到消息
     *
     * @return
     */
    @Bean
    public Binding messageBinding() {
        return BindingBuilder.bind(messageQueue())
                .to(messageDirectExchange())
                .with(CP_QueueConstants.MESSAGE_ROUTE_KEY);
    }







    //以下为延时队列配置
    //延迟发送是指延迟指定的时间再发送消息
    //
    //比如：用户下单后有30分钟时间付款
    //注意：消息消费者不能监听延迟队列名称，否则消息发布会立即被执行，达不到延迟的目的
    /**
     * 消息中心延迟消费交换配置
     *
     * @return
     */
    @Bean
    DirectExchange messageTtlDirect() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(CP_QueueConstants.MESSAGE_EXCHANGE_TTL)
                .durable(true)
                .build();
    }
    /**
     * 消息中心TTL队列
     * 到期后会将当前队列转发到指定的EXCHANGE和ROUTE_KEY多绑定的队列上，所以消息消费者监听的消息队列实际上是转发后的队列名称，由监听后者的队列执行
     *
     * @return
     */
    @Bean
    Queue messageTtlQueue() {
        return QueueBuilder
                .durable(CP_QueueConstants.MESSAGE_QUEUE_NAME_TTL)
                // 配置到期后转发的交换
                .withArgument("x-dead-letter-exchange", CP_QueueConstants.MESSAGE_EXCHANGE)
                // 配置到期后转发的路由键
                .withArgument("x-dead-letter-routing-key", CP_QueueConstants.MESSAGE_ROUTE_KEY)
                .build();
    }

    /**
     * 消息中心TTL绑定实际消息中心实际消费交换机
     *
     * @param messageTtlQueue
     * @param messageTtlDirect
     * @return
     */
    @Bean
    public Binding messageTtlBinding(Queue messageTtlQueue, DirectExchange messageTtlDirect) {
        return BindingBuilder
                .bind(messageTtlQueue)
                .to(messageTtlDirect)
                .with(CP_QueueConstants.MESSAGE_ROUTE_KEY_TTL);
    }
}
