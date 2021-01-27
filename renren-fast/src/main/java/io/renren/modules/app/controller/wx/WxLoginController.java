/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.controller.wx;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.app.enumutils.UserEnum;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.form.WxLoginForm;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ruanyuanyuan
 * @Date: 2021/1/24 17:21
 * @Description: 微信登录接口
 **/
@RestController
@RequestMapping("/app/wx/")
@Api("微信登录接口")
public class WxLoginController {

    @Value("${application.weixin.app-id}")
    private String appId;
    @Value("${application.weixin.app-secret}")
    private String appSecret;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 微信小程序登录同时获取openId
     */
    @PostMapping("xcx/login")
    @ApiOperation("微信小程序登录")
    public R login(@RequestBody WxLoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        //拿到小程序登录成功后的code码，到微信平台获取open_id
        String url= "https://api.weixin.qq.com/sns/jscode2session";
        HashMap<String, Object> param = new HashMap<>();
        param.put("appid",appId);
        param.put("secret",appSecret);
        param.put("js_code",form.getCode());
        param.put("grantType","authorization_code");
        //向微信平台发起请求
        String response = HttpUtil.post(url,param);
        JSONObject data = JSONUtil.parseObj(response);
        String openId = data.getStr("openid");

        //如果openId为空则直接返回
        if(StringUtils.isBlank(openId)){
            return R.error(99999,"登录失败");
        }

        //根据openId查询用户信息，如果没有查询到则将用户注册到数据库中，否则不注册只查询
        UserEntity user = userService.getOne(new QueryWrapper<UserEntity>().eq("open_id", openId));
        if(null == user){
            user = new UserEntity();
            user.setOpenId(openId);
            user.setNickName(form.getNickName());
            user.setPhoto(form.getPhoto());
            user.setType(UserEnum.WEI_XIN_USER.getCode());
            user.setCreateTime(new Date());
            userService.save(user);
        }

        //通过userId生成Token并返回给小程序
        Long userId = user.getUserId();
        String token = jwtUtils.generateToken(userId);
        Map<String,Object> result = new HashMap<>();
        result.put("token",token);
        result.put("expire",jwtUtils.getExpire());
        return R.ok(result);
    }

}
