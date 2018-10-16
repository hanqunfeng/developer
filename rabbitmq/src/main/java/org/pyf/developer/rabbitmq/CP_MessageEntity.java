package org.pyf.developer.rabbitmq;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息实体类
 * Created by hanqf on 2018/10/16 14:24.
 */

@Data
public class CP_MessageEntity<T> implements Serializable {
    /**
     * 消息内容，消息内容为字符串时传递
     */
    private String content;

    /**
     * 消息对象，消息为对象时传递
     */
    private T object;
}