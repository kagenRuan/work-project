package io.renren.modules.app.controller.unionpay;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.renren.common.JedisUtil;
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
import unionpay.acp.sdk.AcpService;
import unionpay.acp.sdk.SDKConfig;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UnionScanCodePayController
 * @Author ruanyuanyuan
 * @Date 2021/1/30-16:12
 * @Version 1.0
 * @Description TODO
 **/
@Log4j2
@RestController
@RequestMapping("/app/union/scancode/pay")
@Api("银联扫码支付接口")
public class UnionScanCodePayController {

    //商户ID
    @Value("${application.unionpay.mch-id}")
    private String mchId;

    //PC浏览器跳转的前端通知地址
    @Value("${application.unionpay.front-url}")
    private String frontUrl;

    //后端通知url
    @Value("${application.unionpay.back-url}")
    private String backUrl;

    //订单页面
    @Value("${application.unionpay.order-url}")
    private String orderUrl;

    //手机浏览器跳转前端通知地址
    @Value("${application.unionpay.wap-front-url}")
    private String wapFrontUrl;


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private JedisUtil jedisUtil;

    @Login
    @PostMapping("/unionScanCodePayOrder")
    @ApiOperation("银联扫码支付创建订单")
    public R unionScanCodePayOrder(@RequestBody ScanCodePayOrderVo scanCodePayOrderVo, @RequestHeader Map header) {

        //表单校验
        ValidatorUtils.validateEntity(scanCodePayOrderVo);

        //获取token并解析,获取userId
        long userId = Long.valueOf(jwtUtils.getClaimByToken(header.get("token").toString()).getSubject());
        String orderId = scanCodePayOrderVo.getId();

        log.info("unionScanCodePayOrder --> 银联扫码支付创建订单 orderId:{} userId:{}",orderId,userId);

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
        //交易金额不能有小数点，所以乘以100
        String amount = orderInfo.getAmount().multiply(new BigDecimal(100)).intValue()+"";

        Map<String, String> requestData = new HashMap<String, String>();
        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        //版本号 全渠道默认值
        requestData.put("version", SDKConfig.getConfig().getVersion());
        //字符集编码 可以使用UTF-8,GBK两种方式
        requestData.put("encoding", "UTF-8");
        //签名方法
        requestData.put("signMethod", SDKConfig.getConfig().getSignMethod());
        //交易类型 01:消费
        requestData.put("txnType", "01");
        //交易子类 06：二维码消费
        requestData.put("txnSubType", "06");
        //填写000000
        requestData.put("bizType", "000000");
        //渠道类型 08手机
        requestData.put("channelType", "08");

        /***商户接入参数***/
        //商户号码
        requestData.put("merId", mchId);
        //接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
        requestData.put("accessType", "0");
        //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        requestData.put("orderId", scanCodePayOrderVo.getId());
        //订单发送时间，
        requestData.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        //交易金额 单位为分，不能带小数点
        requestData.put("txnAmt", amount);
        //境内商户固定 156 人民币
        requestData.put("currencyCode", "156");
        //C2B码,1-20位数字
        requestData.put("qrNo", scanCodePayOrderVo.getAuthCode());
        //终端号
        requestData.put("termId", "49000002");
        //后台通知接口
        requestData.put("backUrl", backUrl);


        try{
            /**对请求参数进行签名并发送http post请求，接收同步应答报文**/
            Map<String, String> reqData = AcpService.sign(requestData,"UTF-8");
            //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
            String requestAppUrl = SDKConfig.getConfig().getBackRequestUrl();
            //发送请求报文并接受同步应答
            Map<String, String> rspData = AcpService.post(reqData,requestAppUrl,"UTF-8");
            if(!rspData.isEmpty()){
                if(AcpService.validate(rspData, "UTF-8")){
                    log.info("unionScanCodePayOrder --> 银联扫码支付【验证签名成功】");
                    String respCode = rspData.get("respCode") ;
                    if(("00").equals(respCode)){
                        String resultId = rspData.get("orderId");
                        String queryId = rspData.get("queryId");
                        UpdateWrapper<OrderEntity> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.eq("id",resultId);
                        updateWrapper.set("payment_type",3);
                        updateWrapper.set("status",3);
                        updateWrapper.set("prepay_id",queryId);
                        orderService.update(updateWrapper);
                        return R.ok("银联支付成功");
                    }else{
                        log.error("unionScanCodePayOrder --> 银联扫码支付失败 msg:{}",rspData.get("respMsg"));
                        return R.error("银联扫码支付失败");
                    }
                }else{
                    log.error("unionScanCodePayOrder --> 银联扫码支付验证签名失败");
                    return R.error("银联扫码支付签名失败");
                }
            }else{
                log.error("unionScanCodePayOrder --> 银联扫码支付失败");
                return R.error("银联扫码支付失败");
            }
        }catch (Exception e){
            log.error("unionScanCodePayOrder --> 银联扫码支付失败 errorMsg:{}",e.getMessage());
        }
        return R.ok("银联扫码支付失败");
    }

}
