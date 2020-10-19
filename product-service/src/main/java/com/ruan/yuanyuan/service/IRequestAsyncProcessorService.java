package com.ruan.yuanyuan.service;

import com.ruan.yuanyuan.request.Request;

/**
 * @ClassName IRequestAsyncProcessorService
 * @Author ruanyuanyuan
 * @Date 2020/10/15-15:26
 * @Version 1.0
 * @Description TODO 主要用于请求过滤
 **/
public interface IRequestAsyncProcessorService {

    void processor(Request request);
}
