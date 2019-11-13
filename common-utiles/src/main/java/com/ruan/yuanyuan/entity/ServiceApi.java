package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * User: ruanyuanyuan
 * Date: 2019-09-03
 * Time: 15:30
 * version:1.0
 * Description:所有的API请求
 */
@TableName("service")
public class ServiceApi extends BaseEntity{
    /**
     * 服务名称
     */
    @TableField("name")
    private String name;
    /**
     * 服务路径
     */
    @TableField("path")
    private String path;
    /**
     * 服务请求路径
     */
    @TableField("url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
