package com.ruan.yuanyuan.netty.nettyexample.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * @ClassName NettyServerChannelInit
 * @Author ruanyuanyuan
 * @Date 2020/9/28-09:23
 * @Version 1.0
 * @Description TODO 用于初始化Channel
 **/
public class NettyServerChannelInit extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new NettyServerHandler(channel));
    }
}
