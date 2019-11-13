package com.ruan.yuanyuan.vo;

import java.math.BigDecimal;

/**
 * @author ryy
 * @Title: 商品vo
 * @version 1.0
 * @Description: 商品vo
 * @date 2018/12/119:38
 */
public class ProductVo {
    /**
     * 商品ID
     */
    private String id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品库存
     */
    private int inventory;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 商品价格
     */
    private BigDecimal price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
