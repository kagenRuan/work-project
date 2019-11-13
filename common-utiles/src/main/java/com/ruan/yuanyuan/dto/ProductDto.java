package com.ruan.yuanyuan.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * User: ruanyuanyuan
 * Date: 2019-08-25
 * Time: 10:25
 * version:
 * Description:订单DTO
 */
public class ProductDto implements Serializable {

    private static final long serialVersionUID = -1101942916170677890L;
    /**
     * 店铺ID
     */
    private String shopId;
    /**
     * 商品ID
     */
    private String id;
    /**
     * 商品数量
     */
    private Integer num;
    /**
     * 商品单价
     */
    private BigDecimal price;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
