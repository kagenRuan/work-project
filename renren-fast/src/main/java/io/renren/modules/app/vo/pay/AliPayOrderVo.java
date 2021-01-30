package io.renren.modules.app.vo.pay;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @ClassName AliPayOrderVo
 * @Author ruanyuanyuan
 * @Date 2021/1/27-21:45
 * @Version 1.0
 * @Description TODO
 **/
@Data
@ApiModel("支付宝小程序支付订单VO")
public class AliPayOrderVo {

    @NotBlank(message = "订单ID不能为空")
    @Min(1)
    private String orderId;
}
