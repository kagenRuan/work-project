package io.renren.modules.app.form.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @ClassName UserOrderForm
 * @Author ruanyuanyuan
 * @Date 2021/1/24-22:41
 * @Version 1.0
 * @Description TODO 订单支付接受参数类
 **/
@Data
@ApiModel(value = "订单支付表单")
public class PayOrderForm {

    //订单ID
    @ApiModelProperty(value = "订单ID")
    @Min(1)//最小值为1
    @NotBlank(message="订单ID不能为空")
    private String id;

}
