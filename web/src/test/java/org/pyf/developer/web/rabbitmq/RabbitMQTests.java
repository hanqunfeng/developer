package org.pyf.developer.web.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.pyf.developer.rabbitmq.CP_MessageEntity;
import org.pyf.developer.service.auth.ISystemUserService;
import org.pyf.developer.service.rabbitmq.CP_IMessageSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/17 15:42.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RabbitMQTests {

    /**
     * 消息队列 - 消息提供者 注入
     */
    @Autowired
    private CP_IMessageSevice messageSevice;
    @Autowired
    private ISystemUserService systemUserService;

    /**
     * 测试发送消息队列方法
     */
    @Test
    public void test1(){
        CP_MessageEntity<SystemUser> messageEntity =  new CP_MessageEntity();
        messageEntity.setContent("SystemUser");
        messageEntity.setObject(systemUserService.findById("admin"));
        // 将实体实例写入消息队列
        messageSevice.sendMessage(messageEntity);
    }

    /**
     * 测试发送延迟消息队列方法
     */
    @Test
    public void test2(){
        CP_MessageEntity<SystemUser> messageEntity =  new CP_MessageEntity();
        messageEntity.setContent("SystemUser");
        messageEntity.setObject(systemUserService.findById("admin"));
        // 将实体实例写入消息队列，延迟10秒
        messageSevice.sendMessage(messageEntity,10000);
    }
}
