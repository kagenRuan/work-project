package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * User: ruanyuanyuan
 * Date: 2019-09-03
 * Time: 15:18
 * version:1.0
 * Description: 网关路由表
 */
@TableName("yy_zuul_api_route")
public class ZuulApiRoute extends BaseEntity {
    /**
     * 请求资源路径
     */
    @TableField("path")
    private String path;
    /**
     * 服务ID
     */
    @TableField("service_id")
    private String serviceId;
    /**
     * 请求资源url
     */
    @TableField("url")
    private String url;
    /**
     * 前缀
     */
    @TableField("strip_prefix")
    private boolean stripPrefix = true;
    /**
     * 重试
     */
    @TableField("retryable")
    private Boolean retryable;
    /**
     * 是否开启
     */
    @TableField("enabled")
    private Boolean enabled;
    /**
     * 服务类型
     */
    private String type;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isStripPrefix() {
        return stripPrefix;
    }

    public void setStripPrefix(boolean stripPrefix) {
        this.stripPrefix = stripPrefix;
    }

    public Boolean getRetryable() {
        return retryable;
    }

    public void setRetryable(Boolean retryable) {
        this.retryable = retryable;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
