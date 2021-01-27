package io.renren.modules.app.vo.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @ClassName ScanCodePayOrderVo
 * @Author ruanyuanyuan
 * @Date 2021/1/26-21:32
 * @Version 1.0
 * @Description TODO 主要用于付款码支付接口接收参数
 **/
@Data
public class ScanCodePayOrderVo {

    @Pattern(regexp = "^1[0-5][0-9]{16}$",message = "付款码ID不正确")
    @ApiModelProperty("订单ID")
    @NotBlank
    private String authCode;

    @ApiModelProperty("订单ID")
    @Min(1)
    private String id;

}