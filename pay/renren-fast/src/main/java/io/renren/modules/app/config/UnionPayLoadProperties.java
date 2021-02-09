package io.renren.modules.app.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import unionpay.acp.sdk.SDKConfig;

import javax.annotation.PostConstruct;

/**
 * @ClassName UnionPayLoadProperties
 * @Author ruanyuanyuan
 * @Date 2021/1/30-14:37
 * @Version 1.0
 * @Description TODO 主要加载银联支付的配置文件
 **/
@Log4j2
@Component
public class UnionPayLoadProperties {

    @PostConstruct
    public void  initUnionPayConfig(){
        log.info("读取unionPay配置文件");
        SDKConfig.getConfig().loadPropertiesFromSrc();
    }
}
