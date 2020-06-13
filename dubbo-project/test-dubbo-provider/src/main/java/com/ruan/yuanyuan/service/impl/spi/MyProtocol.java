package com.ruan.yuanyuan.service.impl.spi;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Protocol;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * @ClassName: MyProtocol
 * @author: ruanyuanyuan
 * @date: 2020/6/5 16:19
 * @version: 1.0
 * @description:
 **/
public class MyProtocol implements Protocol {
    @Override
    public int getDefaultPort() {
        return 999;
    }

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        return null;
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return null;
    }

    @Override
    public void destroy() {

    }
}
