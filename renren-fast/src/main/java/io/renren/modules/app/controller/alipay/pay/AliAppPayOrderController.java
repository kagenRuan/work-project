package io.renren.modules.app.controller.alipay.pay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @ClassName AliAppPayOrder
 * @Author ruanyuanyuan
 * @Date 2021/1/28-13:49
 * @Version 1.0
 * @Description TODO 支付宝APP付款 | H5支付
 **/
@Log4j2
@RestController
@RequestMapping("/app/ali/pay/app")
@Api("支付宝APP付款 | H5支付接口")
public class AliAppPayOrderController {

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
     * @Description: TODO 支付宝APP支付 | H5也是这个接口
     * @return: io.renren.common.utils.R
     **/
    @Login
    @PostMapping("/appPayOrder")
    @ApiOperation("支付宝App付款")
    public R appPayOrder(@RequestBody AliPayOrderVo form, @RequestHeader HashMap header) {

        //表单校验
        ValidatorUtils.validateEntity(form);

        //获取token并解析,获取userId
        long userId = Long.valueOf(jwtUtils.getClaimByToken(header.get("token").toString()).getSubject());
        String orderId = form.getOrderId();

        log.info("appPayOrder--> 支付宝App程序付款 orderId:{} userId:{}",form.getOrderId(),userId);

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
            AlipayClient client=new DefaultAlipayClient(gateway,appid,privateKey,"json","UTF-8",publickey,"RSA2");
            AlipayTradeAppPayRequest request=new AlipayTradeAppPayRequest();
            AlipayTradeAppPayModel model=new AlipayTradeAppPayModel();
            model.setBody("我是测试数据");
            model.setSubject("App支付测试Java");
            model.setOutTradeNo(orderId);//订单ID
            model.setTimeoutExpress("30m");
            model.setTotalAmount(orderInfo.getAmount()+"");
            model.setProductCode("QUICK_MSECURITY_PAY");
            request.setNotifyUrl(notifyUrl);
            request.setBizModel(model);

            AlipayTradeAppPayResponse response= client.sdkExecute(request);
            if(response.isSuccess()){
                String prepayId=response.getTradeNo();
                UpdateWrapper updateWrapper = new UpdateWrapper();
                updateWrapper.eq("id", orderInfo.getId());
                updateWrapper.set("prepay_id",prepayId);
                updateWrapper.set("payment_type",2);
                orderService.update(orderInfo, updateWrapper);
                return R.ok().put("orderString", response.getBody());//将body返回给APP端进行支付调用
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("支付宝支付模块故障");
        }
        return R.error("支付订单创建失败");
    }
}
