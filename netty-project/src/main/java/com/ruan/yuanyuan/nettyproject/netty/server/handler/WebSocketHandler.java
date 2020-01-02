package com.ruan.yuanyuan.nettyproject.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @ClassName: WebSocketHandler
 * @author: ruanyuanyuan
 * @date: 2019/12/30 15:40
 * @version: 1.0
 * @description: 自定义处理webSocketHandler
 **/
public class WebSocketHandler  extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("webSocketHandler#channelRead0");
    }
}
