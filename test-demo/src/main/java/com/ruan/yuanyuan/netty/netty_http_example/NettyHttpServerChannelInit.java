package com.ruan.yuanyuan.netty.netty_http_example;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName NettyHttpServerChannelInit
 * @Author ruanyuanyuan
 * @Date 2020/9/28-13:59
 * @Version 1.0
 * @Description TODO
 **/
public class NettyHttpServerChannelInit extends ChannelInitializer {


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //Netty自带的对Http请求进行编码和解码的Handler
        pipeline.addLast("httCode", new HttpServerCodec());
        pipeline.addLast(new NettyHttpServerHandler());
    }
}
