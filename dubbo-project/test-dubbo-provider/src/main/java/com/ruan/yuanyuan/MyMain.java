package com.ruan.yuanyuan;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;
import com.alibaba.dubbo.rpc.ProxyFactory;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol;

/**
 * @ClassName: MyMain
 * @author: ruanyuanyuan
 * @date: 2020/6/5 16:21
 * @version: 1.0
 * @description:
 **/
public class MyMain {

    public static void main(String[] args) {
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        System.out.println("====");
    }
}
