package io.renren.modules.app.controller.alipay.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.enumutils.OrderStatusEnum;
import io.renren.modules.app.enumutils.ResultCodeEnum;
import io.renren.modules.app.service.OrderService;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.app.vo.pay.AliNativePayVo;
import io.renren.modules.app.vo.pay.AliPayOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AliNativePayOrderController
 * @Author ruanyuanyuan
 * @Date 2021/1/28-14:26
 * @Version 1.0
 * @Description TODO 支付宝【Native支付】 也就是网站
 **/
@Log4j2
@RestController
@RequestMapping("/app/ali/pay/native")
@Api("支付宝【Native】支付接口")
public class AliNativePayOrderController {

    //支付宝网关【固定死】
    @Value("${application.ali.gateway}")
    private String gateway;

    //支付宝appid
    @Value("${application.ali.app.appid}")
    private String appid;

    //支付宝公钥
    @Value("${application.ali.app.public-key}")
    private String publickey;

    //支付宝私钥
    @Value("${application.ali.app.private-key}")
    private String privateKey;

    //小程序回调通知
    @Value("${application.ali.app.notify-url}")
    private String notifyUrl;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/28 15:11
     * @Description: TODO 支付宝的Native支付跟其他的不一样，这个方法只能获取到一段HTML页面然后交由给前端页面进行展示，
     *                    然后用于扫描页面上的二维码进行付款，付款成功后就会跳到一个页面，需要在这个跳转的页面获取
     *                    支付宝平台的支付ID，然后交给后台修改订单状态
     **/
    @Login
    @PostMapping("/aliNativePayOrder")
    @ApiOperation("支付宝Native付款")
    public R aliNativePayOrder(@RequestBody AliPayOrderVo form, @RequestHeader HashMap header){

        //表单校验
        ValidatorUtils.validateEntity(form);
        //获取token并解析,获取userId
        long userId = Long.valueOf(jwtUtils.getClaimByToken(header.get("token").toString()).getSubject());
        String orderId = form.getOrderId();

        log.info("aliNativePayOrder --> 支付宝Native付款 orderId:{} userId:{}",form.getOrderId(),userId);

        //验证此用户是否存在
        UserEntity userInfo = userService.getById(userId);
        if(null == userInfo){
            return R.error("用户不存在");
        }
        //验证此用户是否有此订单
        OrderEntity orderInfo = orderService.getOne(new QueryWrapper<OrderEntity>().eq("user_id",userId).eq("id",orderId));
        if(null == orderInfo){
            return R.error("用户不存在此订单");
        }
        //验证购物券是否有效
        //验证团购活动是否有效

        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appid, privateKey, "json", "utf-8", publickey, "RSA2"); //获得初始化的AlipayClient
        AlipayTradePagePayRequest pagePayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        pagePayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");//页面支付成功后跳转的页面地址
        pagePayRequest.setNotifyUrl(notifyUrl);//在公共参数中设置回跳和通知地址
        pagePayRequest.setBizContent("{" +
                " \"out_trade_no\":\""+orderId+"\"," +
                " \"total_amount\":\""+orderInfo.getAmount()+"\"," +
                " \"subject\":\"Iphone6 16G\"," +
                " \"product_code\":\"FAST_INSTANT_TRADE_PAY\"" +
                " }");//填充业务参数
        try {
            //将HTML代码返回给页面进行展示
            AlipayTradePagePayResponse pagePayResponse = alipayClient.pageExecute(pagePayRequest);
            if(pagePayResponse.isSuccess()){
                UpdateWrapper<OrderEntity> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",orderId);
                updateWrapper.set("payment_type",2);
                orderService.update(updateWrapper);
                return R.ok("创建支付订单成功").put("body",pagePayResponse.getBody());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return R.ok("创建支付订单失败");
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/28 15:19
     * @Description: TODO Native支付成功后，此方法有前端主动传入订单ID，然后由后端根据订单ID修改订单状态
     **/
    @ApiOperation("根据订单ID修改订单状态")
    @RequestMapping("/nativePayUpdateOrderStatus")
    public R  nativePayUpdateOrderStatus(@RequestBody AliNativePayVo nativePayVo, @RequestHeader Map header){

        //表单校验
        ValidatorUtils.validateEntity(nativePayVo);
        //获取token并解析,获取userId
        long userId = Long.valueOf(jwtUtils.getClaimByToken(header.get("token").toString()).getSubject());
        String outTradeNo = nativePayVo.getOutTradeNo();

        log.info("nativePayUpdateOrderStatus --> 根据订单ID修改订单状态 outTradeNo:{} userId:{}",outTradeNo,userId);

        //验证此用户是否存在
        UserEntity userInfo = userService.getById(userId);
        if(null == userInfo){
            return R.error("用户不存在");
        }
        //验证此用户是否有此订单
        OrderEntity orderInfo = orderService.getOne(new QueryWrapper<OrderEntity>().eq("user_id",userId).eq("id",outTradeNo));
        if(null == orderInfo){
            return R.error("用户不存在此订单");
        }

        //TODO 判断订单 状态是否是【已支付】，如果是已支付则不需要再次修改
        if(OrderStatusEnum.PAY.getCode() == orderInfo.getStatus()){
            return R.ok("订单状态已修改");
        }

        try {
            //TODO 如果订单状态不是【已支付】，那么就需要主动到支付宝平台查询订单状态，并修改为【已支付】
            AlipayClient alipayClient = new DefaultAlipayClient(gateway,appid,privateKey,"json","utf-8",publickey,"RSA2");
            AlipayTradeQueryRequest tradeQueryRequest = new AlipayTradeQueryRequest();
            String bizContent = "{" +
                    "\"out_trade_no\":\"" + outTradeNo + "\","+ //商品订单ID
                    "\"query_options\":[" + //订单金额
                    "\"TRADE_SETTLE_INFO\"" +
                    "]"+
                    "}";
            tradeQueryRequest.setBizContent(bizContent);
            AlipayTradeQueryResponse queryResponse = alipayClient.execute(tradeQueryRequest);
            if(queryResponse.isSuccess()){
                String tradeStatus = queryResponse.getTradeStatus();//获取交易状态
                if(ResultCodeEnum.TRADE_FINISHED.getMessage().equals(tradeStatus) || ResultCodeEnum.TRADE_SUCCESS.getMessage().equals(tradeStatus)){
                    UpdateWrapper<OrderEntity> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("id",outTradeNo);
                    updateWrapper.set("status",2);
                    updateWrapper.set("payment_type",2);
                    updateWrapper.set("prepay_id",queryResponse.getTradeNo());
                    orderService.update(updateWrapper);
                    return R.ok("修改订单状态成功");
                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return R.ok("修改订单状态失败");
        }

        return R.ok("修改订单状态成功");
    }
}
