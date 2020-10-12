package com.ruan.yuanyuan.netty.netty_websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @ClassName NettyWebSocketHandler
 * @Author ruanyuanyuan
 * @Date 2020/9/28-23:48
 * @Version 1.0
 * @Description TODO WebSocket Handler
 *                   TextWebSocketFrame 表示一个文本帧
 **/
public class NettyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器接收到消息："+msg.text());
        //服务端回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame(msg.text()));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
