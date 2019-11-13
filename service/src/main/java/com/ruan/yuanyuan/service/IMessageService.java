package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.entity.RabbitMessage;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-20
 * Time: 17:14
 * version:
 * Description:
 */
public interface IMessageService extends IService<RabbitMessage> {

    /**
     * 根据消息ID查询消息
     * @param messageId 消息ID
     * @return
     */
    RabbitMessage findByMessageId(String messageId);

    /**
     * 插入消息
     * @param rabbitMessage 消息实体
     */
    boolean insert(RabbitMessage rabbitMessage);

    /**
     * 根据消息ID修改消息
     * @param rabbitMessage 消息
     */
    boolean updateById(RabbitMessage rabbitMessage);

    /**
     * 查询所有的消息数据
     * @return
     */
    List<RabbitMessage> findAll();

}
