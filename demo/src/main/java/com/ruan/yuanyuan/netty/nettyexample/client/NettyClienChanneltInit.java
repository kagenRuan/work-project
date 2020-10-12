package com.ruan.yuanyuan.netty.nettyexample.client;

import com.ruan.yuanyuan.netty.nettyexample.NettyMessageDecoder;
import com.ruan.yuanyuan.netty.nettyexample.NettyMessageEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName NettyClientInit
 * @Author ruanyuanyuan
 * @Date 2020/9/28-09:46
 * @Version 1.0
 * @Description TODO
 **/
public class NettyClienChanneltInit extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        /**
         * Netty的空闲检测Handler--->主要用于心跳检测
         * readerIdleTime：多长时间没有【读】事件，就会发送一个心跳检测包检测是否连接
         * writerIdleTime：多长时间没有【写】事件，就会发送一个心跳检测包检测是否连接
         * allIdleTime：多长时间没有【读和写】事件，就会发送一个心跳检测包检测是否连接
         * unit：时间单位
         * 当检测是否连接时，就会把这个事件检测的动作传递给下一个Handler的userEventTriggered方法，这个Handler是需要我们自定义的
         **/
        pipeline.addLast(new IdleStateHandler(3,7,10, TimeUnit.SECONDS));
        pipeline.addLast(new NettyClientIdleHandler());
        //Netty的String的解码器，编码码器
//        pipeline.addLast("decoder",new StringDecoder());
//        pipeline.addLast("encoder",new StringEncoder());
        //自定义协议包进行编码
        pipeline.addLast(new NettyMessageEncoder());
        //自定义协议包进行解码
        pipeline.addLast(new NettyMessageDecoder());
        pipeline.addLast(new NettyClientHandler());

    }
}
