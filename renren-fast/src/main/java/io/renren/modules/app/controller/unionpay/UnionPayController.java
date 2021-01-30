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
import javax.annotation.PostConstruct;
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
 * @ClassName UnionPayController
 * @Author ruanyuanyuan
 * @Date 2021/1/28-21:53
 * @Version 1.0
 * @Description TODO 银联支付
 **/
@Log4j2
@RestController
@RequestMapping("/app/union/pay")
@Api("银联支付接口")
public class UnionPayController {

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

    @PostConstruct
    public void  initUnionPayConfig(){
        log.info("读取unionPay配置文件");
        SDKConfig.getConfig().loadPropertiesFromSrc();
    }

    /**
     * TODO 银联APP建支付订单,由于安卓手机上的银联APP支付，只能通过Native的支付方法来做，因为国内无法使用Google服务
     *      流程：1、用户点击付款请求到后台创建支付订单
     *           2、后台调用银联支付的创建订单接口，生成HTML保存到数据库或者redis中，同时后台返回订单的标识给APP
     *           3、APP用订单的标识请求后台获取到银联生成的HTML，APP把这个HTML渲染到浏览器中进行支付
     */
    @Login
    @PostMapping("/wrpUnionPayOrder")
    @ApiOperation("银联APP建支付订单")
    public R wrpUnionPayOrder(@RequestBody AliPayOrderVo aliPayOrderVo, @RequestHeader Map header){

        //表单校验
        ValidatorUtils.validateEntity(aliPayOrderVo);
        //获取token并解析,获取userId
        long userId = Long.valueOf(jwtUtils.getClaimByToken(header.get("token").toString()).getSubject());
        String orderId = aliPayOrderVo.getOrderId();

        log.info("nativePayUpdateOrderStatus --> 根据订单ID修改订单状态 orderId:{} userId:{}",orderId,userId);

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
        requestData.put("version", SDKConfig.getConfig().getVersion());    //版本号，全渠道默认值
        requestData.put("encoding", "UTF-8");  //字符集编码，可以使用UTF-8,GBK两种方式
        requestData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法 默认01代码RSA
        requestData.put("txnType", "01");                //交易类型 ，01：消费
        requestData.put("txnSubType", "01");              //交易子类型， 01：自助消费
        requestData.put("bizType", "000201");            //业务类型，B2C网关支付，手机wap支付
        requestData.put("channelType", "07");            //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机
        /***商户接入参数***/
        requestData.put("merId", mchId);                //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
        requestData.put("accessType", "0");              //接入类型，0：直连商户
        requestData.put("orderId",orderId);             //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        requestData.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        requestData.put("currencyCode", "156");          //交易币种（境内商户一般是156 人民币）
        requestData.put("txnAmt", amount);                  //交易金额，单位分，不要带小数点
        //requestData.put("reqReserved", "透传字段");              //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。

        /**
         * TODO 前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
         * 如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
         * 异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
         **/
        requestData.put("frontUrl", wapFrontUrl);
        /**
         * TODO 后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
         * 后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
         * 注意:1.需设置为外网能访问，否则收不到通知
         *     2.http https均可
         *     3.收单后台通知后需要10秒内返回http200或302状态码
         *     4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
         *     5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
         **/
        requestData.put("backUrl",backUrl);

        /**
         * TODO 订单超时时间。
         * 超过此时间后，除网银交易外，其他交易银联系统会拒绝受理，提示超时。 跳转银行网银交易如果超时后交易成功，会自动退款，大约5个工作日金额返还到持卡人账户。
         * 此时间建议取支付时的北京时间加15分钟。
         * 超过超时时间调查询接口应答origRespCode不是A6或者00的就可以判断为失败。
         **/
        requestData.put("payTimeout", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime() + 15 * 60 * 1000));
        /*
         * TODO 请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面
         **/
        Map<String, String> submitFromData = AcpService.sign(requestData,"UTF-8");
        //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
        String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
        //生成自动跳转的Html表单
        String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,"UTF-8");
        if(!StringUtils.isBlank(html)){
            log.info("打印请求HTML，此为请求报文，为联调排查问题的依据："+html);
            UpdateWrapper<OrderEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",orderId);
            updateWrapper.set("payment_type",3);
            orderService.update(updateWrapper);

            //将HTML内容保存到redis中
            jedisUtil.set(orderInfo.getCode(),15*60*1000, html);
            return R.ok("银联支付订单创建成功").put("orderCode",orderInfo.getCode());
        }
        return R.ok("银联支付订单创建失败");
    }

    /*
     * @Author: ruanyuanyuan
     * @Date: 2021/1/30 13:24
     * @Description: TODO APP传入订单的标识获取HTML页面内容
     **/
    @RequestMapping("/getHtmlBody")
    public R getHtmlBody(@RequestParam("orderCode") String orderCode){
       String result = jedisUtil.get(orderCode);
       if(!StringUtils.isBlank(result)){
           return R.ok("成功").put("html",result);
       }
       return R.error("失败");
    }


    /*
     * @Author: ruanyuanyuan
     * @Date: 2021/1/29 14:45
     * @Description: TODO 银联支付成功后端回调接口
     **/
    @RequestMapping("/backResponse")
    public void backResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("backResponse --> 银联付款成功后端通知消息接口");
        request.setCharacterEncoding("UTF-8");

        String encoding = request.getParameter(SDKConstants.param_encoding);
        // 获取所有参数
        Map<String, String> reqParam = getAllRequestParam(request);
        // 打印参数
        LogUtil.printRequestLog(reqParam);
        // 处理参数
        Map<String, String> validatorData = paramByte(reqParam,null,encoding);

        //验证签名
        if (!AcpService.validate(validatorData, encoding)) {
            //验签失败，需解决验签问题
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("fail");
            writer.close();
        } else {
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
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("ok");
            writer.close();
        }
    }

    /*
     * @Author: ruanyuanyuan
     * @Date: 2021/1/29 14:45
     * @Description: TODO 银联支付成功前端回调通知接口
     **/
    @RequestMapping("/frontResponse")
    public void frontResponse(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.info("frontResponse --> 银联付款成功前端通知消息接口");
        request.setCharacterEncoding("UTF-8");

        String encoding = request.getParameter(SDKConstants.param_encoding);
        //获取所有参数
        Map<String, String> reqParam = getAllRequestParam(request);
        // 打印参数
        LogUtil.printRequestLog(reqParam);
        // 处理参数
        StringBuffer page = new StringBuffer();
        Map<String, String> validatorData = paramByte(reqParam,page,encoding);

        // 验证签名
        if (!AcpService.validate(validatorData, encoding)) {
            page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
        } else {
            page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
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
                page.append("<tr><td width=\"30%\" align=\"right\">付款成功</td></tr>");
            }else{
                page.append("<tr><td width=\"30%\" align=\"right\">付款失败</td></tr>");
            }
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(page.toString());
            writer.close();
        }
    }

    private Map paramByte(Map reqParam,StringBuffer page,String encoding) throws UnsupportedEncodingException {
        if (null != reqParam && !reqParam.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
            Map<String, String> valideData = new HashMap<>(reqParam.size());
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                value = new String(value.getBytes(encoding), encoding);
                if(null != page){
                    page.append("<tr><td width=\"30%\" align=\"right\">" + key + "(" + key + ")</td><td>" + value + "</td></tr>");
                }
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
