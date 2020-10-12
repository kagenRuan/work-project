package com.ruan.yuanyuan.netty.nettyexample.server;

import com.ruan.yuanyuan.netty.nettyexample.NettyMessageDecoder;
import com.ruan.yuanyuan.netty.nettyexample.NettyMessageEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

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
        ChannelPipeline pipeline = channel.pipeline();
        //Netty的String的解码,编码
        pipeline.addLast("decoder",new StringDecoder());
//        pipeline.addLast("encoder",new StringEncoder());
        //自定义协议包进行编码
        pipeline.addLast(new NettyMessageEncoder());
        //自定义协议包进行解码
        pipeline.addLast(new NettyMessageDecoder());
        //自定义业务处理Handler
        pipeline.addLast(new NettyServerWeChartHandler());

    }
}
