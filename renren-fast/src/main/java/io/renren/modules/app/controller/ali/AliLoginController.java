package io.renren.modules.app.controller.ali;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.app.vo.pay.AliPayLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName AliLoginController
 * @Author ruanyuanyuan
 * @Date 2021/1/27-16:24
 * @Version 1.0
 * @Description TODO 支付宝小程序登录接口
 **/
@Log4j2
@RestController
@RequestMapping("/app/ali/")
@Api("支付宝登录接口")
public class AliLoginController {

    //支付宝网关【固定死】
    @Value("${application.ali.gateway}")
    private String gateway;

    //支付宝小程序appid
    @Value("${application.ali.micro-app.appid}")
    private String microAppid;
    //支付宝公钥
    @Value("${application.ali.micro-app.public-key}")
    private String microPublickey;
    //小程序私钥
    @Value("${application.ali.micro-app.private-key}")
    private String microPrivateKey;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/27 21:39
     * @Description: TODO 支付宝小程序登录接口
     * @param aliPayLoginVo:
     * @return: io.renren.common.utils.R
     **/
    @PostMapping("microAppLogin")
    @ApiOperation("支付宝小程序登录")
    public R microAppLogin(@RequestBody AliPayLoginVo aliPayLoginVo){

        log.info("aliMicroAppLogin ->支付宝小程序登录 param:{}", JSON.toJSONString(aliPayLoginVo));

        //表单校验
        ValidatorUtils.validateEntity(aliPayLoginVo);

        //开始封装参数，发起授权请求
        AlipayClient alipayClient = new DefaultAlipayClient(gateway,microAppid,microPrivateKey,"json","utf-8",microPublickey,"RSA2");
        AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
        oauthTokenRequest.setGrantType("authorization_code");
        oauthTokenRequest.setCode(aliPayLoginVo.getAuthCode());
        try {
            AlipaySystemOauthTokenResponse tokenResponse = alipayClient.execute(oauthTokenRequest);
            if(tokenResponse.isSuccess()){
                String userId = tokenResponse.getUserId();
                UserEntity userInfo = userService.getOne(new QueryWrapper<UserEntity>().eq("open_id", userId));
                if(null == userInfo){
                    userInfo.setCreateTime(new Date());
                    userInfo.setUsername(aliPayLoginVo.getNickName());
                    userInfo.setNickName(aliPayLoginVo.getNickName());
                    userInfo.setPhoto(aliPayLoginVo.getPhoto());
                    userInfo.setOpenId(userId);
                    userInfo.setType(2);//支付宝小程序登录
                    userService.save(userInfo);
                }

                //根据用户的userId 生成Token
                String token = jwtUtils.generateToken(userInfo.getUserId());
                Map<String,Object> result = new HashMap<>();
                result.put("token",token);
                result.put("expire",jwtUtils.getExpire());
                return R.ok(result);

            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return R.error("授权失败");
    }
}
