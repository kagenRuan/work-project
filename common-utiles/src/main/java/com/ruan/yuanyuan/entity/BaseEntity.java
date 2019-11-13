package com.ruan.yuanyuan.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruan.yuanyuan.enums.Yum;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * User: ruanyuanyuan
 * Date: 2019-05-23
 * Time: 16:24
 * version:
 * Description: 父类
 */
public class BaseEntity implements Serializable {

    @TableId
    private String id;

    /**
     * 是否有效
     */
    @TableField("is_valid")
    private String isValid;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建事件
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改人
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsValid() {
        return StringUtils.isEmpty(isValid)?Yum.YES.getCode():isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return ObjectUtils.isEmpty(createTime)?new Date():createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return ObjectUtils.isEmpty(updateTime)?new Date():updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void initBean(){
        this.createTime=new Date();
        this.updateTime=new Date();
        this.isValid=Yum.YES.getCode();
    }
}
