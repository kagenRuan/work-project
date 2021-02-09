package io.renren.modules.app.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @ClassName AliPayLoginVo
 * @Author ruanyuanyuan
 * @Date 2021/1/27-16:20
 * @Version 1.0
 * @Description TODO 支付宝登录VO
 **/
@Data
@ApiModel("支付宝登录VO")
public class AliPayLoginVo {

    @ApiModelProperty("支付宝登录临时凭证")
    @NotBlank(message = "支付宝登录临时凭证不能为空")
    private String authCode;

    @ApiModelProperty("支付宝用户昵称")
    @NotBlank(message = "支付宝用户昵称不能为空")
    private String nickName;

    @ApiModelProperty("支付宝用户头像")
    @NotBlank(message = "支付宝用户头像不能为空")
    private String photo;
}
