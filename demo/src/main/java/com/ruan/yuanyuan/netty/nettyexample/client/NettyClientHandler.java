package com.ruan.yuanyuan.netty.nettyexample.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @ClassName NettyClientHandler
 * @Author ruanyuanyuan
 * @Date 2020/9/28-09:33
 * @Version 1.0
 * @Description TODO
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道连接成功，就会触发该事件，也就是说这是就可以进行【write】事件
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server", CharsetUtil.UTF_8));
    }


    /**
     * 当通道有【read】事件就会触发该方法
     * ChannelHandlerContext：上下文，其中Pipeline,channel,连接地址
     * msg：客户端发送的数据
     **/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端接收到服务端【"+ctx.channel().remoteAddress()+"】消息："+byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 异常关闭
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
