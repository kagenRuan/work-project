package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ryy
 * @Title: 订单详情实体
 * @Description: 订单详情
 * @date 2019/1/10
 */
@TableName("yy_order_detail")
public class OrderDetail extends BaseEntity {

    /**
     * 商品ID
     */
    @TableField("id_product")
    private String idProduct;
    /**
     * 订单ID
     */
    @TableField("order_sn")
    private String orderSn;
    /**
     * 商品名称
     */
    @TableField("name")
    private String name;
    /**
     * 商品数量
     */
    @TableField("num")
    private int num;
    /**
     * 商品单价
     */
    @TableField("price")
    private BigDecimal price;
    /**
     * 商品总价
     */
    @TableField("total_price")
    private BigDecimal totalPrice;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}
