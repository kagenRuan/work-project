package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author ryy
 * @Title: 商品实体
 * @Package 商品实体类
 * @Description: 商品实体类
 * @date 2018/12/119:36
 */
@TableName("yy_product")
public class Product extends BaseEntity {

    /**
     * 商品名称
     */
    @TableField("name")
    private String name;
    /**
     * 商品库存
     */
    @TableField("inventory")
    private int inventory;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 商品价格
     */
    @TableField("price")
    private BigDecimal price;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
