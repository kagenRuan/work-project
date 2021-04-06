package com.ruan.yuanyuan.netty.netty_websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @ClassName NettyWebSocketServerChannelInit
 * @Author ruanyuanyuan
 * @Date 2020/9/28-23:46
 * @Version 1.0
 * @Description TODO
 **/
public class NettyWebSocketServerChannelInit extends ChannelInitializer {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /**
         *  websocket 是通过http的状态码为101进行升级为webSocket的
         *  HttpObjectAggregator：用于将分段进行聚合，比如进行大数量传输会出现分段，也就是出现多个http请求传输数据
         *  WebSocketServerProtocolHandler：将http协议升级为websocket协议并保持长连接
         **/
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
        pipeline.addLast(new NettyWebSocketHandler());
    }
}
