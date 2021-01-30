package io.renren.modules.app.controller.alipay.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.enumutils.OrderStatusEnum;
import io.renren.modules.app.enumutils.ResultCodeEnum;
import io.renren.modules.app.service.OrderService;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.app.vo.pay.AliPayOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @ClassName AliMicorAppPayController
 * @Author ruanyuanyuan
 * @Date 2021/1/27-21:43
 * @Version 1.0
 * @Description TODO 支付宝小程序支付
 **/
@Log4j2
@RestController
@RequestMapping("/app/ali/pay")
@Api("支付宝小程序支付接口")
public class AliMicroAppPayController {

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
    //小程序回调通知
    @Value("${application.ali.micro-app.notify-url}")
    private String notifyUrl;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;


    @PostMapping("microAppPayOrder")
    @ApiOperation("支付宝小程序支付")
    public R microAppPayOrder(@RequestBody AliPayOrderVo aliPayOrderVo, @RequestHeader Map header){

        log.info("microAppPayOrder--> 支付宝小程序支付 orderId:{}",aliPayOrderVo.getOrderId());

        //表单校验
        ValidatorUtils.validateEntity(aliPayOrderVo);

        //获取token并解析,获取userId
        long userId = Long.valueOf(jwtUtils.getClaimByToken(header.get("token").toString()).getSubject());
        String orderId = aliPayOrderVo.getOrderId();

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

        //验证购物券是否有效
        //验证团购活动是否有效


        //TODO 验证订单是否有效,只有订单状态为【未支付】才能进行创建支付订单
        if(OrderStatusEnum.NOT_PAY.getCode().equals(orderInfo.getStatus())){
            try {
                log.info("<<<< 调用支付宝创建订单 start >>>");
                AlipayClient alipayClient = new DefaultAlipayClient(gateway,microAppid,microPrivateKey,"json","utf-8",microPublickey,"RSA2");
                AlipayTradeCreateRequest tradeCreateRequest = new AlipayTradeCreateRequest();
                String amount = orderInfo.getAmount().intValue()+"";
                String bizContent = "{" +
                        "\"out_trade_no\":\"" + orderInfo.getId() + "\","+ //商品订单ID
                        "\"total_amount\":\"" + amount +"\","+ //订单金额
                        "\"subject\":\"订单备注\","+ //订单备注
                        "\"buyer_id\":\"" + userInfo.getOpenId() +"\","+ //用户ID
                        "\"notify_url\":\"" + notifyUrl +"\""+ //回调通知URL
                        "}";

                log.info("<<<< 调用支付宝创建订单 bizContent:{} >>>",bizContent);

                tradeCreateRequest.setBizContent(bizContent);
                AlipayTradeCreateResponse tradeCreateResponse = alipayClient.execute(tradeCreateRequest);
                if(tradeCreateResponse.isSuccess()){
                    String prepayId = tradeCreateResponse.getTradeNo();
                    orderInfo.setPrepayId(prepayId);
                    orderInfo.setPaymentType(2);
                    orderService.updateById(orderInfo);
                    //返回数据给小程序
                    return R.ok("创建支付订单成功").put("prepayId",prepayId);
                }
                log.info("<<<< 调用支付宝创建订单 end >>>");
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }
        return R.ok("创建支付订单失败");
    }

    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/27 23:11
     * @Description: TODO 主动查询支付宝平台订单状态
     * @return: void
     **/
    @ApiOperation("主动查询支付宝平台订单状态")
    @RequestMapping("/queryAliOrderStatus")
    public R queryAliOrderStatus(@RequestBody AliPayOrderVo aliPayOrderVo,@RequestHeader Map header){
        log.info("queryAliOrderStatus--> 主动查询支付宝平台订单状态 orderId:{}",aliPayOrderVo.getOrderId());

        //表单校验
        ValidatorUtils.validateEntity(aliPayOrderVo);

        //获取token并解析,获取userId
        long userId = Long.valueOf(jwtUtils.getClaimByToken(header.get("token").toString()).getSubject());
        String orderId = aliPayOrderVo.getOrderId();

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

        //TODO 判断订单 状态是否是【已支付】，如果是已支付则不需要再次修改
        if(OrderStatusEnum.PAY.getCode() == orderInfo.getStatus()){
            return R.ok("订单状态已修改");
        }

        try {
            //TODO 如果订单状态不是【已支付】，那么就需要主动到支付宝平台查询订单状态，并修改为【已支付】
            AlipayClient alipayClient = new DefaultAlipayClient(gateway,microAppid,microPrivateKey,"json","utf-8",microPublickey,"RSA2");
            AlipayTradeQueryRequest tradeQueryRequest = new AlipayTradeQueryRequest();
            String bizContent = "{" +
                    "\"out_trade_no\":\"" + orderId + "\","+ //商品订单ID
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
                    updateWrapper.eq("id",orderId);
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

    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/27 23:11
     * @Description: TODO 小程序支付异步回调通知
     * @param request:
     * @param response:
     * @return: void
     **/
    @ApiOperation("小程序支付异步回调通知")
    @RequestMapping("/aliMicroAppPayNotify")
    public void aliMicroAppPayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String tradeNo = request.getParameter("trade_no"); //支付订单ID 也就是数据库中prepay_id
        String outTradeNo = request.getParameter("out_trade_no"); //订单
        String tradeStatus = request.getParameter("trade_status");
        if(ResultCodeEnum.TRADE_FINISHED.getMessage().equals(tradeStatus) || ResultCodeEnum.SUCCESS.getMessage().equals(tradeStatus)){
            UpdateWrapper<OrderEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",outTradeNo);
            updateWrapper.set("payment_type",2);
            updateWrapper.set("status",2);
            orderService.update(updateWrapper);
        }

        response.setCharacterEncoding("utf-8");
        Writer writer = response.getWriter();
        writer.write("success");
        writer.close();
    }
}
