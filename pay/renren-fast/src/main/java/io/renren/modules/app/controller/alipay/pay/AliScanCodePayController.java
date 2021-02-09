package io.renren.modules.app.controller.alipay.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.service.OrderService;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.app.vo.pay.AliPayOrderVo;
import io.renren.modules.app.vo.pay.ScanCodePayOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName AliScanCodePayController
 * @Author ruanyuanyuan
 * @Date 2021/1/28-16:21
 * @Version 1.0
 * @Description TODO 支付宝扫码支付
 **/
@Log4j2
@RestController
@RequestMapping("/app/ali/pay/app")
@Api("支付宝扫码支付接口")
public class AliScanCodePayController {

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
     * @Date: 2021/1/28 14:01
     * @Description: TODO 支付宝扫码（商家通过扫码器扫描用户的付款码进行付款）
     *                    流程：1、用户出示付款码，商户进行扫码
     *                         2、商户扫码成功后，将付款码传给服务端
     *                         3、服务端调用支付宝平台接口进行支付，并返回结果
     **/
    @Login
    @PostMapping("/storeScanCodePayOrder")
    @ApiOperation("商家扫码付款")
    public R storeScanCodePayOrder(@RequestBody ScanCodePayOrderVo scanCodePayOrderVo, @RequestHeader Map header){
        //表单校验
        ValidatorUtils.validateEntity(scanCodePayOrderVo);

        //获取token并解析,获取userId
        long userId = Long.valueOf(jwtUtils.getClaimByToken(header.get("token").toString()).getSubject());
        String orderId = scanCodePayOrderVo.getId();

        log.info("storeScanCodePayOrder--> 支付宝商家扫码付款 orderId:{} userId:{}",scanCodePayOrderVo.getId(),userId);

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
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(gateway,appid,privateKey,"json","utf-8", publickey,"RSA2");  //获得初始化的AlipayClient
            AlipayTradePayRequest request = new AlipayTradePayRequest();  //创建API对应的request类
            String amount = orderInfo.getAmount().intValue()+"";
            request.setBizContent ( "{"   +
                    "\"out_trade_no\":\""+orderId+"\","   +
                    "\"scene\":\"bar_code\","   +
                    "\"auth_code\":\""+scanCodePayOrderVo.getAuthCode()+"\","   + //即用户在支付宝客户端内出示的付款码，使用一次即失效，需要刷新后再去付款
                    "\"subject\":\"Iphone6 16G\","   +
                    "\"store_id\":\"NJ_001\","   +
                    "\"timeout_express\":\"2m\","   +
                    "\"total_amount\":\""+amount+"\""   +
                    "}" );  //设置业务参数
            AlipayTradePayResponse response = alipayClient.execute(request);
            if(response.isSuccess() && response.getCode().equals("10000")){
                UpdateWrapper<OrderEntity> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",orderId);
                updateWrapper.set("payment_type",2);
                updateWrapper.set("status",2);
                updateWrapper.set("prepay_id",response.getTradeNo());
                orderService.update(updateWrapper);
                return R.ok("支付成功");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return R.error("支付失败");
        }
        return R.ok("支付失败");
    }


    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/28 14:01
     * @Description: TODO 支付宝扫码（用户通过支付宝扫描支付宝二维码进行付款）
     *                    流程：1、用户点击付款，此时请求到服务端生成支付订单
     *                         2、服务端请求支付宝平台生成支付订单，并返回一个二维码给服务端
     *                         3、服务端将二维码返回给页面展示
     *                         4、用户扫描二维码进行支付
     *                         5、用户支付成功后，需要将订单ID或者支付流水号交给服务端调用支付宝平台进行查询支付结果并修改订单状态
     **/
    @Login
    @PostMapping("/userScanCodePayOrder")
    @ApiOperation("用户扫描付款")
    public R userScanCodePayOrder(@RequestBody AliPayOrderVo aliPayOrderVo, @RequestHeader Map header){
        //表单校验
        ValidatorUtils.validateEntity(aliPayOrderVo);

        //获取token并解析,获取userId
        long userId = Long.valueOf(jwtUtils.getClaimByToken(header.get("token").toString()).getSubject());
        String orderId = aliPayOrderVo.getOrderId();

        log.info("userScanCodePayOrder--> 支付宝用户扫描付款 orderId:{} userId:{}",orderId,userId);

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


        try {
            //调用支付宝创建支付订单
            AlipayClient alipayClient = new DefaultAlipayClient(gateway,appid,privateKey,"json","utf-8", publickey,"RSA2");  //获得初始化的AlipayClient
            AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();  //创建API对应的request类
            String amount = orderInfo.getAmount().intValue()+"";
            request.setBizContent( "{"   +
                    "\"out_trade_no\":\""+orderId+"\"," + //商户订单号
                    "\"total_amount\":\""+amount+"\","   +
                    "\"subject\":\"Iphone6 16G\","   +
                    "\"store_id\":\"NJ_001\","   +
                    "\"timeout_express\":\"90m\"}" );
            AlipayTradePrecreateResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                return R.ok("创建支付订单成功").put("qr_code",response.getQrCode());//将二维码返回给前端进行展示
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return R.error("支付失败");
        }
        return R.ok("支付失败");
    }


    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/28 14:01
     * @Description: TODO 支付宝扫码（用户通过支付宝扫描支付宝二维码进行付款）
     *                    流程：用户扫码支付成功后，需要将订单ID或者支付流水号交给服务端调用支付宝平台进行查询支付结果并修改订单状态
     **/
    @Login
    @PostMapping("/userScanCodePayOrderUpdateStatus")
    @ApiOperation("用户扫描付款成功后修改订单状态")
    public R userScanCodePayOrderUpdateStatus(@RequestBody AliPayOrderVo aliPayOrderVo, @RequestHeader Map header){

        //表单校验
        ValidatorUtils.validateEntity(aliPayOrderVo);

        //获取token并解析,获取userId
        long userId = Long.valueOf(jwtUtils.getClaimByToken(header.get("token").toString()).getSubject());
        String orderId = aliPayOrderVo.getOrderId();

        log.info("userScanCodePayOrderUpdateStatus--> 用户扫描付款成功后修改订单状态 orderId:{} userId:{}",orderId,userId);

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

        try {
            AlipayClient alipayClient = new DefaultAlipayClient(gateway,appid,privateKey,"json","utf-8", publickey,"RSA2"); //获得初始化的AlipayClient
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
            request.setBizContent("{" +
                    "\"out_trade_no\":\""+orderId+"\""
                    ); //设置业务参数
            AlipayTradeQueryResponse response = alipayClient.execute(request);//通过alipayClient调用API，获得对应的response类
            System.out.print(response.getBody());
            if(response.isSuccess()){
                UpdateWrapper<OrderEntity> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",orderId);
                updateWrapper.set("payment_type",2);
                updateWrapper.set("status",2);
                updateWrapper.set("prepay_id",response.getTradeNo());
                orderService.update(updateWrapper);
                return R.ok("修改订单状态成功");//将二维码返回给前端进行展示
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return R.error("修改订单状态失败");
        }
        return R.ok("修改订单状态失败");
    }





}
