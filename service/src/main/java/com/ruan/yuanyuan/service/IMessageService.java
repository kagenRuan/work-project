package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.entity.RabbitMessage;
import com.ruan.yuanyuan.vo.RabbitMessageVo;

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
     *
     * @param rabbitMessageVo 消息ID
     * @return
     */
    boolean updateByMessageId(RabbitMessageVo rabbitMessageVo);

    /**
     * 插入消息
     *
     * @param rabbitMessageVo 消息实体
     */
    boolean saveAndUpdate(RabbitMessageVo rabbitMessageVo);

    /**
     * 根据消息ID修改消息
     *
     * @param rabbitMessage 消息
     */
    boolean updateById(RabbitMessage rabbitMessage);

    /**
     * 查询所有的消息数据
     *
     * @return
     */
    List<RabbitMessage> findAll();

}
