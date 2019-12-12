package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-26
 * Time: 14:29
 * version:1.0
 * Description: 业务本地消息幂等表
 */
@TableName("yy_power_etc")
public class PowerEtc extends BaseEntity {

    @TableField("message_id")
    private String MessageId;

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }
}
