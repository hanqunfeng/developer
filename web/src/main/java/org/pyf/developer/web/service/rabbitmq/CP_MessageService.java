package org.pyf.developer.web.service.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.pyf.developer.service.rabbitmq.CP_IMessageSevice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/16 16:51.
 */

@Component
@ConditionalOnProperty(prefix="spring.rabbitmq",name = "enabled",havingValue = "false",matchIfMissing = true)
@Slf4j
public class CP_MessageService implements CP_IMessageSevice {

    @Override
    public void sendMessage(Object object) {
        log.info("rabbitmq is disabled");
    }

    @Override
    public void sendMessage(Object object, String exchange, String routerKey) {
        log.info("rabbitmq is disabled");
    }

    @Override
    public void sendMessage(Object object, long delayTimes) {
        log.info("rabbitmq is disabled");
    }

    @Override
    public void sendMessage(Object object, String exchange, String routerKey, long delayTimes) {
        log.info("rabbitmq is disabled");
    }
}
