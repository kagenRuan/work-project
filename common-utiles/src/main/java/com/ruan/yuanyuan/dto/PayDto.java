package com.ruan.yuanyuan.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付DTO
 */
public class PayDto implements Serializable {

    /**
     * 交易单号
     */
    private String outTradeNo;
    /**
     * 订单标题
     */
    private String subject;
    /**
     * 订单金额
     */
    private BigDecimal totalAmount;
    /**
     * 描述
     */
    private String body;
    /**
     * 店铺ID
     */
    private String storeId;

}
