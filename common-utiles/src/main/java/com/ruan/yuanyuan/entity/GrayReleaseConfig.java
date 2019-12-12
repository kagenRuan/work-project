package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * User: ruanyuanyuan
 * Date: 2019-09-03
 * Time: 15:28
 * version:1.0
 * Description:灰度发布
 */
@TableName("yy_gray_release_config")
public class GrayReleaseConfig extends BaseEntity {

    /**
     * 服务ID
     */
    @TableField("service_id")
    private String serviceId;
    /**
     * 请求路径
     */
    private String path;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
