package io.renren.modules.app.controller.wx.pay;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.renren.modules.app.enumutils.OrderStatusEnum;
import io.renren.modules.app.form.order.PayOrderForm;
import wxpay.config.MyWXPayConfig;
import wxpay.config.WXPay;
import wxpay.config.WXPayUtil;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.enumutils.ResultCodeEnum;
import io.renren.modules.app.service.OrderService;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WxPayController
 * @Author ruanyuanyuan
 * @Date 2021/1/24-23:25
 * @Version 1.0
 * @Description TODO 微信小程序支付控制器
 **/
@Log4j2
@RestController
@RequestMapping("/app/wx/pay")
@Api("微信小程序支付接口")
public class WxMicroPayController {

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
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private MyWXPayConfig myWXPayConfig;

    /**
     * 微信小程序创建支付订单
     */
    @Login
    @PostMapping("/microAppPayOrder")
    @ApiOperation("小程序创建支付订单")
    public R microAppPayOrder(@RequestBody PayOrderForm form, @RequestHeader Map header){

        log.info("/microAppPayOrder 小程序创建支付订单 orderId:{}",form.getId());

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
            payParam.put("out_trade_no", orderId);//订单ID TODO 订单ID必须要唯一最好不要使用数据库自增的ID值因为很容易重复
            //TODO 由于微信支付的金额不能是小数，所以这里会乘100
            String amount = orderInfo.getAmount().multiply(new BigDecimal(100)).intValue()+"";
            payParam.put("total_fee", amount);//金额
            payParam.put("spbill_create_ip", "127.0.0.1");//创建订单的IP，默认
            payParam.put("notify_url", microAppNotifyUrl);//创建订单成功后的回调通知地址
            payParam.put("trade_type", "JSAPI");//支付方式固定写死
            payParam.put("openid", userInfo.getOpenId());//小程序的appId
            Map<String, String> resultPayParam = wxPay.unifiedOrder(payParam);//微信SDK已经封装好URL，所以直接传入参数调用就行了

            String resultCode = resultPayParam.get("result_code");//业务结果
            String returnCode = resultPayParam.get("return_code");//此次通讯结果

            log.info("<<<<<微信平台创建订单结果:{}>>>>>",resultCode);

            if("SUCCESS".equals(resultCode)){
                //TODO 支付成功后会返回微信平台支付成功的ID,将其保存到数据库中
                String prepayId = resultPayParam.get("prepay_id");
                orderInfo.setPrepayId(prepayId);
                orderService.updateById(orderInfo);

                //生成数字签名
                payParam.clear();
                String timeStamp = new Date().getTime()+"";
                String nonceStr = WXPayUtil.generateNonceStr();
                createSign(payParam,timeStamp,nonceStr,prepayId);
                //开始生成数据签名,并返回给小程序
                String signature = WXPayUtil.generateSignature(payParam, mchKey);
                return R.ok("创建支付订单成功").put("package","prepay_id="+prepayId).
                        put("timeStamp",timeStamp).
                        put("nonceStr",nonceStr).
                        put("sign",signature);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("创建支付订单失败");
        }
        return R.ok("创建支付订单失败");
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/25 17:37
     * @Description: 小程序支付成功后的回调通知
     **/
    @RequestMapping("/microAppPeyNotify")
    @ApiOperation("小程序支付成功后的回调通知")
    public void microAppPeyNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("/microAppPeyNotify 小程序支付成功后的回调通知 ");

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String resultXml = readerXml(request);
        Map<String, String> result = WXPayUtil.xmlToMap(resultXml);
        String resultCode = result.get("result_code");
        String returnCode = result.get("return_code");

        log.info("<<<<小程序支付成功回调通知结果>>>>resultCode:{} returnCode:{}",resultCode,returnCode);

        if(ResultCodeEnum.SUCCESS.getMessage().equals(resultCode) && ResultCodeEnum.SUCCESS.getMessage().equals(returnCode)){
            //TODO 微信支付成功后，返回的本地订单ID
            UpdateWrapper<OrderEntity> wrapper = new UpdateWrapper<>();
            String outTradeOn = result.get("out_trade_no");
            wrapper.eq("id",outTradeOn);
            wrapper.set("status",OrderStatusEnum.PAY.getCode());
            orderService.update(wrapper);
            //返回信息给微信平台
            responseMsg(response);
        }
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/25 22:38
     * @Description: TODO 功能：主要就是防止小程序支付成功后，商户系统没有收到微信平台发送的支付成功通知
     *                    需求：
     *                    当小程序支付成功后，就会调用此方法修改订单的状态。
     *                    但是如果订单的状态是【已付款】那么则不需要再向微信平台发起查询订单的请求，并且也不会修改数据库的订单状态了。
     *                    否则如果订单的状态是未支付，那么就会向微信平台发起查询订单请求，如果微信平台中的该订单的交易状态为【SUCCESS】那么就会修改数据库的订单状态为【已付款】
     **/
    @Login
    @RequestMapping("/microAppOrderQuery")
    @ApiOperation("小程序查询订单状态")
    public R microAppOrderQuery(@RequestBody PayOrderForm form,@RequestHeader Map header){
        log.info("/microAppOrderQuery 小程序查询订单状态 orderId:{}",form.getId());

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
                    orderInfo.setStatus(2);
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

    //从request中读取XML文件
    private String readerXml(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuffer temp = new StringBuffer();
        String line = reader.readLine();
        while (null != line){
            temp.append(line);
            line = reader.readLine();
        }
        reader.close();
        return temp.toString();
    }

    //响应
    private void responseMsg(HttpServletResponse response) throws Exception {
        response.setContentType("application/xml");
        Writer writer = response.getWriter();
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        HashMap<String, String> result  = new HashMap<>();
        result.put("return_code","SUCCESS");
        result.put("return_msg","OK");
        String resultXml = WXPayUtil.mapToXml(result);
        bufferedWriter.write(resultXml);
        writer.close();
        bufferedWriter.close();
    }


    //生成数字签名需要的参数
    private Map createSign(Map result,String timeStamp,String nonceStr,String prepayId){
        result.put("appId",appId);
        result.put("timeStamp",timeStamp);
        result.put("nonceStr",nonceStr);
        result.put("package","prepay_id="+prepayId);
        result.put("signType","MD5");
        return result;
    }
}
