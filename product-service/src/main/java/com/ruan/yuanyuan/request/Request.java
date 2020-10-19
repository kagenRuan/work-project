package com.ruan.yuanyuan.request;

/**
 * @ClassName Request
 * @Author ruanyuanyuan
 * @Date 2020/10/15-09:34
 * @Version 1.0
 * @Description TODO 请求接口
 **/
public interface Request {

    void process();

    String getProductId();

    boolean isForceRefresh();
}
