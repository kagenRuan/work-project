package io.renren.modules.app.vo.pay;

import io.swagger.annotations.Api;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName AliNativrPayVo
 * @Author ruanyuanyuan
 * @Date 2021/1/28-15:16
 * @Version 1.0
 * @Description TODO
 **/
@Data
@Api("支付宝Native支付Vo")
public class AliNativePayVo {

    @NotBlank(message = "订单ID不能为空")
    private String outTradeNo;
}
