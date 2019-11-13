package com.ruan.yuanyuan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruan.yuanyuan.entity.RabbitMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-20
 * Time: 17:05
 * version:
 * Description: 消息实体
 */
public interface MessageMapper extends BaseMapper<RabbitMessage> {

    /**
     * 根据消息ID查询消息
     * @param messageId 消息ID
     * @return
     */
    RabbitMessage findMessageById(@Param("messageId") String messageId);

    /**
     * 查询消息
     * @param rabbitMessage 消息实体
     */
    void insertMessage(@Param("rabbitMessage") RabbitMessage rabbitMessage);

    /**
     * 根据消息ID修改消息
     * @param rabbitMessage 消息ID
     */
    Integer updateMessageById(@Param("message") RabbitMessage rabbitMessage);

    /**
     * 根据消息ID修改消息状态
     * @param messageId 消息ID
     * @param status 状态
     * @return
     */
    Integer updateMessageByMessageId(@Param("messageId") String messageId, @Param("status") String status);

    /**
     * 批量修改
     * @param rabbitMessages
     * @return
     */
    Integer updateMessages(@Param("message") List<RabbitMessage> rabbitMessages);

}
