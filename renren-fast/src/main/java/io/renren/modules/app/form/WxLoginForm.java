/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 微信登录表单
 *
 * @author ruanyuanyuan
 */
@Data
@ApiModel(value = "微信小程序登录表单")
public class WxLoginForm {

    @ApiModelProperty(value = "小程序登录临时code")
    @NotBlank(message="小程序登录临时code")
    private String code;

    @ApiModelProperty(value = "用户图像")
    @NotBlank(message="用户图像")
    private String photo;

    @ApiModelProperty(value = "用户昵称")
    @NotBlank(message="用户昵称")
    private String nickName;


}
