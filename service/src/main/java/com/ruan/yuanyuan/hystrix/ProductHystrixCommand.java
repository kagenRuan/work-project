package com.ruan.yuanyuan.hystrix;

import com.netflix.hystrix.*;
import com.ruan.yuanyuan.entity.Product;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.enums.ResultObjectEnum;

/**
 * @ClassName: ProductHystrixCommand
 * @author: ruanyuanyuan
 * @date: 2019/12/7 12:17
 * @version: 1.0
 * @description: 商品Hystrix降级自定义类
 **/
public class ProductHystrixCommand extends HystrixCommand<ResultObject> {

    private String productId;

    public ProductHystrixCommand(String productId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductHystrixCommandGroup")).
                andCommandKey(HystrixCommandKey.Factory.asKey("ProductHystrixCommandKey")).
                andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ProductThreadPool")).
                andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter().withRequestCacheEnabled(false))//关闭Hystrix缓存

        );
        this.productId = productId;
    }

    @Override
    protected ResultObject run() throws Exception {
        /**
         * 这里实际上应该调用Product服务接口
         */
        Product product = new Product();
        product.setId("1");
        return new ResultObject(product);
    }

    @Override
    protected ResultObject getFallback() {
        return new ResultObject(-9999, ResultObjectEnum.FAIL.getName());
    }

    @Override
    protected String getCacheKey() {
        return productId;
    }
}
