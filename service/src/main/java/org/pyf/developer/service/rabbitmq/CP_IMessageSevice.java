package org.pyf.developer.service.rabbitmq;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/16 16:41.
 */


public interface CP_IMessageSevice {

    /**
     * 发送消息
     * @param object
     */
    public void sendMessage(Object object);
    public void sendMessage(Object object, String exchange, String routerKey);

    /**
     * 延迟发送消息
     *
     * 延迟发送是指延迟指定的时间再发送消息
     *
     * 比如：用户下单后有30分钟时间付款
     * @param object
     * @param delayTimes
     */
    public void sendMessage(Object object,final long delayTimes);
    public void sendMessage(Object object, String exchange, String routerKey, final long delayTimes);
}
