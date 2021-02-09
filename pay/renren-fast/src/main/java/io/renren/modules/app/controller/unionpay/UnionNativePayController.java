package io.renren.modules.app.controller.unionpay;

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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import unionpay.acp.sdk.AcpService;
import unionpay.acp.sdk.LogUtil;
import unionpay.acp.sdk.SDKConfig;
import unionpay.acp.sdk.SDKConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName UnionpNativePayController
 * @Author ruanyuanyuan
 * @Date 2021/1/30-14:34
 * @Version 1.0
 * @Description TODO 银联支付【主要针对PC浏览器】
 **/
@Log4j2
@RestController
@RequestMapping("/app/union/native/pay")
@Api("银联APP支付接口")
public class UnionNativePayController {


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

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
     * TODO 银联APP建支付订单,由于安卓手机上的银联APP支付，只能通过Native的支付方法来做，因为国内无法使用Google服务
     *      流程：1、用户点击付款请求到后台创建支付订单
     *           2、后台调用银联支付的创建订单接口，生成HTML保存到数据库或者redis中，同时后台返回订单的标识给APP
     *           3、APP用订单的标识请求后台获取到银联生成的HTML，APP把这个HTML渲染到浏览器中进行支付
     */
    @Login
    @PostMapping("/unionNativePayOrder")
    @ApiOperation("银联Native支付创建订单")
    public R unionNativePayOrder(@RequestBody AliPayOrderVo aliPayOrderVo, @RequestHeader Map header){

        //表单校验
        ValidatorUtils.validateEntity(aliPayOrderVo);

        //获取token并解析,获取userId
        long userId = Long.valueOf(jwtUtils.getClaimByToken(header.get("token").toString()).getSubject());
        String orderId = aliPayOrderVo.getOrderId();

        log.info("unionNativePayOrder --> 银联Native支付创建订单 orderId:{} userId:{}",orderId,userId);

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
        //版本号，全渠道默认值
        requestData.put("version", SDKConfig.getConfig().getVersion());
        //字符集编码，可以使用UTF-8,GBK两种方式
        requestData.put("encoding", "UTF-8");
        //签名方法
        requestData.put("signMethod", SDKConfig.getConfig().getSignMethod());
        //交易类型 ，01：消费
        requestData.put("txnType", "01");
        //交易子类型， 01：自助消费
        requestData.put("txnSubType", "01");
        //业务类型，B2C网关支付，手机wap支付
        requestData.put("bizType", "000201");
        //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板 08：手机
        requestData.put("channelType", "07");
        /***商户接入参数***/
        //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
        requestData.put("merId", mchId);
        //接入类型，0：直连商户
        requestData.put("accessType", "0");
        //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        requestData.put("orderId",orderId);
        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        requestData.put("txnTime",  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        //订单超时时间
        requestData.put("payTimeout", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime() + 15 * 60 * 1000));
        //交易币种（境内商户一般是156 人民币）
        requestData.put("currencyCode", "156");
        //交易金额，单位分，不要带小数点
        requestData.put("txnAmt", amount);
        //前台通知地址
        requestData.put("frontUrl", frontUrl);
        //后台通知地址
        requestData.put("backUrl",backUrl);

        try{
            /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
            Map<String, String> submitFromData = AcpService.sign(requestData,"UTF-8");
            //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
            String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
            //生成自动跳转的Html表单
            String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,"UTF-8");
            log.info("打印请求HTML，此为请求报文，为联调排查问题的依据："+html);

            if(StringUtils.isNotBlank(html)){
                UpdateWrapper<OrderEntity> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",orderId);
                updateWrapper.set("payment_type",3);
                orderService.update(updateWrapper);
                return R.ok("银联Native支付订单创建成功").put("htmlBody",html);
            }
        }catch (Exception e){
            log.error("unionNativePayOrder --> 银联Native支付创建订单 errorMsg:{}",e.getMessage());
        }
        return R.error("银联支付订单创建失败");
    }


    /*
     * @Author: ruanyuanyuan
     * @Date: 2021/1/29 14:45
     * @Description: TODO 银联Native支付成功前端回调通知接口
     **/
    @RequestMapping(value = "/nativeFrontResponse")
    public void nativeFrontResponse(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.info("frontResponse --> 银联Native付款成功前端通知消息接口");
        request.setCharacterEncoding("UTF-8");

        String encoding = request.getParameter(SDKConstants.param_encoding);
        //获取所有参数
        Map<String, String> reqParam = getAllRequestParam(request);
        // 打印参数
        LogUtil.printRequestLog(reqParam);
        // 处理参数
        StringBuffer page = new StringBuffer();
        Map<String, String> validatorData = paramByte(reqParam);

        // 验证签名
        if (AcpService.validate(validatorData, encoding)) {
            /*
             * TODO respCode=00表示交易成功
             **/
            if("00".equals(request.getParameter("respCode"))){
                //获取订单ID
                String orderId =validatorData.get("orderId");
                //交易流水号
                String queryId =validatorData.get("queryId");
                UpdateWrapper<OrderEntity> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",orderId);
                updateWrapper.set("payment_type",3);
                updateWrapper.set("status",2);
                updateWrapper.set("prepay_id",queryId);
                orderService.update(updateWrapper);
            }
        }
        response.sendRedirect(orderUrl);
    }

    private Map paramByte(Map reqParam) throws UnsupportedEncodingException {
        if (null != reqParam && !reqParam.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
            Map<String, String> valideData = new HashMap<String, String>(reqParam.size());
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                valideData.put(key, value);
            }
            return valideData;
        }
        return null;
    }

    //获取request中所有的参数
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                    // System.out.println("======为空的字段名===="+en);
                    res.remove(en);
                }
            }
        }
        return res;
    }
}


