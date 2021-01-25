package com.github.wxpay.sdk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;

/**
 * @ClassName MyWXPayConfig
 * @Author ruanyuanyuan
 * @Date 2021/1/25-16:01
 * @Version 1.0
 * @Description TODO 自定义微信支付配置文件
 **/
@Component
public class MyWXPayConfig extends WXPayConfig{

    //小程序AppId
    @Value("${application.app-id}")
    private String appId;

    //小程序商户ID
    @Value("${application.mch-id}")
    private String mchId;

    //小程序商户ID对应的密钥
    @Value("${application.mch-key}")
    private String mchKey;

    //小程序商户对应的数字证书
    @Value("${application.cert-path}")
    private String certPath;

    //用于保存小程序商户对应的数字证书中的内容
    private byte[] certData;

    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/25 16:09
     * @Description: TODO 当Spring创建完该对象后，对其进行初始化时就会调用次方法，
     *                    该方法主要是用于【读取数字证书中的内容】
     * @return: void
     **/
    @PostConstruct
    public void init() throws Exception{
        File file = new File(certPath);
        FileInputStream inputStream = new FileInputStream(file);
        //将文件中的内容放入到Buffer缓存中
        BufferedInputStream buffered = new BufferedInputStream(inputStream);
        this.certData=new byte[(int)file.length()];
        buffered.read(this.certData);//读取数字证书的内容
        buffered.close();
        inputStream.close();
    }

    @Override
    String getAppID() {
        return this.appId;
    }

    @Override
    String getMchID() {
        return this.mchId;
    }

    @Override
    String getKey() {
        return this.mchKey;
    }

    @Override
    InputStream getCertStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.certData);
        return byteArrayInputStream;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API,true);
            }
        };
    }
}
