package com.ruan.yuanyuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruan.yuanyuan.entity.PowerEtc;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-26
 * Time: 14:34
 * version:1.0
 * Description:幂等消息服务接口
 */
public interface IPowerEtcService extends IService<PowerEtc> {

    /**
     * 根据消息ID查询
     * @param messageId 消息ID
     * @return
     */
    PowerEtc findByMessageId(String messageId);

    /**
     * 保存消息幂等
     * @param powerEtc
     */
    void  add(PowerEtc powerEtc);
}
