package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName OrderEntity
 * @Author ruanyuanyuan
 * @Date 2021/1/24-22:15
 * @Version 1.0
 * @Description TODO 订单支付实体
 **/
@Data
@TableName("tb_order")
public class OrderEntity implements Serializable {

    /**
     * 订单ID
     */
    @TableId
    private Long id;
    /**
     * code
     */
    private String code;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 支付金额
     */
    private BigDecimal amount;
    /**
     * 支付类型1、微信支付2、支付宝支付3、银联支付
     */
    private Date paymentType;
    /**
     * 订单状态1、未支付2、已支付3、已发货4、已签收5、已取消
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
}
