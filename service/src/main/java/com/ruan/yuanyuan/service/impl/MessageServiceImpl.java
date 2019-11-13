package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.MessageMapper;
import com.ruan.yuanyuan.entity.RabbitMessage;
import com.ruan.yuanyuan.enums.Yum;
import com.ruan.yuanyuan.exception.BusinessAssert;
import com.ruan.yuanyuan.exception.ExceptionUtil;
import com.ruan.yuanyuan.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-20
 * Time: 17:14
 * version: 1.0
 * Description:消息消费端
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper,RabbitMessage> implements IMessageService {


    @Autowired
    private MessageMapper messageMapper;
    /**
     * 查询消息
     * @param messageId 消息ID
     * @return
     */
    @Override
    public RabbitMessage findByMessageId(String messageId) {
        BusinessAssert.notBlank(messageId, ExceptionUtil.MessageExceptionEnum.MESSAGE_ID_NOT_NULL);
        RabbitMessage rabbitMessage =messageMapper.selectOne(new QueryWrapper<RabbitMessage>().eq("message_id",messageId));
        return rabbitMessage;
    }

    /**
     * 添加消息
     * @param rabbitMessage 消息实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(RabbitMessage rabbitMessage) {
       int result = messageMapper.insert(rabbitMessage);
       return result>0?true:false;
    }

    /**
     * 修改消息
     * @param rabbitMessage 消息ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(RabbitMessage rabbitMessage) {
        int result = messageMapper.updateById(rabbitMessage);
        return result>0?true:false;
    }

    /**
     * 查询所有的消息
     * @return
     */
    @Override
    public List<RabbitMessage> findAll() {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_valid",Yum.YES.getCode());
        return messageMapper.selectList(queryWrapper);
    }

}
