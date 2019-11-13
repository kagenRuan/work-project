package com.ruan.yuanyuan.vo;

import java.math.BigDecimal;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-22
 * Time: 14:22
 * version:1.0
 * Description:订单详情VO
 */
public class OrderDetailVo {
    /**
     * 订单详情ID
     */
    private String id;
    /**
     * 商品ID
     */
    private String idProduct;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品数量
     */
    private int num;
    /**
     * 商品单价
     */
    private BigDecimal price;
    /**
     * 商品总价
     */
    private BigDecimal totalPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
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
}
