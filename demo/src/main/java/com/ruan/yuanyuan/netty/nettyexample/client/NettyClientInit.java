package com.ruan.yuanyuan.netty.nettyexample.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * @ClassName NettyClientInit
 * @Author ruanyuanyuan
 * @Date 2020/9/28-09:46
 * @Version 1.0
 * @Description TODO
 **/
public class NettyClientInit extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new NettyClientHandler());
    }
}
