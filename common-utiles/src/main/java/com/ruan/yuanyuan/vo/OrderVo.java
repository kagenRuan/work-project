package com.ruan.yuanyuan.vo;

import java.util.List;

/**
 * @author ryy
 * @Version 1.0
 * @Description: 订单VO
 * @date 2018/12/119:38
 */
public class OrderVo {
    /**
     * 订单ID
     */
    private String id;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 订单详情
     */
    private List<OrderDetailVo> orderDetailVos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<OrderDetailVo> getOrderDetailVos() {
        return orderDetailVos;
    }

    public void setOrderDetailVos(List<OrderDetailVo> orderDetailVos) {
        this.orderDetailVos = orderDetailVos;
    }
}
