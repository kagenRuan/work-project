package com.ruan.yuanyuan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruan.yuanyuan.dao.PowerEtcMapper;
import com.ruan.yuanyuan.entity.PowerEtc;
import com.ruan.yuanyuan.enums.Yum;
import com.ruan.yuanyuan.service.IPowerEtcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-26
 * Time: 14:35
 * version:1.0
 * Description:幂等消息服务
 */
@Service
public class PowerEtcServiceImpl extends ServiceImpl<PowerEtcMapper, PowerEtc> implements IPowerEtcService {

    /**
     * 查询消息
     * @param messageId 消息ID
     * @return
     */
    @Override
    public PowerEtc findByMessageId(String messageId) {
        return baseMapper.selectOne(new QueryWrapper<PowerEtc>().eq("message_id",messageId).eq("is_valid", Yum.YES.getCode()));
    }

    /**
     * 保存消息幂等
     * @param powerEtc
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(PowerEtc powerEtc) {
        baseMapper.insert(powerEtc);
    }
}
