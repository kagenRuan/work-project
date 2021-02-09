package io.renren.modules.app.controller.wxpay.pay;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.enumutils.OrderStatusEnum;
import io.renren.modules.app.enumutils.ResultCodeEnum;
import io.renren.modules.app.service.OrderService;
import wxpay.config.MyWXPayConfig;
import wxpay.config.WXPay;
import wxpay.config.WXPayUtil;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.app.vo.pay.ScanCodePayOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WxScanCodePayController
 * @Author ruanyuanyuan
 * @Date 2021/1/26-21:37
 * @Version 1.0
 * @Description TODO 【扫码付款】商家扫描用户的付款码进行付款,只要有付款码和订单ID就可以进行支付
 **/
@Log4j2
@RestController
@RequestMapping("/app/wx/scan/code")
@Api("微信扫码付款支付接口")
public class WxScanCodePayController {

    @Value("${application.weixin.micro-app-notify-url}")
    private String microAppNotifyUrl;

    @Value("${application.weixin.app-id}")
    private String appId;

    @Value("${application.weixin.mch-id}")
    private String mchId;

    @Value("${application.weixin.mch-key}")
    private String mchKey;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MyWXPayConfig myWXPayConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    /**
     * 微信【扫码付款】创建支付订单
     */
    @Login
    @PostMapping("/scanCodePayOrder")
    @ApiOperation("微信【商家扫描收款】创建支付订单")
    public R scanCodePayOrder(@RequestBody ScanCodePayOrderVo form, @RequestHeader Map header){

        log.info("scanCodePayOrder 微信【商家扫描收款】创建支付订单 orderId:{}",form.getId());
        //表单校验
        ValidatorUtils.validateEntity(form);

        //获取token并解析,获取userId
        long userId = Long.valueOf(jwtUtils.getClaimByToken(header.get("token").toString()).getSubject());
        String orderId = form.getId();

        //验证此用户是否存在
        UserEntity userInfo = userService.getById(userId);
        if(null == userInfo){
            log.info("用户不存在 userId:{}",userId);
            return R.error("用户不存在");
        }
        //验证此用户是否有此订单
        OrderEntity orderInfo = orderService.getOne(new QueryWrapper<OrderEntity>().
                eq("user_id",userId).
                eq("id",orderId));
        if(null == orderInfo){
            log.info("用户不存在此订单 userId:{} id:{}",userId,orderId);
            return R.error("用户不存在此订单");
        }
        //验证订单是否有效
        if(!OrderStatusEnum.NOT_PAY.getCode().equals(orderInfo.getStatus())){
            log.info("订单无效 id:{} status:{}",orderId,orderInfo.getStatus());
            return R.error("订单无效");
        }
        //验证购物券是否有效
        //验证团购活动是否有效

        //开始发起微信创建订单流程
        try {
            WXPay wxPay = new WXPay(myWXPayConfig);
            HashMap<String, String> payParam = new HashMap<>();
            payParam.put("appid",appId);
            payParam.put("mch_id",mchId);
            payParam.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
            payParam.put("body", "订单备注");//订单备注
            //TODO 订单ID必须要唯一最好不要使用数据库自增的ID值因为很容易重复
            payParam.put("out_trade_no", orderId);
            //TODO 由于微信支付的金额不能是小数，所以这里会乘100
            String amount = orderInfo.getAmount().multiply(new BigDecimal(100)).intValue()+"";
            payParam.put("total_fee", amount);//金额
            payParam.put("spbill_create_ip", "127.0.0.1");//创建订单的IP，默认
            payParam.put("auth_code",form.getAuthCode());//TODO 【付款码】
            String sign = WXPayUtil.generateSignature(payParam,mchKey);
            payParam.put("sign",sign);
            Map<String, String> resultPayParam = wxPay.microPay(payParam);//微信SDK已经封装好URL，所以直接传入参数调用就行了

            String resultCode = resultPayParam.get("result_code");
            String returnCode = resultPayParam.get("return_code");

            log.info("<<<<<微信平台创建订单结果:{}>>>>>",resultCode);

            if(ResultCodeEnum.SUCCESS.getMessage().equals(resultCode) && ResultCodeEnum.SUCCESS.getMessage().equals(returnCode)){
                /**
                 * @Description: 将微信平台返回的ID与订单进行关联
                 **/
                String prepayId = resultPayParam.get("transaction_id");
                orderInfo.setPrepayId(prepayId);
                orderInfo.setStatus(2);
                orderInfo.setPaymentType(1);
                orderService.updateById(orderInfo);
                return R.ok("付款成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("付款失败");
        }
        return R.ok("付款失败");
    }


}
