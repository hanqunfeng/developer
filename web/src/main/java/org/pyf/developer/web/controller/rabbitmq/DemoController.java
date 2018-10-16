package org.pyf.developer.web.controller.rabbitmq;

import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.pyf.developer.rabbitmq.CP_MessageEntity;
import org.pyf.developer.service.auth.ISystemUserService;
import org.pyf.developer.service.rabbitmq.CP_IMessageSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/16 14:50.
 */

@RestController
@RequestMapping("/rabbitmq")
public class DemoController {

    /**
     * 消息队列 - 消息提供者 注入
     */
    @Autowired
    private CP_IMessageSevice messageSevice;
    @Autowired
    private ISystemUserService systemUserService;

    /**
     * 测试发送消息队列方法
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        CP_MessageEntity<SystemUser> messageEntity =  new CP_MessageEntity();
        messageEntity.setContent("SystemUser");
        messageEntity.setObject(systemUserService.findById("admin"));
        // 将实体实例写入消息队列
        messageSevice.sendMessage(messageEntity);
        return "Success";
    }


    /**
     * 测试发送延迟消息队列方法
     *
     * @return
     */
    @RequestMapping(value = "/delay")
    public String delay() {
        CP_MessageEntity<SystemUser> messageEntity =  new CP_MessageEntity();
        messageEntity.setContent("SystemUser");
        messageEntity.setObject(systemUserService.findById("admin"));
        // 将实体实例写入消息队列，延迟10秒
        messageSevice.sendMessage(messageEntity,10000);
        return "Success delay";
    }
}
