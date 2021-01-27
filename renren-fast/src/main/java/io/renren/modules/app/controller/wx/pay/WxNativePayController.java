package io.renren.modules.app.controller.wx.pay;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.enumutils.OrderStatusEnum;
import io.renren.modules.app.form.order.PayOrderForm;
import io.renren.modules.app.service.OrderService;
import wxpay.config.MyWXPayConfig;
import wxpay.config.WXPay;
import wxpay.config.WXPayUtil;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.enumutils.ResultCodeEnum;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
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
 * @ClassName WxNativePayController
 * @Author ruanyuanyuan
 * @Date 2021/1/26-17:00
 * @Version 1.0
 * @Description TODO 微信【Native支付】控制器 Native支付主要用于PC端的浏览器支付
 **/
@Log4j2
@RestController
@RequestMapping("/app/wx/native")
@Api("微信Native接口")
public class WxNativePayController {

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
     * 微信【Native支付】创建支付订单
     */
    @Login
    @PostMapping("/nativePayOrder")
    @ApiOperation("微信【Native支付】创建支付订单")
    public R nativePayOrder(@RequestBody PayOrderForm form, @RequestHeader Map header){

        log.info("/nativePayOrder 微信【Native支付】创建支付订单 orderId:{}",form.getId());
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
            payParam.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
            payParam.put("body", "订单备注");//订单备注
            //订单ID TODO 订单ID必须要唯一最好不要使用数据库自增的ID值因为很容易重复
            payParam.put("out_trade_no", orderId);
            //TODO 由于微信支付的金额不能是小数，所以这里会乘100
            String amount = orderInfo.getAmount().multiply(new BigDecimal(100)).intValue()+"";
            payParam.put("total_fee", amount);//金额
            payParam.put("spbill_create_ip", "127.0.0.1");//创建订单的IP，默认
            payParam.put("notify_url", microAppNotifyUrl);//创建订单成功后的回调通知地址
            payParam.put("trade_type", "NATIVE");//支付方式固定写死
            String sign = WXPayUtil.generateSignature(payParam,mchKey);
            payParam.put("sign",sign);
            Map<String, String> resultPayParam = wxPay.unifiedOrder(payParam);//微信SDK已经封装好URL，所以直接传入参数调用就行了

            String resultCode = resultPayParam.get("result_code");//业务结果
            String returnCode = resultPayParam.get("return_code");//此次通讯结果
            String codeUrl = resultPayParam.get("code_url");//付款地址

            log.info("<<<<<微信平台创建订单结果:{}>>>>>",resultCode);

            if(ResultCodeEnum.SUCCESS.getMessage().equals(resultCode) && ResultCodeEnum.SUCCESS.getMessage().equals(returnCode)){
                /**
                 * @Description: 将微信平台返回的ID与订单进行关联
                 **/
                String prepayId = resultPayParam.get("prepay_id");
                orderInfo.setPrepayId(prepayId);
                orderService.updateById(orderInfo);
                //将code_url返回给页面生成二维码
                return R.ok("创建支付订单成功").put("code_url",codeUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("创建支付订单失败");
        }
        return R.ok("创建支付订单成功");
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/25 22:38
     * @Description: TODO 需求：Native支付成功后，微信平台无法通知PC端浏览器，所以就需要PC端根据业务需要定时调用次接口查询支付结果
     **/
    @Login
    @RequestMapping("/nativePayOrderQuery")
    @ApiOperation("Native支付查询订单状态")
    public R nativePayOrderQuery(@RequestBody PayOrderForm form,@RequestHeader Map header){
        log.info("/nativePayOrderQuery Native支付查询订单状态 orderId:{}",form.getId());

        //表单校验
        ValidatorUtils.validateEntity(form);
        try {
            //获取token并解析,获取userId
            long userId = Long.valueOf(jwtUtils.getClaimByToken(header.get("token").toString()).getSubject());

            //验证此用户是否有此订单
            OrderEntity orderInfo = orderService.getOne(new QueryWrapper<OrderEntity>().
                    eq("user_id",userId).
                    eq("id",form.getId()));
            if(null == orderInfo){
                log.info("该用户没有订单 userId:{} orderId:{}",userId,form.getId());
                return R.ok("该用户没有订单");
            }

            HashMap<String, String> param = new HashMap<>();
            param.put("appid",appId);
            param.put("mch_id",mchId);
            param.put("out_trade_no",String.valueOf(orderInfo.getId()));
            param.put("nonce_str",WXPayUtil.generateNonceStr());
            //生成签名
            String sign = WXPayUtil.generateSignature(param, mchKey);
            param.put("sign",sign);
            WXPay wxPay = new WXPay(myWXPayConfig);
            //发起小程序订单查询
            Map<String, String> resultParam = wxPay.orderQuery(param);

            //解析查询结果
            String resultCode = resultParam.get("result_code");//通讯结果状态
            String returnCode = resultParam.get("return_code");//业务结果状态

            log.info("小程序订单查询结果 resultCode:{} returnCode:{}",resultCode,returnCode);

            //只有当通讯结果状态和业务结果状态都为【SUCCESS】时才算成功
            if(ResultCodeEnum.SUCCESS.getMessage().equals(resultCode) && ResultCodeEnum.SUCCESS.getMessage().equals(returnCode)){
                String tradeState = resultParam.get("trade_state");//交易状态
                //当交易状态为【SUCCESS】时修改订单状态为【已付款】
                if(ResultCodeEnum.SUCCESS.getMessage().equals(tradeState)){
                    orderInfo.setStatus(OrderStatusEnum.PAY.getCode());
                    orderInfo.setPaymentType(1);
                    orderService.updateById(orderInfo);
                    return R.ok("订单状态修改成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("查询支付订单失败");
        }
        return R.ok("订单还未支付");
    }



}
